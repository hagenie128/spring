package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션의 시작점입니다.
 * @SpringBootApplication: 이 어노테이션은 다음 세 가지를 포함합니다.
 * 1. @SpringBootConfiguration: 스프링 부트 설정 선언
 * 2. @EnableAutoConfiguration: 자동 설정 활성화 (프로젝트 설정을 자동으로 구성)
 * 3. @ComponentScan: 현재 패키지 및 하위 패키지에서 빈(Bean)을 찾아 등록
 */
@SpringBootApplication
public class Step10BootBasicApplication {

    public static void main(String[] args) {
        // 애플리케이션을 구동합니다.
        SpringApplication.run(Step10BootBasicApplication.class, args);
    }

}
