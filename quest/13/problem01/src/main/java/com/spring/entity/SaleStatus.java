package com.spring.entity;

public enum SaleStatus {
	SELLING("판매중"),
	RESERVED("예약중"),
	SOLD("판매완료");

	private final String label;

	SaleStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
