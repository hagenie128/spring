package com.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Book;
import com.spring.entity.Loan;
import com.spring.entity.LoanItem;
import com.spring.entity.LoanStatus;
import com.spring.entity.Student;
import com.spring.repository.BookRepository;
import com.spring.repository.LoanRepository;
import com.spring.repository.StudentRepository;

/**
 * 샘플 데이터 — 그룹 A(TODO 1~4)를 완성하면 대출 3건이 저장됩니다.
 */
@Component
public class DataInitializer implements CommandLineRunner {

	private final StudentRepository studentRepository;
	private final BookRepository bookRepository;
	private final LoanRepository loanRepository;

	public DataInitializer(StudentRepository studentRepository,
			BookRepository bookRepository,
			LoanRepository loanRepository) {
		this.studentRepository = studentRepository;
		this.bookRepository = bookRepository;
		this.loanRepository = loanRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (studentRepository.count() > 0) {
			return;
		}

		Student s1 = studentRepository.save(new Student("김민수", "2024001", "minsu@school.ac.kr"));
		Student s2 = studentRepository.save(new Student("이서연", "2024002", "seoyeon@school.ac.kr"));
		studentRepository.save(new Student("박지훈", "2024003", "jihoon@school.ac.kr"));

		Book b1 = bookRepository.save(new Book("스프링 부트 실전", "김영한", "978-1-111"));
		Book b2 = bookRepository.save(new Book("JPA 프로그래밍", "김영한", "978-2-222"));
		Book b3 = bookRepository.save(new Book("해리포터와 마법사의 돌", "J.K.롤링", "978-3-333"));
		Book b4 = bookRepository.save(new Book("1984", "조지 오웰", "978-4-444"));
		Book b5 = bookRepository.save(new Book("클린 코드", "로버트 마틴", "978-5-555"));

		Loan loan1 = new Loan(s1);
		loan1.addLoanItem(new LoanItem(b1, 1));
		loan1.addLoanItem(new LoanItem(b3, 2));
		loan1.setStatus(LoanStatus.RETURNED);
		loanRepository.save(loan1);

		Loan loan2 = new Loan(s2);
		loan2.addLoanItem(new LoanItem(b2, 1));
		loan2.addLoanItem(new LoanItem(b4, 1));
		loan2.setStatus(LoanStatus.BORROWED);
		loanRepository.save(loan2);

		Loan loan3 = new Loan(s1);
		loan3.addLoanItem(new LoanItem(b5, 1));
		loan3.setStatus(LoanStatus.OVERDUE);
		loanRepository.save(loan3);
	}
}
