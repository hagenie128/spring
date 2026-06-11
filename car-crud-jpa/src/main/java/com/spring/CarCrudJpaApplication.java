package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * car-crud-jpa Spring Boot 애플리케이션의 시작 클래스입니다.
 */
@SpringBootApplication
public class CarCrudJpaApplication {

	/**
	 * 내장 Tomcat과 Spring 컨테이너를 실행합니다.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CarCrudJpaApplication.class, args);
	}
}
