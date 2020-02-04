package in.turls.lib.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RequestResponseLoggingFilter implements Filter {
	
	private static final Logger LOG = LogManager.getLogger(RequestResponseLoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = HttpServletRequest.class.cast(request);
		HttpServletResponse httpResponse = HttpServletResponse.class.cast(response);
		
		LOG.info("Logging Incoming Request at {} from {}", httpRequest.getRequestURI(), httpRequest.getRemoteAddr());
		chain.doFilter(request, response);
		LOG.info("Logging Outgoing Response with HTTP status: {}", httpResponse.getStatus());		
	}

}
