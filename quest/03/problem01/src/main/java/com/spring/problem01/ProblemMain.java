package com.spring.problem01;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        FileHandler handler = context.getBean("fileHandler", FileHandler.class);
        handler.processFile("report.txt");

        System.out.println("\n▶ 컨테이너 종료");
        context.close();
    }
}
