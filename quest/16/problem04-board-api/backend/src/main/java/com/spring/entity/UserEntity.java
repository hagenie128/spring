package com.spring.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * users 테이블 한 행을 표현하는 JPA 엔티티이자 Spring Security의 사용자 정보이다.
 * {@link UserDetails}를 구현했기 때문에 AuthenticationManager가 username, password, authorities를
 * 표준 방식으로 읽을 수 있다. 즉, DB 모델과 인증 사용자 모델을 이 학습 예제에서는 하나로 사용한다.
 */
@Entity
@Table(name = "board_member")
@Getter
@Setter
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20, unique = true)
  private String username;

  @Column(nullable = true, length = 255)
  private String password;

  @Column(nullable = false, length = 10)
  private String nickname;

  @Column(nullable = false, length = 20)
  private String role = "ROLE_USER";

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 현재는 한 회원이 하나의 role만 가진 구조이므로 요소가 하나인 목록으로 반환한다.
    return List.of(new SimpleGrantedAuthority(role));
  }

 

}
