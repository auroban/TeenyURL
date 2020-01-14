package com.example.auro.teenyurl.models.url;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@ToString
@Getter
@Setter
public class URLEntity {
	
	@Id
	private Long id;
	
	private String originalUrl;
	private String shortUrl;
	private Date createdAt;
	private Date updatedAt;
	

}
