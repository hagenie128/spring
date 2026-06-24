package com.spring.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

  private final SecretKey secretKey;
  private final long accessExpiration;
  private final long refreshExpiration;

  public JwtTokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.access-expiration}") long accessExpiration,
      @Value("${jwt.refresh-expiration}") long refreshExpiration) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.accessExpiration = accessExpiration;
    this.refreshExpiration = refreshExpiration;
  }

  public String generateAccessToken(UserDetails details) {
    // TODO 2: subject, role, issuedAt, expiration, signature를 넣어 Access Token을 만드세요.
    throw new UnsupportedOperationException("TODO 2");
  }

  public String generateRefreshToken(UserDetails details) {
    // TODO 2: Access Token보다 긴 만료 시간을 가진 Refresh Token을 만드세요.
    throw new UnsupportedOperationException("TODO 2");
  }

  public Claims parseClaims(String token) {
    // TODO 3: secretKey로 서명을 검증하고 Claims를 반환하세요.
    throw new UnsupportedOperationException("TODO 3");
  }

  public boolean validateToken(String token) {
    // TODO 3: 정상 토큰이면 true, 만료·위변조·형식 오류이면 false를 반환하세요.
    return false;
  }

  public String getUsername(String token) {
    // TODO 3: subject에 저장된 username을 반환하세요.
    throw new UnsupportedOperationException("TODO 3");
  }

  public String getRole(String token) {
    // TODO 3: role claim을 반환하세요.
    throw new UnsupportedOperationException("TODO 3");
  }
}
