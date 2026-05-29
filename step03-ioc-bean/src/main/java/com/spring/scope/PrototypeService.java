package com.spring.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeService {
	
	public PrototypeService() {
		System.out.println("PrototypeService 생성자 호출");
	}
	
	@PreDestroy
	public void onDestroy() {
		System.out.println("PrototypeService onDestroy()");
	}
}
