package com.example.auro.lib.controllers.v1;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private URLManagerService urlManagerService;

	@PostMapping
	public ResponseEntity<ApiResponse> createShortUrl(@RequestBody ApiRequest apiRequest, ServletRequest servletRequest)
			throws Exception {
		LOG.info("Incoming Request for creating short URL: {}", apiRequest);
		String shortUrl;
		shortUrl = urlManagerService.createShortUrlKey(apiRequest.getLongUrl()).get();
		ApiResponse response = new ApiResponse();
		response.setStatus(ApiRequestStatus.SUCCESS);
		response.setErrorCode(null);
		response.setMessage("Successfully created short URL");
		response.setResponse("www.ts.com/" + shortUrl);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
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