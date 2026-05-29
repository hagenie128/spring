package com.spring.problem04;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.spring.problem04")
public class AppConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder("SHA-256");
	}
}