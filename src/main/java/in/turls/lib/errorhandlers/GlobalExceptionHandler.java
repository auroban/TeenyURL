package in.turls.lib.errorhandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.turls.lib.constants.ApiRequestErrorCode;
import in.turls.lib.constants.ApiRequestStatus;
import in.turls.lib.models.api.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiResponse> handleUnmappedError(final Exception exception) {
		LOG.error("Handling unmapped error: {}", exception.getMessage(), exception);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setStatus(ApiRequestStatus.FAILURE);
		apiResponse.setMessage("Something went wrong. Check back later please");
		apiResponse.setErrorCode(ApiRequestErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleInvalidMethodArgument(final MethodArgumentNotValidException exception) {
		ApiResponse apiResponse = new ApiResponse();
		LOG.error("Handling invalid method argument:\n", exception);
		String message = exception.getMessage();
		if (message.contains("longUrl")) {
			apiResponse.setErrorCode(ApiRequestErrorCode.INVALID_REQUEST_PARAMETER);
			apiResponse.setMessage("Invalid URL Length. URL length must be between 8 and 200");
			apiResponse.setStatus(ApiRequestStatus.FAILURE);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
		return handleUnmappedError(exception);
	}
}
