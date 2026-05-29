package com.spring.constructor;

public class OrderRepository {
	public void save(String orderInfo) {
		System.out.println("[OrderRepository] 주문 저장 : "+orderInfo);
	}
}
