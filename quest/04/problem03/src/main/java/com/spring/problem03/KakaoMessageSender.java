package com.spring.problem03;

import org.springframework.stereotype.Component;

// TODO: @Component("kakaoMessageSender")를 추가하세요.
@Component("kakaoMessageSender")
public class KakaoMessageSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("[KakaoMessageSender] " + message);
    }
}
