package com.spring.problem03;

import org.springframework.stereotype.Component;

// TODO: @Component("smsMessageSender")를 추가하세요.
@Component("smsMessageSender")
public class SmsMessageSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("[SmsMessageSender] " + message);
    }
}
