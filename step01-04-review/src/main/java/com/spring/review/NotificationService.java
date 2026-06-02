package com.spring.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	@Qualifier("smsMessageSender")
	private MessageSender messageSender;

	public void sendNotification(String message) {
		messageSender.send(message);
		System.out.println("[NotificationService] 알림 처리 완료");
	}
}
