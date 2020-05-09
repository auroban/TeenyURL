package in.turls.lib.services.impls;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import in.turls.lib.constants.UrlExpiryUnit;
import in.turls.lib.constants.UrlStatus;
import in.turls.lib.documents.url.URLDocument;
import in.turls.lib.models.url.ShortUrlInfo;
import in.turls.lib.repositories.URLRepository;
import in.turls.lib.services.interfaces.CounterService;
import in.turls.lib.services.interfaces.URLManagerService;

@Service
public class URLManagerServiceImpl implements URLManagerService {

	private static final Logger LOG = LogManager.getLogger(URLManagerServiceImpl.class);
	
	private static final String URL_CACHE_NAME = "urls";

	@Autowired
	private URLRepository urlRepository;

	@Autowired
	private CounterService counterService;
	
	@Value("${short_url.domain}")
	private String shortUrlDomain;
	
	@Autowired
	private RedissonClient redissonClient;

	@Override
	public Optional<ShortUrlInfo> createShortUrlKey(String longUrl, UrlExpiryUnit expiryUnit, Integer expiryValue) throws DuplicateKeyException, NoSuchElementException {

		Long counter = counterService.getNextCounterNumber();
		LOG.info("Selecting counter number: {}", counter);
		String shortUrlKey = Base64.getEncoder().encodeToString(counter.toString().getBytes()).replaceAll("=", "")
				.replaceAll("/+", "");
		if (shortUrlKey.length() > 7) {
			shortUrlKey = shortUrlKey.substring(shortUrlKey.length() - 7);
		}
		LOG.info("Generated Short URL Key: {}", shortUrlKey);
		URLDocument urlEntity = new URLDocument(longUrl, getValidTill(expiryUnit, expiryValue));
		urlEntity.setShortUrlKey(shortUrlKey);
		urlEntity.setStatus(UrlStatus.ACTIVE);
		urlEntity = urlRepository.save(urlEntity);
		LOG.info("Saved URL Document: {}", urlEntity);
		ShortUrlInfo shortUrlInfo = new ShortUrlInfo();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ssXXX");
		shortUrlInfo.setKey(urlEntity.getShortUrlKey());
		shortUrlInfo.setCompleteUrl(shortUrlDomain + urlEntity.getShortUrlKey());
		shortUrlInfo.setExpiry(simpleDateFormat.format(urlEntity.getValidTill()));
		return Optional.of(shortUrlInfo);
	}

	@Override
	public Optional<String> retrieveOriginalUrl(String shortUrlKey) throws NoSuchElementException {
		LOG.info("Finding original URL with Short URL Key: {}", shortUrlKey);
		LOG.info("Looking for URL in Cache....");
		RMapCache<String, String> urlCache = redissonClient.getMapCache(URL_CACHE_NAME);
		if (urlCache.containsKey(shortUrlKey)) {
			String originalUrl = urlCache.get(shortUrlKey);
			LOG.info("URL {} found in Cache", originalUrl);
			return Optional.of(originalUrl);
		}
		LOG.info("URL could not be found in Cache. Looking in DB....");
		Optional<URLDocument> urlEntityOptional = urlRepository.findOneByShortUrlKeyAndStatus(shortUrlKey, UrlStatus.ACTIVE);
		if (urlEntityOptional.isPresent()) {
			LOG.info("Retrieved URL Entity: {}", urlEntityOptional.get());
			URLDocument urlEntity = urlEntityOptional.get();
			Calendar calendar = Calendar.getInstance();
			long today = calendar.getTimeInMillis();
			long expiryAt = urlEntity.getValidTill().getTime();
			long diff = expiryAt - today;
			if (diff > 0) {
				urlCache.fastPutIfAbsent(shortUrlKey, urlEntity.getOriginalUrl(), diff, TimeUnit.MILLISECONDS);
				return Optional.of(urlEntity.getOriginalUrl());
			}
		}
		LOG.info("No URL Document Retrieved");
		return Optional.empty();
	}

	@Override
	public Optional<Boolean> deleteUrlEntity(String shortUrlKey) throws NoSuchElementException {
		LOG.info("Deleting URL Entity having Short URL Key: {}", shortUrlKey);
		RMapCache<String, String> urlCache = redissonClient.getMapCache(URL_CACHE_NAME);
		if (urlCache.containsKey(shortUrlKey)) {
			LOG.info("Removing {} from Cache....", shortUrlKey);
			urlCache.remove(shortUrlKey);
		}
		if (urlRepository.deleteByShortUrlKeyAndStatus(shortUrlKey, UrlStatus.ACTIVE)) {
			return Optional.of(Boolean.TRUE);
		}
		return Optional.of(Boolean.FALSE);
	}
	
	private Date getValidTill(final UrlExpiryUnit unit, final Integer value) {
		Calendar calendar = Calendar.getInstance();
		int field = 0;
		if (unit.equals(UrlExpiryUnit.DAYS)) {
			field = Calendar.DATE;
		} else if (unit.equals(UrlExpiryUnit.WEEKS)) {
			field = Calendar.WEEK_OF_YEAR;
		} else if (unit.equals(UrlExpiryUnit.MONTHS)) {
			field = Calendar.MONTH;
		} else if (unit.equals(UrlExpiryUnit.MINUTES)) {
			field = Calendar.MINUTE;
		} else {
			field = Calendar.YEAR;
		}
		calendar.add(field, value);
		return calendar.getTime();
	}
}
