package in.turls.lib.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class IPUsageLimitFilter implements Filter {
	
	private static final Logger LOG = LogManager.getLogger(IPUsageLimitFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = HttpServletRequest.class.cast(request);
		LOG.info("Checking usgae limit for IP: {}", httpServletRequest.getRemoteAddr());
		chain.doFilter(httpServletRequest, response);
		

	}

}
