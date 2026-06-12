package com.spring.entity;

public enum District {
	GANGNAM("강남구"),
	MAPO("마포구"),
	SEONGSU("성동구"),
	JONGNO("종로구"),
	ETC("기타");

	private final String label;

	District(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
