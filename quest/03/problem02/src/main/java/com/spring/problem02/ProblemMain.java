package com.spring.problem02;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        EmailConnectionPool pool = context.getBean("emailConnectionPool", EmailConnectionPool.class);
        pool.sendEmail("admin@example.com");

        System.out.println("\n▶ 컨테이너 종료");
        context.close();
    }
}
