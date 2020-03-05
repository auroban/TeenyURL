package in.turls.lib.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.turls.lib.constants.UrlStatus;
import in.turls.lib.documents.url.URLDocument;

@Repository
public interface URLRepository extends MongoRepository<URLDocument, String>, CustomUrlRepository {
	
	public Optional<URLDocument> findOneByShortUrlKeyAndStatus(String shortUrlKey, UrlStatus status);	
	
	public Optional<URLDocument> findOneByOriginalUrlAndStatus(String originalUrl, UrlStatus status);	
	
}
