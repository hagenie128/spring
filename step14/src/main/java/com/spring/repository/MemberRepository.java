package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Member;

/**
 * [회원 DB 접근 Repository]
 *
 * JpaRepository 만 상속해도 save, findAll, findById, deleteById 등 사용 가능.
 * 나중에 findByUsername(String username) 처럼 메서드 이름만 추가해도
 * Spring Data JPA 가 쿼리를 자동 생성해 줍니다 (쿼리 메서드).
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

}
