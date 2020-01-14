package com.example.auro.teenyurl.models.api;

import com.example.auro.teenyurl.constants.ApiRequestErrorCode;
import com.example.auro.teenyurl.constants.ApiRequestStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse {
	
	@JsonProperty("status")
	private ApiRequestStatus status;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("response")
	private String response;
	
	@JsonProperty("error")
	private ApiRequestErrorCode errorCode;

}
