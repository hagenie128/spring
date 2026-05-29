package com.spring.problem04;

import org.springframework.stereotype.Component;

// TODO: @Component("emailNotifier")를 추가하세요.
@Component("emailNotifier")
public class EmailNotifier implements Notifier {
    @Override
    public void send(String memberId) {
        System.out.println("[EmailNotifier] " + memberId + " 가입 알림 전송");
    }
}
