package in.turls.lib.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.turls.lib.models.url.URLEntity;

@Repository
public interface URLRepository extends MongoRepository<URLEntity, String> {
	
	public Optional<URLEntity> findOneByShortUrlKey(String shortUrlKey);	
	
	public Optional<URLEntity> findOneByOriginalUrl(String originalUrl);	
	
}
