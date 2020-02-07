package in.turls.lib.filters;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.turls.lib.constants.ApiRequestErrorCode;
import in.turls.lib.constants.ApiRequestStatus;
import in.turls.lib.models.api.ApiResponse;
import in.turls.lib.services.interfaces.ApiUsageMonitorService;

public class ApiUsageLimitFilter implements Filter {
	
	private static final Logger LOG = LogManager.getLogger(ApiUsageLimitFilter.class);
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private ApiUsageMonitorService apiUsageMonitorService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = HttpServletRequest.class.cast(request);
		HttpServletResponse httpServletResponse = HttpServletResponse.class.cast(response);
		try {
		
		LOG.info("Checking usgae limit for IP: {}", httpServletRequest.getHeader("X-Real-IP"));
		if (apiUsageMonitorService.isAllowed(httpServletRequest)) {
			LOG.info("IP: {} allowed", httpServletRequest.getHeader("X-Real-IP"));
			chain.doFilter(httpServletRequest, response);
		} else {
			LOG.warn("IP: {} reached its usage limit. Blocking any further calls", httpServletRequest.getHeader("X-Real-IP"));
			ApiResponse errorResponse = new ApiResponse();
			errorResponse.setStatus(ApiRequestStatus.FAILURE);
			errorResponse.setMessage("You have reached the API usage limit. Only 10 requests allowed per hour. Please try after the time specified in Retry-After header");
			errorResponse.setErrorCode(ApiRequestErrorCode.API_USAGE_LIMIT_REACHED);
			String errorResponseString = getResponseAsString(errorResponse);
			LOG.info("Error response as String:{}", errorResponseString);
			httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			Long remainingTTL = apiUsageMonitorService.remainingTTL(httpServletRequest);
			Long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(remainingTTL);
			httpServletResponse.setHeader("Retry-After", remainingSeconds.toString() + " seconds");
			httpServletResponse.getWriter().write(errorResponseString);
		}
		} catch (Exception e) {
			LOG.error("Error while processing request:\n",e);
			httpServletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}
	
	
	private String getResponseAsString(Object responseObject) throws JsonProcessingException {
		if (responseObject == null) {
			throw new RuntimeException("Response Object to be mapped is null");
		}
		return objectMapper.writeValueAsString(responseObject);
	}

}