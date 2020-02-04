package in.turls.lib.models.url;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("urls")
@ToString
@Getter
@RequiredArgsConstructor
public class URLEntity {
	
	@Id
	private String id;
	
	@NonNull
	private final String originalUrl;
	
	@Setter
	@Indexed(unique = true)
	private String shortUrlKey;
	
	private Date createdAt = new Date();
	
	@Setter
	private Date updatedAt = new Date();
	

}
