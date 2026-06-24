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
  private String accessToken;
  private String refreshToken;
  private String tokenType;
}
