package com.spring.problem03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

// TODO: @Service를 추가하세요.
@Service
public class MessageService {
    // TODO: @Autowired와 @Qualifier("kakaoMessageSender")를 추가하세요.
	@Autowired
	@Qualifier("kakaoMessageSender")
    private MessageSender messageSender;

    public void sendPickupMessage() {
        messageSender.send("픽업 준비가 완료되었습니다.");
        System.out.println("[MessageService] 메시지 전송 완료");
    }
}
