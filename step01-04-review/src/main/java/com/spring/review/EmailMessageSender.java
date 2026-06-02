package com.spring.review;

import org.springframework.stereotype.Component;

@Component("emailMessageSender")
public class EmailMessageSender implements MessageSender {

	@Override
	public void send(String message) {
		System.out.println("[EmailMessageSender] " + message);
	}
}
