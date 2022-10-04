package com.serkanalgl.jwt.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootJwtStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtStarterApplication.class, args);
	}

}
