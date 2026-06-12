package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Loan;
import com.spring.entity.LoanStatus;
import com.spring.repository.LoanRepository;
import com.spring.repository.StudentRepository;

@Service
@Transactional(readOnly = true)
public class LoanService {

	private final LoanRepository loanRepository;
	private final StudentRepository studentRepository;

	public LoanService(LoanRepository loanRepository, StudentRepository studentRepository) {
		this.loanRepository = loanRepository;
		this.studentRepository = studentRepository;
	}

	public List<Loan> findAllWithStudent() {
		return loanRepository.findAllWithStudent();
	}

	/**
	 * ━━ 그룹 A: TODO 3 ━━
	 * loanRepository.search(studentId, status) 호출
	 */
	public List<Loan> search(Long studentId, LoanStatus status) {
		// ???
		return List.of();
	}

	public List<com.spring.entity.Student> findAllStudents() {
		return studentRepository.findAll();
	}
}
