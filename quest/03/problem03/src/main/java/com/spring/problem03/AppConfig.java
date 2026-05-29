package com.spring.problem03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java Config 설정 클래스.
 *
 * TODO 1: 이 클래스가 Spring 설정 클래스임을 선언하는 어노테이션을 추가하세요.
 *         힌트: @_____________
 *
 * TODO 2: memberRepository() 메서드가 Spring Bean을 반환하도록 어노테이션을 추가하세요.
 *         힌트: @_____________
 *
 * TODO 3: memberService() 메서드가 Spring Bean을 반환하도록 어노테이션을 추가하세요.
 *         MemberService 생성자에 memberRepository() 를 전달해야 합니다.
 *         힌트: @_____________
 */
// TODO 1: @Configuration 추가
@Configuration
public class AppConfig {

    // TODO 2: @Bean 추가
	@Bean
    public MemberRepository memberRepository() {
        return new MemberRepository();
    }

    // TODO 3: @Bean 추가, memberRepository() 전달
	@Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
}
