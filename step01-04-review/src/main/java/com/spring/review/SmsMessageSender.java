package com.spring.review;

import org.springframework.stereotype.Component;

@Component("smsMessageSender")
public class SmsMessageSender implements MessageSender {

	@Override
	public void send(String message) {
		System.out.println("[SmsMessageSender] " + message);
	}
}
