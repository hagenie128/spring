package com.spring.entity;

public enum LoanStatus {
	BORROWED("대출중"),
	RETURNED("반납완료"),
	OVERDUE("연체");

	private final String label;

	LoanStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
