package com.spring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {
	// 주문 테이블의 기본키입니다.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Order(N) : Member(1) 관계입니다. 실제 FK 컬럼은 orders.member_id로 생성됩니다.
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	// 주문 생성 시각입니다. @PrePersist에서 INSERT 직전에 값이 들어갑니다.
	@Column(name = "order_date", updatable = false)
	private LocalDateTime orderDate;

	// EnumType.STRING은 DB에 enum 순번이 아니라 PENDING 같은 문자열을 저장합니다.
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OrderStatus status = OrderStatus.PENDING;

	// Order(1) : OrderItem(N) 관계입니다. 주문 저장 시 주문 항목도 함께 저장됩니다.
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		this.orderDate = LocalDateTime.now();
	}

	// 총 금액 계산입니다. DB 컬럼이 아니라 화면/비즈니스 로직에서 계산해서 사용하는 값입니다.
	public int getTotalPrice() {
		return orderItems.stream()
				.mapToInt(item -> item.getUnitPrice() * item.getQuantity())
				.sum();
	}

	// 연관관계 편의 메서드입니다. 양방향 관계 양쪽(orderItems, item.order)을 동시에 맞춥니다.
	public void addOrderItem(OrderItem item) {
		orderItems.add(item);
		item.setOrder(this);
	}

}
