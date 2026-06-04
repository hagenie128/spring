package com.spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {
	private String customerName;
	private long menuId;
	private int quantity;
	private String requestMessage;
}
