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

	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LoanItem> loanItems = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		this.loanDate = LocalDateTime.now();
	}

	public void addLoanItem(LoanItem item) {
		loanItems.add(item);
		item.setLoan(this);
	}
}
