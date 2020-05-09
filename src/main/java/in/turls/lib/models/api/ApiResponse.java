package in.turls.lib.models.api;


import in.turls.lib.constants.ApiRequestErrorCode;
import in.turls.lib.constants.ApiRequestStatus;

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
public class ApiResponse<T> {
	
	@JsonProperty("status")
	private ApiRequestStatus status;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("response")
	private T response;
	
	@JsonProperty("error")
	private ApiRequestErrorCode errorCode;

}
