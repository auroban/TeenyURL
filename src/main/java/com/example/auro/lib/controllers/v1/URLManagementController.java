package com.example.auro.lib.controllers.v1;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletRequest;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auro.lib.constants.ApiRequestErrorCode;
import com.example.auro.lib.constants.ApiRequestStatus;
import com.example.auro.lib.models.api.ApiRequest;
import com.example.auro.lib.models.api.ApiResponse;
import com.example.auro.lib.services.interfaces.URLManagerService;

@RestController
@RequestMapping("/v1/url")
public class URLManagementController {

	private static final Logger LOG = LogManager.getLogger(URLManagementController.class);
	private static final UrlValidator URL_VALIDATOR = new UrlValidator();

	@Autowired
	private URLManagerService urlManagerService;
	
	
	@Value("${shorturl.path}")
	private String shortUrlPath;

	@PostMapping
	public ResponseEntity<ApiResponse> createShortUrl(@RequestBody ApiRequest apiRequest,
			ServletRequest servletRequest) {

		try {
			LOG.info("Incoming Request for creating short URL: {}", apiRequest);
			ApiResponse response = new ApiResponse();
			String shortUrl;
			String longUrl = apiRequest.getLongUrl();
			if (!StringUtils.hasText(longUrl) || !URL_VALIDATOR.isValid(longUrl)) {
				LOG.error("Invalid Long URL:{}", longUrl);
				response.setStatus(ApiRequestStatus.FAILURE);
				response.setMessage(
						"Invalid URL. Please ensure the URL is not missing any URL scemes like http, https or ftp");
				response.setErrorCode(ApiRequestErrorCode.INVALID_REQUEST_PARAMETER);
				return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
			shortUrl = urlManagerService.createShortUrlKey(apiRequest.getLongUrl()).get();
			response.setStatus(ApiRequestStatus.SUCCESS);
			response.setErrorCode(null);
			response.setMessage("Successfully created short URL");
			response.setResponse(shortUrlPath + shortUrl);
			LOG.debug("Outgoing Response: {}", response);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

		} catch (DuplicateKeyException e) {
			LOG.error("Duplicate Key found while creating short URL key\n", e);
			String errorMessage = e.getMessage();
			if (errorMessage.contains("originalUrl")) {
				ApiResponse errorResponse = new ApiResponse();
				errorResponse.setErrorCode(ApiRequestErrorCode.DUPLICATE_URL);
				errorResponse.setMessage("The URL " + apiRequest.getLongUrl() + " is already in existence");
				errorResponse.setStatus(ApiRequestStatus.FAILURE);
				return new ResponseEntity<ApiResponse>(errorResponse, HttpStatus.CONFLICT);
			} else {
				throw e;
			}
		}
	}

	@GetMapping()
	public ResponseEntity<ApiResponse> getLongUrl(@RequestParam("id") String id, ServletRequest servletRequest) {

		LOG.info("Incoming Request for getting long URL: {}", id);
		ApiResponse apiResponse = new ApiResponse();
		if (StringUtils.isEmpty(id)) {
			LOG.error("Invalid ID");
			apiResponse.setStatus(ApiRequestStatus.FAILURE);
			apiResponse.setMessage("The given ID is invalid");
			apiResponse.setErrorCode(ApiRequestErrorCode.INVALID_ID);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}

		try {
			Optional<String> longUrl = urlManagerService.retrieveOriginalUrl(id);
			if (longUrl.isPresent() && !StringUtils.isEmpty(longUrl.get())) {
				apiResponse.setStatus(ApiRequestStatus.SUCCESS);
				apiResponse.setMessage("Found URL");
				apiResponse.setResponse(longUrl.get());
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			}

		} catch (NoSuchElementException e) {
			LOG.error("Error while getting Long URL: {}", e.getMessage(), e);
		}
		apiResponse.setStatus(ApiRequestStatus.FAILURE);
		apiResponse.setMessage("No URL found for the given ID");
		apiResponse.setErrorCode(ApiRequestErrorCode.URL_NOT_FOUND);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@CacheEvict(value = "url-single", key = "#id")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUrlEntry(@PathVariable("id") String id) {

		LOG.info("Incoming request for deleting URL entry for Short URL Key: {}", id);
		ApiResponse apiResponse = new ApiResponse();

		try {
			if (StringUtils.isEmpty(id)) {
				apiResponse.setStatus(ApiRequestStatus.FAILURE);
				apiResponse.setMessage("Invalid ID");
				apiResponse.setErrorCode(ApiRequestErrorCode.INVALID_ID);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			}

			Optional<Boolean> deleted = urlManagerService.deleteUrlEntity(id);

			if (deleted.isPresent() && deleted.get().equals(Boolean.TRUE)) {
				LOG.info("Successfully deleted URL entry with Short URL Key: {}", id);
				apiResponse.setStatus(ApiRequestStatus.SUCCESS);
				apiResponse.setMessage("Successfully deleted URL");
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			}
		} catch (NoSuchElementException e) {
			LOG.error("No Such URL Entity found with Short URL Key: {}", id);
		}

		apiResponse.setStatus(ApiRequestStatus.FAILURE);
		apiResponse.setMessage("Failed to delete URL");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

	}

}
