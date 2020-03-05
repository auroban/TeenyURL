package in.turls.lib.utils;

import in.turls.lib.constants.UrlExpiryUnit;
import in.turls.lib.exceptions.InvalidUrlExpiry;
import in.turls.lib.models.api.ApiRequest;
import in.turls.lib.models.url.UrlExpiry;

public class ApiRequestValidator {
	
	public static void validateApiRequest(final ApiRequest apiRequest) throws InvalidUrlExpiry {
		
		if (apiRequest.getUrlExpiry() != null) {
			validateUrlExpiry(apiRequest.getUrlExpiry());
		}
		
	}
	
	private static void validateUrlExpiry(final UrlExpiry urlExpiry) throws InvalidUrlExpiry {
		
		UrlExpiryUnit unit = urlExpiry.getUnit();
		Integer value = urlExpiry.getValue();
		
		switch (unit) {
		case DAYS:
			if (value.intValue() < 3) {
				throw new InvalidUrlExpiry("When UNIT selected is DAYS, value cannot be less than 3");
			}
			break;
		case WEEKS:
		case MONTHS:
		case YEARS:
			String message;
			if (value.intValue() < 1) {
				if (UrlExpiryUnit.WEEKS.equals(unit)) {
					message = "When URL Expiry \"unit\" selected is WEEKS, \"value\" cannot be less than 3";
				} else if (UrlExpiryUnit.MONTHS.equals(unit)) {
					message = "When URL Expiry \"unit\" selected is MONTHS, \"value\" cannot be less than 1";
				} else {
					message = "When URL Expiry \"unit\" selected is YEARS, \"value\" cannot be less than 1";
				}
				throw new InvalidUrlExpiry(message);
			}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + unit);
		}
		
	}

}
