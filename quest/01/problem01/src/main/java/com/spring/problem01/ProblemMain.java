package com.spring.problem01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        // pom.xml에 spring-context 의존성이 없으면 이 줄에서 컴파일 오류가 발생합니다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        GreeterBean greeter = context.getBean("greeterBean", GreeterBean.class);
        greeter.greet();

        ((ClassPathXmlApplicationContext) context).close();
    }
}
