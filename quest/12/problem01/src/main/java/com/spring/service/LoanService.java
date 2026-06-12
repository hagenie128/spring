package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Loan;
import com.spring.repository.LoanRepository;

@Service
@Transactional(readOnly = true)
public class LoanService {

	private final LoanRepository loanRepository;

	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	/**
	 * ━━ 그룹 B: TODO 6 ━━
	 * Repository의 findAllWithStudent()를 호출하세요.
	 */
	public List<Loan> findAllWithStudent() {
		// ???
		return List.of();
	}
}
