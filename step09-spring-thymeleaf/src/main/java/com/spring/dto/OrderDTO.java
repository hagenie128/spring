package com.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * OrderDTO
 * 주문 폼 데이터를 전달받기 위한 객체 (커맨드 객체로 사용됨)
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDTO {
	// input name="customerName" 과 매칭
	private String customerName;
	// input name="quantity" 와 매칭
	private int quantity;
	// input name="requestMessage" 와 매칭
	private String requestMessage;
	// select name="menuId" 와 매칭
	private long menuId;
}




