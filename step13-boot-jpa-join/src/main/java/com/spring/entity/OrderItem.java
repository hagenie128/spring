package com.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderItem {
	// 주문 항목 테이블의 기본키입니다.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// OrderItem(N) : Order(1) 관계입니다. 같은 주문에 여러 메뉴 항목이 들어갈 수 있습니다.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	// OrderItem(N) : MenuItem(1) 관계입니다. 주문 당시 어떤 메뉴를 담았는지 연결합니다.
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_item_id", nullable = false)
	private MenuItem menuItem;
	
	// 주문 수량입니다.
	@NonNull
	@Column(nullable = false)
	private Integer quantity;
	
	// 주문 당시의 단가입니다. 메뉴 가격이 나중에 바뀌어도 기존 주문 금액을 보존합니다.
	@NonNull
	@Column(name = "unit_price", nullable = false)
	private Integer unitPrice;
	
}







