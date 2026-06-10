package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션의 시작점(Entry Point) 클래스입니다.
 *
 * @SpringBootApplication: 이 어노테이션은 다음 세 가지 핵심 설정을 하나로 합친 것입니다:
 * 1. @SpringBootConfiguration: Spring Boot 전용 설정 선언
 * 2. @EnableAutoConfiguration: 설정된 라이브러리(MyBatis, DB 등)를 바탕으로 Spring의 자동 설정을 활성화
 * 3. @ComponentScan: 현재 패키지와 하위 패키지에서 @Component, @Service, @Repository, @Controller 등을 찾아 Bean으로 등록
 */
@SpringBootApplication
public class Step11BootMybatisApplication {

	/**
	 * 자바 애플리케이션의 실행 시작점인 main 메서드입니다.
	 * SpringApplication.run()을 호출하여 내장 톰캣(Tomcat)을 구동하고 Spring 컨테이너를 초기화합니다.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Step11BootMybatisApplication.class, args);
	}

}
