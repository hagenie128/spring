package com.spring.security;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // TODO 4: Authorization 헤더에서 Bearer Token을 추출하세요.
    // TODO 5: 토큰 검증 → 회원 조회 → Authentication 생성 → SecurityContext 저장 순서로 구현하세요.

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    // TODO 4: "Bearer "로 시작할 때 토큰 부분만 반환하세요.
    return null;
  }
}
