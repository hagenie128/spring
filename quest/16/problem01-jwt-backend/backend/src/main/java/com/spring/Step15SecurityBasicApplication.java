package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Step15SecurityBasicApplication {

	// 애플리케이션의 시작점이다. Spring Boot가 필요한 설정을 읽고 내장 웹 서버를 실행한다.
	public static void main(String[] args) {
		SpringApplication.run(Step15SecurityBasicApplication.class, args);
	}

}
