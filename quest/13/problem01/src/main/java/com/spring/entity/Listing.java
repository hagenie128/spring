package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 판매글 — Post 역할 (당근마켓 중고거래 글)
 *
 * ━━ TODO 1, 2 ━━
 */
@Entity
@Table(name = "listing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO 1-1: 제목
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	// TODO 1-2: 가격 (nullable=false)
	private Integer price;

	// TODO 1-3: ListingCategory, SaleStatus, District — enum STRING
	private ListingCategory category;
	private SaleStatus saleStatus;
	private District district;

	// 2교시 전 임시 — 나중에 User @ManyToOne 으로 교체
	private String sellerNickname;

	/** 샘플·플레이스홀더용 — 3교시 ListingImage 로 대체 */
	private String thumbnailEmoji;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	// TODO 2: @PrePersist
	public void onCreate() {
		// ???
	}
}
