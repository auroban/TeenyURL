package in.turls.lib.repositories;

import java.util.Date;

import in.turls.lib.constants.UrlStatus;

public interface CustomUrlRepository {
	
	public void updateByValidTillAndStatus(final Date currentDate, final UrlStatus oldStatus, final UrlStatus newStatus);
	
	public boolean deleteByShortUrlKeyAndStatus(final String shortUrlKey, final UrlStatus status);

}
