package in.turls.lib.models.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiRequest {
	
	@JsonProperty("url")
	private String longUrl;
	
}
