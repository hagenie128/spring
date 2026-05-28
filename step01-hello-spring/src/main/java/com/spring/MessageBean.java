package com.spring;

public class MessageBean {
	private String message;

	public MessageBean() {
		this.message = "Hello Spring!";
	}

	public MessageBean(String message) {
		this.message = message;
	}
	
	public void printMessage() {
		System.out.println("[MessageBean]"+this.message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
