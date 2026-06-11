package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByEmail(String email);
	
	List<Member> findAllByOrderByCreatedAtDesc();
	
	@Query("select distinct m from Member m left join fetch m.orders order by m.createdAt desc")
	List<Member> findAllWithOrders();
}






