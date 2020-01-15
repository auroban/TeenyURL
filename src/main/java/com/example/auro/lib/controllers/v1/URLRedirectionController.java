package com.example.auro.lib.controllers.v1;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.auro.lib.services.interfaces.URLManagerService;

@RestController("/")
public class URLRedirectionController {

	private static final Logger LOG = LogManager.getLogger(URLRedirectionController.class);

	@Autowired
	private URLManagerService urlManagerService;

	@GetMapping
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Hey There!! Nothing for you here I guess", HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ModelAndView redirect(@PathVariable("id") String id, ServletRequest servletRequest,
			ServletResponse servletResponse) {
		HttpServletRequest request = HttpServletRequest.class.cast(servletRequest);
		LOG.info("Redirection request from: {} for Short URL Key: {}", request.getRemoteAddr(), id);
		try {
			Optional<String> originalUrlOptional = urlManagerService.retrieveOriginalUrl(id);
			if (originalUrlOptional.isPresent() && !StringUtils.isEmpty(originalUrlOptional.get())) {
				LOG.info("Found Original URL: {} for Short URL Key: {}", originalUrlOptional.get(), id);
				return new ModelAndView("redirect:https://" + originalUrlOptional.get());
			}

		} catch (NoSuchElementException e) {
			LOG.error("Error while redirecting: {}", e.getMessage(), e);
		}
		return new ModelAndView("redirect:/notfound");
	}

	@GetMapping("/notfound")
	public ResponseEntity<String> noResourceFound() {
		LOG.info("Resource not found");
		return new ResponseEntity<String>("The Resource you're looking for could not be found", HttpStatus.NOT_FOUND);
	}

}
