package com.example.auro.teenyurl.services.impls;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auro.teenyurl.models.url.URLEntity;
import com.example.auro.teenyurl.repositories.URLRepository;
import com.example.auro.teenyurl.services.interfaces.URLManagerService;


@Service
public class URLManagerServiceImpl implements URLManagerService {
	
	private static final Logger LOG = LogManager.getLogger(URLManagerServiceImpl.class);
	
	@Autowired
	private URLRepository urlRepository;
	
	@Override
	public Optional<String> addUrl(String longUrl) throws Exception {
		
		URLEntity urlEntity = new URLEntity(longUrl);
		String shortUrl = RandomStringUtils.randomAlphanumeric(7);
		LOG.info("Generated Short URL {} \n Length: {}", shortUrl, shortUrl.length());
		urlEntity.setShortUrl(shortUrl);
		urlRepository.save(urlEntity);
		Optional<URLEntity> urlEntityOptional = urlRepository.findOneByShortUrl(shortUrl);
		LOG.info("Retrieved URL entity: {}", urlEntityOptional.get());
		return Optional.ofNullable(urlEntityOptional.get().getShortUrl());
	}

	@Override
	public Optional<String> findUrl(String urlId) throws Exception {
		Optional<URLEntity> urlEntityOptional = urlRepository.findOneByShortUrl(urlId);
		LOG.info("Found URL Entity: {}", urlEntityOptional.or(null));
		return Optional.ofNullable(urlEntityOptional.get().getOriginalUrl());
	}

	@Override
	public Optional<Boolean> deleteUrl(String urlId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> findWithLongUrl(String longUrl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
