package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Loan;
import com.spring.entity.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	@Query("SELECT l FROM Loan l JOIN FETCH l.student ORDER BY l.loanDate DESC")
	List<Loan> findAllWithStudent();

	@Query("SELECT l FROM Loan l JOIN FETCH l.student s "
			+ "WHERE (:studentId IS NULL OR s.id = :studentId) "
			+ "AND (:status IS NULL OR l.status = :status) "
			+ "ORDER BY l.loanDate DESC")
	List<Loan> search(@Param("studentId") Long studentId, @Param("status") LoanStatus status);
}
