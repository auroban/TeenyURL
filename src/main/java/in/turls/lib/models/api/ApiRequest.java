package in.turls.lib.models.api;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiRequest {
	
	@JsonProperty("url")
	@NotNull
	@Length(min = 8, max = 200)
	private String longUrl;	
}
