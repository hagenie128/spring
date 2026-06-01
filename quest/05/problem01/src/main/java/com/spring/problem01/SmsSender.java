package com.spring.problem01;

import org.springframework.stereotype.Component;

@Component("smsSender")
public class SmsSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("[SmsSender] SMS 발송: " + message);
    }
}
