package com.example.auro.teenyurl.controllers.v1;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auro.teenyurl.constants.ApiRequestStatus;
import com.example.auro.teenyurl.models.api.ApiRequest;
import com.example.auro.teenyurl.models.api.ApiResponse;
import com.example.auro.teenyurl.services.interfaces.URLManagerService;

@RestController
@RequestMapping("/v1/url")
public class URLManagementController {

	private static final Logger LOG = LogManager.getLogger(URLManagementController.class);
	
	@Autowired
	private URLManagerService urlManagerService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponse> createShortUrl(@RequestBody ApiRequest apiRequest, ServletRequest servletRequest) throws Exception {
		LOG.info("Incoming Request: {}", apiRequest);
		String shortUrl = urlManagerService.addUrl(apiRequest.getLongUrl()).get();
		ApiResponse response = new ApiResponse();
		response.setStatus(ApiRequestStatus.SUCCESS);
		response.setErrorCode(null);
		response.setMessage("Successfully created short URL");
		response.setResponse("www.ts.com/" + shortUrl);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getLongUrl(@RequestParam("id") String id, ServletRequest servletRequest) {
		return null;
	}
	
}
