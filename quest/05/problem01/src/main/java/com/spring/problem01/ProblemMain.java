package com.spring.problem01;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        AlertService alertService = ctx.getBean(AlertService.class);
        alertService.notify("주문 완료");

        ctx.close();
    }
}
