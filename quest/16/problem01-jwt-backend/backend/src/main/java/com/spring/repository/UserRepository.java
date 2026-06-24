package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.UserEntity;

// JpaRepository를 상속하면 기본적인 회원 저장, 단건 조회, 전체 조회, 삭제 메서드가 자동으로 제공된다.
// UserEntity는 다룰 엔티티 타입이고 Long은 UserEntity 기본 키(id)의 타입이다.
public interface UserRepository extends JpaRepository<UserEntity, Long>{
  // 메서드 이름을 분석해 username 조건의 조회 쿼리를 Spring Data JPA가 자동으로 만든다.
  // 조회 결과가 없을 수도 있으므로 null 대신 Optional로 감싸서 반환한다.
  Optional<UserEntity> findByUsername(String username);

  // 이메일이 일치하는 회원 한 명을 조회한다.
  Optional<UserEntity> findByEmail(String email);

  // 같은 아이디가 이미 있으면 true를 반환한다. 회원가입 전 중복 검사에 사용할 수 있다.
  boolean existsByUsername(String username);

}
