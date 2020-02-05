package in.turls.lib.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.turls.lib.filters.IPUsageLimitFilter;

@Configuration
public class FilterConfiguration {
	
	@Bean
	public FilterRegistrationBean<IPUsageLimitFilter> ipUsageLimitFilterRegistrationBean() {
		
		FilterRegistrationBean<IPUsageLimitFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(ipUsageLimitFilter());
		registrationBean.addUrlPatterns("/v1/url/*");
		registrationBean.setName("ipUsageLimitFiler");
		return registrationBean;
	}
	
	
	@Bean
	public IPUsageLimitFilter ipUsageLimitFilter() {
		return new IPUsageLimitFilter();
	}

}
