package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션의 메인 클래스입니다.
 *
 * @SpringBootApplication: Spring Boot의 핵심 어노테이션으로, 다음 세 기능을 수행합니다:
 * 1. @SpringBootConfiguration: Spring 설정을 담당하는 클래스임을 선언
 * 2. @EnableAutoConfiguration: 클래스패스에 있는 라이브러리를 바탕으로 Bean을 자동 설정 (예: JPA, Thymeleaf 등)
 * 3. @ComponentScan: 현재 패키지 및 하위 패키지에서 @Component, @Service, @Controller 등을 찾아 Bean으로 등록
 */
@SpringBootApplication
public class Step12BootJpaApplication {

	/**
	 * 애플리케이션을 구동하는 진입점(main)입니다.
	 * SpringApplication.run()은 내장 WAS(Tomcat)를 실행하고 Spring 컨테이너를 초기화합니다.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Step12BootJpaApplication.class, args);
	}

}
