package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	/**
	 * ━━ 그룹 B: TODO 5 ━━
	 *
	 * 학생(Student)을 JOIN FETCH로 함께 조회합니다.
	 * 힌트: select l from Loan l join fetch l.student order by l.loanDate desc
	 */
	@Query("SELECT l FROM Loan l ORDER BY l.loanDate DESC")
	List<Loan> findAllWithStudent();
}
