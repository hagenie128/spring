package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Member;

/**
 * [회원 데이터 처리 리포지토리 인터페이스]
 * 
 * JpaRepository 인터페이스를 상속받으면 기본적인 CRUD(save, findById, findAll, delete 등) 메서드가 자동으로 생성됩니다.
 * 스프링부트 실행 시 Spring Data JPA가 인터페이스의 메서드명을 기반으로 실제 데이터베이스 쿼리를 동적으로 구현해 줍니다.
 * 
 * - 첫 번째 제네릭 매개변수 (Member): 이 리포지토리가 다루는 JPA 엔티티 클래스
 * - 두 번째 제네릭 매개변수 (Long): 엔티티의 기본키(ID) 데이터 타입
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

  /**
   * [아이디 중복 여부 확인 메서드]
   * 
   * existsBy...: Spring Data JPA는 메서드 이름 분석을 통해 
   * "SELECT COUNT(m) > 0 FROM Member m WHERE m.username = :username" 쿼리를 자동으로 생성합니다.
   * 
   * @param username 중복 검사할 아이디
   * @return 존재하면 true, 없으면 false
   */
  boolean existsByUsername(String username);

  /**
   * [닉네임 중복 여부 확인 메서드]
   * 
   * @param nickname 중복 검사할 닉네임
   * @return 존재하면 true, 없으면 false
   */
  boolean existsByNickname(String nickname);

  /**
   * [아이디를 통한 회원 정보 단건 조회 메서드]
   * 
   * findByUsername: "SELECT m FROM Member m WHERE m.username = :username" 쿼리를 생성합니다.
   * 
   * Optional<Member>: 조회 결과가 없을 때 null을 바로 반환하는 대신, 
   * 비어있는 상자를 넘겨주어 호출하는 쪽에서 NullPointerException을 방지하고 
   * .orElseThrow() 등으로 안전하게 기본값 처리를 하도록 돕습니다.
   * 
   * @param username 조회할 사용자 아이디
   * @return 회원 정보를 담고 있는 Optional 객체
   */
  Optional<Member> findByUsername(String username);
}
