package com.spring.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA 엔티티 — menu_item 테이블과 매핑
 *
 * @Entity  : 이 클래스가 JPA가 관리하는 엔티티임을 선언
 * @Table   : 매핑할 DB 테이블명 지정
 * @Id     : PK 필드 선언
 * @GeneratedValue(strategy = IDENTITY) : AUTO_INCREMENT 사용
 * @Column  : 컬럼 속성 설정 (nullable, length, name 등)
 * @PrePersist : INSERT 직전 자동 실행 (createdAt 자동 설정)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="menu_item")
public class MenuDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	@NotBlank(message="메뉴명을 입력하세요")
	private String name;
	
	@Column(nullable = false)
	@NotNull(message = "가격을 입력하세요")
	@Min(value = 0, message = "가격은 100원 이상이어야 합니다")
	private Integer price;
	
	@Column(length = 50)
	private String category;
	
	@Column(length = 500)
	private String description;
	
	@Column
	private boolean available=true;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}


	public MenuDTO(@NotBlank(message = "메뉴명을 입력하세요") String name,
			@NotNull(message = "가격을 입력하세요") @Min(value = 0, message = "가격은 100원 이상이어야 합니다") Integer price,
			String category, String description, boolean available) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
		this.available = available;
	}
	
}
