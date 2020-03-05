package in.turls.lib.models.url;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.turls.lib.constants.UrlExpiryUnit;
import lombok.Data;

@Data
public class UrlExpiry {
	
	@Expose
	@SerializedName("unit")
	private UrlExpiryUnit unit;

	@Expose
	@SerializedName("value")
	private Integer value;

}
