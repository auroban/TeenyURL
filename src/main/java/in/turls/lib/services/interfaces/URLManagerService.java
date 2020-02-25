package in.turls.lib.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.dao.DuplicateKeyException;

public interface URLManagerService {
	
	public Optional<String> createShortUrlKey(final String longUrl) throws NoSuchElementException, DuplicateKeyException;
	public Optional<String> retrieveOriginalUrl(final String shortUrlKey) throws NoSuchElementException;
	public Optional<Boolean> deleteUrlEntity(final String shortUrlKey) throws NoSuchElementException;
	public Optional<String> retrieveShortUrl(final String longUrl) throws NoSuchElementException;	

}
