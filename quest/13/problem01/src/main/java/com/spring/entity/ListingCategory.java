package com.spring.entity;

public enum ListingCategory {
	DIGITAL("디지털"),
	FURNITURE("가구"),
	CLOTHING("의류"),
	BOOK("도서"),
	LIVING("생활용품"),
	ETC("기타");

	private final String label;

	ListingCategory(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
