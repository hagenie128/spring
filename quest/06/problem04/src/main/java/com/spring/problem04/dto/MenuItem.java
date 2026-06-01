package com.spring.problem04.dto;

/**
 * [완성된 파일 — 수정 불필요] 메뉴 항목 DTO (Data Transfer Object)
 * 
 * DTO란?
 * 계층 간(예: Controller와 View) 데이터 전송을 위해 사용하는 객체입니다.
 * 주로 여러 개의 데이터를 하나로 묶어서 전달할 때 사용합니다.
 * 
 * Spring MVC의 '커맨드 객체' 역할:
 * HTML 폼의 <input name="name"> 태그에 입력된 값이 
 * 이 객체의 setName() 메서드를 통해 자동으로 주입됩니다.
 */
public class MenuItem {

	private String name; // 메뉴 이름
	private int price;   // 메뉴 가격

	// 모든 필드를 초기화하는 생성자
	public MenuItem(String name, int price) {
		this.name = name;
		this.price = price;
	}

	// 기본 생성자 (Spring이 객체를 생성할 때 필요함)
	public MenuItem() {
	}

	// Getter/Setter: 필드 값을 읽거나 쓰기 위한 메서드
	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	// 객체의 정보를 문자열로 반환 (디버깅용)
	@Override
	public String toString() {
		return "MenuItem [name=" + name + ", price=" + price + "]";
	}

}