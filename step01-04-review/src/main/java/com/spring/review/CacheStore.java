package com.spring.review;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class CacheStore {

	public CacheStore() {
		System.out.println("[CacheStore] 생성자 호출");
	}

	@PostConstruct
	public void load() {
		System.out.println("[CacheStore] @PostConstruct - 캐시 로딩");
	}

	@PreDestroy
	public void clear() {
		System.out.println("[CacheStore] @PreDestroy - 캐시 정리");
	}
}
