package com.spring.problem01;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// TODO: @Primary 어노테이션을 추가하여 EmailSender가 기본 구현체로 선택되도록 하라.
@Component("emailSender")
@Primary
public class EmailSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("[EmailSender] 이메일 발송: " + message);
    }
}
