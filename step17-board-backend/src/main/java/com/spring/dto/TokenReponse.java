package com.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인 성공 후 클라이언트에 반환하는 응답 DTO이다.
 * tokenType이 Bearer이므로 클라이언트는 API 요청 헤더에
 * {@code Authorization: Bearer accessToken값} 형식으로 담아 보낸다.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenReponse {
  // 짧은 시간 동안 보호된 API를 호출할 때 사용하는 토큰이다.
  private String accessToken;
  // accessToken이 만료되었을 때 새 accessToken을 받기 위해 사용하는 토큰이다.
  private String refreshToken;
  // HTTP Authorization 헤더에 사용할 인증 방식 이름이다. 보통 "Bearer"를 사용한다.
  private String tokenType;
}
