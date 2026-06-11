package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.entity.Member;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
    
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(@Param("email") String email);

    List<Member> findAllByOrderByCreatedAtDesc();
    
    @Query("select distinct m from Member m left join fetch m.orders order by m.createdAt desc")
    List<Member> findAllWithOrders();
}
