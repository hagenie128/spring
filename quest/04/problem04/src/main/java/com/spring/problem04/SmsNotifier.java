package com.spring.problem04;

import org.springframework.stereotype.Component;

// TODO: @Component("smsNotifier")를 추가하세요.
@Component("smsNotifier")
public class SmsNotifier implements Notifier {
    @Override
    public void send(String memberId) {
        System.out.println("[SmsNotifier] " + memberId + " 가입 알림 전송");
    }
}
