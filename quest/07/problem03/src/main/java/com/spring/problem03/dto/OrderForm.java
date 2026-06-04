package com.spring.problem03.dto;


public class OrderForm {

    // TODO 1: customerName, menuName, quantity, requestMessage 필드 작성
	private String customerName;
	private String menuName;
	private int quantity;
	private String requestMessage;
	// TODO 2: 기본 생성자 작성
	public OrderForm() {
	}
	// TODO 3: 모든 필드의 getter/setter 작성
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	
	
}

