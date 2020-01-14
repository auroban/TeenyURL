package com.example.auro.teenyurl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.auro.teenyurl.repositories.URLRepository;

@SpringBootApplication
public class TeenyUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeenyUrlApplication.class, args);
	}

}
