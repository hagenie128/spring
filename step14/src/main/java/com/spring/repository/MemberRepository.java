package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Member;

/**
 * [회원 DB 접근 Repository]
 *
 * JpaRepository 만 상속해도 save, findAll, findById, deleteById 등 사용 가능합니다.
 *
 * [쿼리 메서드(Query Method)]
 *  메서드 이름만 규칙에 맞게 작성하면 Spring Data JPA 가 SQL 을 자동 생성합니다.
 *  - existsByUsername → SELECT ... WHERE username = ?
 *  - existsByNickname → SELECT ... WHERE nickname = ?
 *  - findByUsername   → SELECT ... WHERE username = ? (Optional 반환)
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 아이디 중복 여부 (회원가입 시 사용) */
    boolean existsByUsername(String username);

    /** 닉네임 중복 여부 (회원가입 시 사용) */
    boolean existsByNickname(String nickname);

    /** 로그인 시 username 으로 회원 1명 조회 */
    Optional<Member> findByUsername(String username);

}
