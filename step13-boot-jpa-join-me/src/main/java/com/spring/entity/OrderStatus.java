package com.spring.entity;

public enum OrderStatus {
    PENDING("대기중"),
	CONFIRMED("확정"),
	COMPLETED("완료"),
	CANCELLED("취소");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
