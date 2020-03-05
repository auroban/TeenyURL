package in.turls.lib.documents;


import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class AbstractDocument {

	@Id
	private String id;
	
}
