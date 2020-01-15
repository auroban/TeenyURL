package com.example.auro.lib.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.auro.lib.models.url.URLEntity;

@Repository
public interface URLRepository extends MongoRepository<URLEntity, String> {
	
	public Optional<URLEntity> findOneByShortUrlKey(String shortUrlKey);	
	
	public Optional<URLEntity> findOneByOriginalUrl(String originalUrl);	
	
}
