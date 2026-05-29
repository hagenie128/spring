package com.spring.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	@Autowired //자동 연결
	@Qualifier("smsSender") // 우선 선정
	private MessageSender messageSender;
	
	public void notifyOrder(String message) {
		messageSender.send(message);
		System.out.println("[NotificationService] 알림처리 완료");
	}
}
