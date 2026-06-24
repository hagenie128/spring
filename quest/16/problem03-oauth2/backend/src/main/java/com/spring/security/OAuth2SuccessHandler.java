package com.spring.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.spring.repository.RefreshTokenRepository;
import com.spring.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Value("${app.frontend-url}")
  private String frontendUrl;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    // TODO 5: OAuth2User에서 email을 꺼내 DB 회원을 조회하세요.
    // TODO 5: 우리 서비스의 Access/Refresh Token을 발급하세요.
    // TODO 6: 기존 Refresh Token을 삭제하고 새 토큰을 저장하세요.
    // TODO 8: frontendUrl + "/oauth2/callback"로 리다이렉트하세요.

    throw new UnsupportedOperationException("TODO 5~8");
  }
}
