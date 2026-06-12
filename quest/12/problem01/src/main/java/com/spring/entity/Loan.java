package com.spring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 대출 엔티티 — step13의 Order와 같은 역할
 *
 * ━━ 그룹 A: TODO 2, 3, 4 ━━
 */
@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@Column(name = "loan_date", updatable = false)
	private LocalDateTime loanDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private LoanStatus status = LoanStatus.BORROWED;

	// TODO 2: LoanItem과 @OneToMany 관계를 설정하세요.
	//   힌트: mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true
	private List<LoanItem> loanItems = new ArrayList<>();

	// TODO 4: @PrePersist 를 붙이고, INSERT 직전에 loanDate에 현재 시각을 넣으세요.
	public void setLoanDateOnCreate() {
		// ???
	}

	// TODO 3: 양방향 연관관계 편의 메서드를 작성하세요.
	//   힌트: loanItems.add(item) + item.setLoan(this)
	public void addLoanItem(LoanItem item) {
		// ???
	}
}
