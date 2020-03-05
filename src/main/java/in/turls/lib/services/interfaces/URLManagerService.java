package in.turls.lib.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import com.google.gson.JsonObject;

import in.turls.lib.constants.UrlExpiryUnit;

public interface URLManagerService {
	
	public Optional<JsonObject> createShortUrlKey(final String longUrl, final UrlExpiryUnit expiryUnit, final Integer expiryValue) throws NoSuchElementException, DuplicateKeyException;
	public Optional<String> retrieveOriginalUrl(final String shortUrlKey) throws NoSuchElementException;
	public Optional<Boolean> deleteUrlEntity(final String shortUrlKey) throws NoSuchElementException;

}
