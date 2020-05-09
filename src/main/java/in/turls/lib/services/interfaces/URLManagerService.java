package in.turls.lib.services.interfaces;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import in.turls.lib.constants.UrlExpiryUnit;
import in.turls.lib.models.url.ShortUrlInfo;

public interface URLManagerService {
	
	public Optional<ShortUrlInfo> createShortUrlKey(final String longUrl, final UrlExpiryUnit expiryUnit, final Integer expiryValue) throws NoSuchElementException, DuplicateKeyException;
	public Optional<String> retrieveOriginalUrl(final String shortUrlKey) throws NoSuchElementException;
	public Optional<Boolean> deleteUrlEntity(final String shortUrlKey) throws NoSuchElementException;

}
