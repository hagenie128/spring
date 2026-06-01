package com.spring.life_cycle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
//@Scope("prototype")
@Scope("single")
public class CacheManager {
	public CacheManager() {
		System.out.println("[CacheManager] 생성자 - 인스턴스화");
	}
	
	@PostConstruct
	public void initCache() {
		System.out.println("[CacheManager] PostConstruct");
	}
	
	@PreDestroy
	public void clearCache() {
		System.out.println("[CacheManager] PreDestroy");
	}
	
	
}
