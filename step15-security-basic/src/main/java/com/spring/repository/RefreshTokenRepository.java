package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.RefreshToken;
import com.spring.entity.UserEntity;

/**
 * RefreshToken 엔티티의 영속성 작업을 담당한다.
 * JpaRepository를 상속하면 save(), findById(), delete() 같은 기본 CRUD 구현체를 Spring이 자동 생성한다.
 * 아래 메서드들은 이름을 분석해 WHERE token = ? 또는 WHERE user_id = ? 쿼리로 만들어진다.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{

  // 토큰 값으로 조회
  Optional<RefreshToken> findByToken(String token);

  // 사용자의 기존 Refresh Token을 삭제한다. 반환값이 없어도 @Transactional 안에서 DELETE가 실행된다.
  void deleteByUser(UserEntity user);
}
