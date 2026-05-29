package com.spring.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingletonService {
	public SingletonService() {
		System.out.println("SingletonService 생성자");
	}
	
	@PreDestroy
	public void onDestroy() {
		System.out.println("SingletonService의 onDestroy 호출됨");
	}
}
