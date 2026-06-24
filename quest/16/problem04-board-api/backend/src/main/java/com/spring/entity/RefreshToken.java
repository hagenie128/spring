package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Access Token 재발급에 사용할 Refresh Token을 DB에 보관하는 엔티티이다.
 * JWT 자체는 무상태(stateless)이지만 Refresh Token을 서버에 저장하면 로그아웃, 탈취 토큰 폐기,
 * 사용자별 재발급 제한처럼 서버가 토큰 생명주기를 통제할 수 있다.
 */
@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 한 회원당 하나의 Refresh Token만 갖는 1:1 관계이며 user_id가 users 테이블을 참조하는 FK가 된다.
  // LAZY는 RefreshToken만 조회할 때 회원 정보까지 즉시 SELECT하지 않고 실제 사용 시점에 조회한다.
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false) 
  private UserEntity user;
  
  // 실제 JWT 문자열이다. unique 제약으로 같은 토큰이 두 행에 중복 저장되는 것을 막는다.
  @Column(nullable = false, unique = true, length = 600)
  private String token;

  // JWT 내부 exp 외에도 DB 조회만으로 만료 여부를 판단하기 위해 별도로 기록한다.
  @Column(nullable = false)
  private LocalDateTime expiresAt;

  public boolean isExpired(){
    // 현재 시각이 만료 시각을 지난 경우 true이다.
    return LocalDateTime.now().isAfter(expiresAt);
  }
}
