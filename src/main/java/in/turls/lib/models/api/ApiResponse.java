package in.turls.lib.models.api;


import in.turls.lib.constants.ApiRequestErrorCode;
import in.turls.lib.constants.ApiRequestStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse<T> {
	
	@Expose
	@SerializedName("status")
	private ApiRequestStatus status;
	
	@Expose
	@SerializedName("message")
	private String message;
	
	@Expose
	@SerializedName("response")
	private T response;
	
	@Expose
	@SerializedName("error")
	private ApiRequestErrorCode errorCode;

}
