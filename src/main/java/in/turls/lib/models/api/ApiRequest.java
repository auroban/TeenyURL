package in.turls.lib.models.api;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.turls.lib.models.url.UrlExpiry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiRequest {
	
	@Expose
	@SerializedName("url")
	@NotNull
	@Length(min = 8, max = 200)
	private String longUrl;	
	
	@Expose
	@SerializedName("expiry")
	private UrlExpiry urlExpiry;
}
