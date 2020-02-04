package in.turls.lib.controllers.v1;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletRequest;
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

import in.turls.lib.services.interfaces.URLManagerService;

@RestController("/")
public class URLRedirectionController {

	private static final Logger LOG = LogManager.getLogger(URLRedirectionController.class);

	@Autowired
	private URLManagerService urlManagerService;

	@GetMapping
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Hey There!! Nothing for you here I guess", HttpStatus.OK);
	}

	@GetMapping("{shortUrlKey}")
	public ModelAndView redirect(@PathVariable("shortUrlKey") final String shortUrlKey, ServletRequest serveRequest) {
		HttpServletRequest request = HttpServletRequest.class.cast(serveRequest);
		LOG.info("Redirection request coming from {} for Short URL Key: {}", request.getRemoteAddr(), shortUrlKey);
		try {
			Optional<String> originalUrl = urlManagerService.retrieveOriginalUrl(shortUrlKey);
			if (originalUrl.isPresent() && StringUtils.hasText(originalUrl.get())) {
				LOG.info("Redirecting to Original URL: " + originalUrl.get());
				return new ModelAndView("redirect:" + originalUrl.get());
			}
		} catch (NoSuchElementException e) {
			LOG.error("Error while redirecting: {}", e.getMessage(), e);
		}
		return new ModelAndView("redirect:/notfound");
	}

	@GetMapping("/notfound")
	public ResponseEntity<String> noResourceFound() {
		LOG.error("Resource not found");
		return new ResponseEntity<String>("The Resource you're looking for could not be found", HttpStatus.NOT_FOUND);
	}

}
