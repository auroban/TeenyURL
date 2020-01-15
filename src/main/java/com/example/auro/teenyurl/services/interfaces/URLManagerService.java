package com.example.auro.teenyurl.services.interfaces;

import java.util.Optional;

public interface URLManagerService {
	
	public Optional<String> addUrl(final String longUrl) throws Exception;
	public Optional<String> findUrl(final String urlId) throws Exception;
	public Optional<Boolean> deleteUrl(final String urlId) throws Exception;
	public Optional<String> findWithLongUrl(final String longUrl) throws Exception;

}
