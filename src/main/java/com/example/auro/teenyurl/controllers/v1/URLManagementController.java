package com.example.auro.teenyurl.controllers.v1;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auro.teenyurl.models.api.ApiRequest;
import com.example.auro.teenyurl.models.api.ApiResponse;

@RestController
@RequestMapping("/v1/url")
public class URLManagementController {

	private static final Logger LOG = LogManager.getLogger(URLManagementController.class);
	
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> createShortUrl(@RequestBody ApiRequest apiRequest, ServletRequest servletRequest) {
		
		return null;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getLongUrl(@RequestParam("id") String id, ServletRequest servletRequest) {
		return null;
	}
	
}
