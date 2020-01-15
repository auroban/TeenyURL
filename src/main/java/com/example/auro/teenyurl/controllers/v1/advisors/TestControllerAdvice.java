package com.example.auro.teenyurl.controllers.v1.advisors;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.auro.teenyurl.constants.ApiRequestErrorCode;
import com.example.auro.teenyurl.constants.ApiRequestStatus;
import com.example.auro.teenyurl.models.api.ApiResponse;

@RestControllerAdvice
public class TestControllerAdvice {
	
	@ExceptionHandler(value = NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException() {
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponse> handleUnmappedError() {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(ApiRequestStatus.FAILURE);
		apiResponse.setMessage("Something went wrong. Please Check later");
		apiResponse.setErrorCode(ApiRequestErrorCode.GENERAL_ERROR);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
