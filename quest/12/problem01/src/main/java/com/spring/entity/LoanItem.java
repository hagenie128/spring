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

/**
 * 대출 도서 한 줄 — step13의 OrderItem과 같은 역할
 *
 * ━━ 그룹 A: TODO 1 ━━
 */
@Entity
@Table(name = "loan_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO 1: Loan과 @ManyToOne 관계를 설정하세요.
	//   힌트: fetch = LAZY, @JoinColumn(name = "loan_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="loan_id",nullable = false)
	private Loan loan;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@NonNull
	@Column(nullable = false)
	private Integer quantity;
}
