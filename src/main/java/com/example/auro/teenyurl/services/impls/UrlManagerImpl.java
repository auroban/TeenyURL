package com.example.auro.teenyurl.services.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auro.teenyurl.repositories.URLRepository;
import com.example.auro.teenyurl.services.interfaces.URLManager;


@Service
public class UrlManagerImpl implements URLManager {
	
	@Override
	public void addUrl(String longUrl) throws Exception {
		
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<String> findUrl(String urlId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Boolean> deleteUrl(String urlId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> findWithLongUrl(String longUrl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
