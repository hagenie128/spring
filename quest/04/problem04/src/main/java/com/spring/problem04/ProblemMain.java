package com.spring.problem04;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProblemMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context 
        		= new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = context.getBean("memberService", MemberService.class);
        memberService.join("user01", "1234");
        ((AnnotationConfigApplicationContext) context).close();
    }
}