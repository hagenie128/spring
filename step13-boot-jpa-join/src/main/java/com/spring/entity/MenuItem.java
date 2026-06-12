package com.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "menu_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MenuItem {
	// 메뉴 테이블의 기본키입니다.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 메뉴명, 가격, 카테고리는 샘플 데이터 생성자에서 반드시 받는 값입니다.
	@Column(nullable = false, length = 100)
	@NonNull
	private String name;

	@Column(nullable = false)
	@NotNull(message = "가격을 입력하세요")
	@Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
	private Integer price;

	@Column(length = 50)
	private String category;

	// 판매 가능 여부입니다. 새 메뉴는 기본적으로 판매 가능 상태로 생성됩니다.
	@Column(nullable = false)
	private boolean available = true;

	public MenuItem(String name,
			@NotNull(message = "가격을 입력하세요") @Min(value = 100, message = "가격은 100원 이상이어야 합니다.") Integer price,
			String category) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
	}
}
