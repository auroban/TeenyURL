package com.example.auro.lib.models.api;

import com.example.auro.lib.constants.ApiRequestErrorCode;
import com.example.auro.lib.constants.ApiRequestStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
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