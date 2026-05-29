package com.spring.problem01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderService orderService = context.getBean("orderService", OrderService.class);
        orderService.order("김치볶음밥", 9000);
    }
}
