package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Loan;
import com.spring.entity.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	@Query("SELECT l FROM Loan l JOIN FETCH l.student ORDER BY l.loanDate DESC")
	List<Loan> findAllWithStudent();

	/**
	 * ━━ 그룹 A: TODO 1~2 ━━
	 *
	 * studentId / status 가 null이면 해당 조건을 무시합니다.
	 * 힌트: (:studentId IS NULL OR s.id = :studentId) AND (:status IS NULL OR l.status = :status)
	 */
	// TODO 1~2: WHERE 절에 null 조건 추가 (지금은 전체 조회 — 완성 전 테스트용)
	@Query("SELECT l FROM Loan l JOIN FETCH l.student s where (:studentId IS NULL OR s.id = :studentId) AND (:status IS NULL OR l.status = :status) ORDER BY l.loanDate DESC")
	List<Loan> search(@Param("studentId") Long studentId, @Param("status") LoanStatus status);
}
