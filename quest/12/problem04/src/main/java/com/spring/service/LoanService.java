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

	public Loan findByIdWithDetails(Long id) {
		// TODO 3: findByIdWithDetails().orElseThrow(...)
		throw new UnsupportedOperationException("TODO 3");
	}

	@Transactional
	public Loan save(Long studentId, List<Long> bookIds, List<Integer> quantities) {
		Loan loan = new Loan();
		loan.setStudent(studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalArgumentException("없는 학생입니다")));
		for (int i = 0; i < bookIds.size(); i++) {
			if (quantities.get(i) == 0) {
				continue;
			}
			Book book = bookRepository.findById(bookIds.get(i))
					.orElseThrow(() -> new IllegalArgumentException("없는 도서입니다"));
			loan.addLoanItem(new LoanItem(book, quantities.get(i)));
		}
		if (loan.getLoanItems().isEmpty()) {
			throw new IllegalArgumentException("책을 한 권 이상 선택하세요");
		}
		return loanRepository.save(loan);
	}

	/**
	 * ━━ TODO 4 ━━
	 * 조회한 Loan의 status만 변경 (dirty checking)
	 */
	@Transactional
	public void updateStatus(Long id, LoanStatus status) {
		// ???
	}

	/**
	 * ━━ TODO 5 ━━
	 * 대출만 삭제 (Book은 삭제하면 안 됨!)
	 * 힌트: loanRepository.deleteById(id)
	 */
	@Transactional
	public void delete(Long id) {
		// ???
	}
}
