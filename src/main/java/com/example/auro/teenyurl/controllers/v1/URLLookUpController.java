package com.example.auro.teenyurl.controllers.v1;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auro.teenyurl.models.api.ApiRequest;
import com.example.auro.teenyurl.models.api.ApiResponse;
import com.example.auro.teenyurl.services.interfaces.URLManager;

@RestController
@RequestMapping("/lookup")
public class URLLookUpController {
	
	private static final Logger LOG = LogManager.getLogger(URLLookUpController.class);
	
	@Autowired
	private URLManager urlManager;

	@PostMapping("/")
	public ResponseEntity<ApiResponse> lookUpUsingLongUrl(@RequestBody ApiRequest apiRequest, ServletRequest servletRequest) {
		return null;
	}
}
