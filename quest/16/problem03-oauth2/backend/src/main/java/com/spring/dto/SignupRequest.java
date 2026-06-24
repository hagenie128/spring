package com.spring.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 JSON 요청을 받는 DTO(Data Transfer Object)이다.
 * API 입력 형태를 엔티티와 분리하면 클라이언트가 id, role 같은 서버 관리 필드를 임의로 바꾸는 것을 막고
 * API 사양과 DB 구조가 서로 직접 의존하지 않게 할 수 있다.
 */
@Getter
@Setter
public class SignupRequest {
  // 사용자가 로그인할 때 입력할 아이디이다. AuthService에서 중복 여부를 검사한다.
  private String username;
  // 회원가입 요청 시에는 원문으로 들어오지만, 저장 전 PasswordEncoder로 해시 처리된다.
  private String password;
  // 로컬 로그인 회원과 Google 소셜 로그인 회원을 연결하거나 구분할 때도 활용할 수 있는 이메일이다.
  private String email;

}
