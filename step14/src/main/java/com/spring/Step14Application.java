package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * [Spring Boot 애플리케이션 시작점]
 *
 * main() 이 실행되면 내장 Tomcat 서버가 뜨고,
 * com.spring 패키지 아래의 @Controller, @Service, @Repository 등을 자동으로 찾아 등록합니다.
 *
 * [@SpringBootApplication 이 하는 일 (세 가지를 한 번에)]
 *  1. @Configuration      — 이 클래스를 설정 클래스로 등록
 *  2. @EnableAutoConfiguration — classpath 에 있는 라이브러리에 맞춰 자동 설정
 *     (예: JPA 있으면 DataSource 자동 구성, web 있으면 Tomcat 자동 구성)
 *  3. @ComponentScan      — com.spring 하위 패키지를 스캔해서 @Component 계열 빈 등록
 */
@SpringBootApplication
public class Step14Application {

	public static void main(String[] args) {
		SpringApplication.run(Step14Application.class, args);
	}

}
