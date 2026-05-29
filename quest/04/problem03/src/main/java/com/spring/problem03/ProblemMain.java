package com.spring.problem03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MessageService messageService = context.getBean("messageService", MessageService.class);
        messageService.sendPickupMessage();
    }
}
