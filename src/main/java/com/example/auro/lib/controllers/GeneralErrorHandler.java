package com.example.auro.lib.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.auro.lib.constants.ApiRequestErrorCode;
import com.example.auro.lib.constants.ApiRequestStatus;
import com.example.auro.lib.models.api.ApiResponse;

@RestControllerAdvice
public class GeneralErrorHandler {
	
	private static final Logger LOG = LogManager.getLogger(GeneralErrorHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponse> handleUnmappedError(final Exception exception) {
		LOG.error("Handling unmapped error: {}", exception.getMessage(), exception);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(ApiRequestStatus.FAILURE);
		apiResponse.setMessage("Something went wrong. Check back later please");
		apiResponse.setErrorCode(ApiRequestErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
