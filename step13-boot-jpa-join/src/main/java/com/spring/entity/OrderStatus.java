package com.spring.entity;

// 주문 진행 상태를 제한된 값으로 관리하기 위한 enum입니다.
public enum OrderStatus {
	PENDING("대기중"),
	CONFIRMED("확정"),
	COMPLETED("완료"),
	CANCELLED("취소");
	
	private final String label;

	private OrderStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
