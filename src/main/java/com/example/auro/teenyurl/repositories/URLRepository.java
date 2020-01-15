package com.example.auro.teenyurl.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.auro.teenyurl.models.url.URLEntity;

@Repository
public interface URLRepository extends MongoRepository<URLEntity, Long> {
	
	public Optional<URLEntity> findOneByShortUrl(String shortUrl);
	
	
}
