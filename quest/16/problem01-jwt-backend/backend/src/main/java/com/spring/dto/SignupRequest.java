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
  private String username;
  private String password;
  private String email;

}
