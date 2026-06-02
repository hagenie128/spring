package com.spring.review;

import java.util.UUID;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RequestTracker {
	private final String requestId = UUID.randomUUID().toString();

	public RequestTracker() {
		System.out.println("[RequestTracker] prototype bean 생성");
	}

	public String getRequestId() {
		return requestId;
	}
}
