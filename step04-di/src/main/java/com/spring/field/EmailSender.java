package com.spring.field;

import org.springframework.stereotype.Component;

@Component
public class EmailSender implements MessageSender{

	@Override
	public void send(String message) {
		System.out.println("EmailSender : "+message);
	}

}
