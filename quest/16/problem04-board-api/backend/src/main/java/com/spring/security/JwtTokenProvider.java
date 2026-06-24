package com.spring.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JWT의 생성, 서명 검증, 정보 추출을 담당한다.
 *
 * <p>JWT는 header.payload.signature 세 부분으로 이루어진 문자열이다. Payload는 암호화된 영역이
 * 아니므로 비밀번호 같은 민감 정보는 넣으면 안 된다. Signature는 서버의 비밀 키로 만들어져
 * Payload가 변경되었는지 확인하는 용도로 사용한다.</p>
 *
 * <p>Access Token은 수명이 짧고 API 호출에 사용하며, Refresh Token은 Access Token 재발급을
 * 위해 더 오래 보관한다. 둘의 역할을 나누면 Access Token 유출 피해 시간을 줄일 수 있다.</p>
 */
@Component
public class JwtTokenProvider {
  // JWT의 서명을 만들고 검증할 때 함께 사용하는 비밀 키이다.
  private final SecretKey secretKey;
  // Access Token과 Refresh Token의 유효 시간이며 application.properties에서 밀리초 단위로 읽는다.
  private final long accessExpiration;
  private final long refreshExpiration;

  // @Value는 설정 파일의 값을 생성자 매개변수로 주입한다.
  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, 
    @Value("${jwt.access-expiration}") long accessExpiration, 
    @Value("${jwt.refresh-expiration}") long refreshExpiration) {
    // 설정의 문자열 키를 HMAC 서명 알고리즘이 사용할 SecretKey 객체로 변환한다.
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.accessExpiration = accessExpiration;
    this.refreshExpiration = refreshExpiration;
  }

  /**
   * API 요청 때 인증과 권한 확인에 사용할 Access Token을 만든다.
   * Payload에는 사용자 아이디(sub), 권한(role), 발급 시각(iat), 만료 시각(exp)이 들어간다.
   */
  public String generateAccessToken(UserDetails details){
    // 권한 목록의 첫 번째 값을 사용하고, 값이 없다면 일반 사용자 권한을 기본값으로 사용한다.
    String role = details.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_USER");

    // 마지막 signWith 단계에서 토큰에 서명하므로 서버가 이후 위변조 여부를 확인할 수 있다.
    return Jwts.builder().subject(details.getUsername()).claim("role", role).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+accessExpiration)).signWith(secretKey).compact();
  }

  /**
   * Access Token을 다시 발급받을 때 사용할 Refresh Token을 만든다.
   * 사용자 아이디와 시간 정보만 넣으며, 권한 정보는 포함하지 않는다.
   */
  public String generateRefreshToken(UserDetails details){
    return Jwts.builder().subject(details.getUsername()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+refreshExpiration)).signWith(secretKey).compact();
  }

  /**
   * 토큰의 서명과 만료 시간을 검사하고, 검사를 통과하면 Payload(Claims)를 꺼낸다.
   * 문제가 있는 토큰이면 JJWT 라이브러리가 예외를 발생시킨다.
   */
  public Claims parseClaims(String token){
    // verifyWith가 signature를 확인하고 parseSignedClaims가 JWT 본문을 Claims 객체로 변환한다.
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }

  /**
   * 예외 발생 여부를 이용해 토큰이 현재 사용할 수 있는 상태인지 확인한다.
   * @return 서명과 만료 시간이 정상이면 true, 만료·위변조·형식 오류가 있으면 false
   */
  public boolean validateToken(String token){
    try {
      parseClaims(token);    
      return true;
    } catch (ExpiredJwtException e) {
      System.out.println("JWT 만료 : " + e.getMessage());
    } catch(JwtException | IllegalArgumentException e){
      System.out.println("유효하지 않은 JWT : " + e.getMessage());
    }
    return false;
  }

  // 토큰의 subject(sub)에 저장해 둔 사용자 아이디를 꺼낸다.
  public String getUsername(String token){
    return parseClaims(token).getSubject();
  }
  
  // Access Token의 사용자 정의 claim인 role 값을 문자열로 꺼낸다.
  public String getRole(String token){
    return parseClaims(token).get("role", String.class);
  }

}






