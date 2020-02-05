package in.turls.lib.services.impls;

import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import in.turls.lib.models.url.URLEntity;
import in.turls.lib.repositories.URLRepository;
import in.turls.lib.services.interfaces.CounterService;
import in.turls.lib.services.interfaces.URLManagerService;

@Service
public class URLManagerServiceImpl implements URLManagerService {

	private static final Logger LOG = LogManager.getLogger(URLManagerServiceImpl.class);

	@Autowired
	private URLRepository urlRepository;

	@Autowired
	private CounterService counterService;

	@Override
	public Optional<String> createShortUrlKey(String longUrl) throws DuplicateKeyException, NoSuchElementException {

		URLEntity urlEntity = new URLEntity(longUrl);
		Long counter = counterService.getNextCounterNumber();
		LOG.info("Selecting counter number: {}", counter);
		String shortUrlKey = Base64.getEncoder().encodeToString(counter.toString().getBytes()).replaceAll("=", "")
				.replaceAll("/+", "");
		if (shortUrlKey.length() > 7) {
			shortUrlKey = shortUrlKey.substring(shortUrlKey.length() - 7);
		}
		LOG.info("Generated Short URL Key: {}", shortUrlKey);
		urlEntity.setShortUrlKey(shortUrlKey);
		urlRepository.save(urlEntity);
		Optional<URLEntity> urlEntityOptional = urlRepository.findOneByShortUrlKey(shortUrlKey);		
		LOG.info("Retrieved URL entity: {}", urlEntityOptional.get());
		if (urlEntityOptional.isPresent()) {
			return Optional.of(urlEntityOptional.get().getShortUrlKey());
		}
		return Optional.empty();
	}

	@Override
	@Cacheable(value = "url-single", key = "#shortUrlKey", unless = "#result == null")
	public Optional<String> retrieveOriginalUrl(String shortUrlKey) throws NoSuchElementException {
		LOG.info("Finding original URL with Short URL Key: {}", shortUrlKey);
		Optional<URLEntity> urlEntityOptional = urlRepository.findOneByShortUrlKey(shortUrlKey);
		if (urlEntityOptional.isPresent()) {
			LOG.info("Retrieved URL Entity: {}", urlEntityOptional.get());
			return Optional.of(urlEntityOptional.get().getOriginalUrl());
		}
		LOG.info("No URL Entity Retrieved");
		return Optional.empty();
	}

	@Override
	@CacheEvict(value = "url-single", key = "#shortUrlKey")
	public Optional<Boolean> deleteUrlEntity(String shortUrlKey) throws NoSuchElementException {
		LOG.info("Deleting URL Entity having Short URL Key: {}", shortUrlKey);
		Optional<URLEntity> urlEntityOptional = urlRepository.findOneByShortUrlKey(shortUrlKey);
		if (urlEntityOptional.isPresent()) {
			LOG.info("Retrieved URL Entity: {}", urlEntityOptional.get());
			urlRepository.delete(urlEntityOptional.get());
			return Optional.of(Boolean.TRUE);
		}
		LOG.info("No URL Entity found");
		return Optional.of(Boolean.FALSE);
	}

	@Override
	public Optional<String> retrieveShortUrl(String longUrl) throws NoSuchElementException {
		
		LOG.info("Finding with Original URL: {}", longUrl);
		Optional<URLEntity> urlEntityOptional = urlRepository.findOneByOriginalUrl(longUrl);
		if (urlEntityOptional.isPresent()) {
			LOG.info("Retrieved URL Entity: {}", urlEntityOptional.get());
			return Optional.of(urlEntityOptional.get().getShortUrlKey());
		} 
		LOG.info("No URL Entity found");
		return Optional.empty();
	}

}