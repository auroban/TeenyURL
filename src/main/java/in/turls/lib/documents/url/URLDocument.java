package in.turls.lib.documents.url;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.turls.lib.constants.UrlStatus;
import in.turls.lib.documents.AbstractDocument;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("urls")

@Getter
@ToString
@RequiredArgsConstructor
public class URLDocument extends AbstractDocument {
	
	@NotNull
	private final String originalUrl;
	
	@Setter
	@Indexed(unique = true)
	private String shortUrlKey;
	
	@Setter
	@Indexed
	private UrlStatus status;
	
	private Date createdAt = new Date();
	
	@Setter
	@Field
	@Indexed
	@NonNull
	@NotNull
	private Date validTill;
	
	@Setter
	private Date expiredAt;

}
