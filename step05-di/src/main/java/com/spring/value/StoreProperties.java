package com.spring.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:store.properties")
public class StoreProperties {
	
	@Value("${store.name}")
	private String storeName; 
	
	@Value("${store.openTime}")
	private String openTime; 
	
	@Value("${store.maxOrder}")
	private int maxOrder; 
	
	public void printInfo() {
		System.out.println("가게 이름: " + storeName);
		System.out.println("영업 시간: " + openTime);
		System.out.println("최대 주문 수: " + maxOrder);
	}
}
