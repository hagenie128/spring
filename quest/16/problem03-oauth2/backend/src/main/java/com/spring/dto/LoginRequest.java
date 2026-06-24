package com.spring.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 JSON의 username과 password만 전달받는 요청 DTO이다.
 * 클라이언트가 POST /auth/login으로 보내는 JSON 예시는
 * {"username":"user1","password":"1234"} 형태이다.
 */
@Getter
@Setter
public class LoginRequest {
  // 로그인할 때 DB에서 회원을 찾는 기준값이다.
  private String username;
  // 사용자가 입력한 원문 비밀번호이다. 서버는 이 값을 DB에 저장하지 않고 BCrypt 해시와 비교만 한다.
  private String password;
}
