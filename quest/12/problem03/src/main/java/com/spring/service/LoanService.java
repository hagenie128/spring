package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Book;
import com.spring.entity.Loan;
import com.spring.entity.LoanItem;
import com.spring.entity.LoanStatus;
import com.spring.repository.BookRepository;
import com.spring.repository.LoanRepository;
import com.spring.repository.StudentRepository;

@Service
@Transactional(readOnly = true)
public class LoanService {

	private final LoanRepository loanRepository;
	private final StudentRepository studentRepository;
	private final BookRepository bookRepository;

	public LoanService(LoanRepository loanRepository,
			StudentRepository studentRepository,
			BookRepository bookRepository) {
		this.loanRepository = loanRepository;
		this.studentRepository = studentRepository;
		this.bookRepository = bookRepository;
	}

	public List<Loan> search(Long studentId, LoanStatus status) {
		return loanRepository.search(studentId, status);
	}

	/**
	 * ━━ 그룹 A: TODO 1~5 ━━
	 *
	 * 1. Loan 생성 + student 조회 (없으면 IllegalArgumentException)
	 * 2. bookIds / quantities 루프 — 수량 0이면 continue
	 * 3. Book 조회 후 LoanItem 생성 → addLoanItem()
	 * 4. loanItems 비어 있으면 "책을 한 권 이상..." 예외
	 * 5. orderRepository.save(loan) 후 반환
	 *
	 * 힌트: step13 OrderService.save() 참고
	 */
	@Transactional
	public Loan save(Long studentId, List<Long> bookIds, List<Integer> quantities) {
		Loan loan = new Loan(studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다")));
		
		throw new UnsupportedOperationException("TODO 1~5 구현");
	}
}
