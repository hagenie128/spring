package com.spring.dto;

import lombok.Getter;
import lombok.Setter;

/** 로그인 JSON의 username과 password만 전달받는 요청 DTO이다. */
@Getter
@Setter
public class LoginRequest {
  private String username;
  private String password;
}
