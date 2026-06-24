package com.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.LoginRequest;
import com.spring.dto.SignupRequest;
import com.spring.dto.TokenReponse;
import com.spring.entity.UserEntity;
import com.spring.service.AuthService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
/**
 * 인증 REST API의 진입점이다.
 *
 * <p>{@code @RestController}는 메서드 반환값을 JSON 응답 본문으로 변환하고,
 * {@code @RequestMapping("/auth")}는 모든 메서드의 공통 URL 앞부분을 지정한다.</p>
 *  
 * POST /auth/signup  — 회원가입
 * POST /auth/login   — 로그인 → JWT 발급
 * POST /auth/logout  — 로그아웃 (Refresh Token 삭제)
 * GET  /auth/me      — 현재 로그인 사용자 정보
 */
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  // @RequestBody가 JSON 본문을 SignupRequest 객체로 변환한다.
  // ResponseEntity를 사용하면 응답 본문뿐 아니라 201 같은 HTTP 상태 코드도 명시할 수 있다.
  @PostMapping("/signup")
  public ResponseEntity<Map<String,String>> signup(@RequestBody SignupRequest signupRequest) {
      
      authService.signup(signupRequest);

      return ResponseEntity.status(HttpStatus.CREATED).body(
        Map.of("message","회원가입이 완료되었습니다.")
      );
  }
  
  @PostMapping("/login")
  public ResponseEntity<TokenReponse> login(@RequestBody LoginRequest req) {
    // 인증 성공 시 200 OK와 두 종류의 JWT를, 실패 시 Spring Security 인증 예외를 반환한다.
    return ResponseEntity.ok(authService.login(req));
  }
  
  @PostMapping("/logout")
  public ResponseEntity<Map<String,String>> logout(@AuthenticationPrincipal UserEntity currentUser) {
      // JWT 필터가 SecurityContext에 넣은 principal을 현재 로그인 회원으로 주입받는다.
      authService.logout(currentUser);      
      return ResponseEntity.ok( Map.of("message","로그아웃 완료"));
  }
  
  @GetMapping("/me")
  public ResponseEntity<Map<String,Object>> me(@AuthenticationPrincipal UserEntity currentUser) {
      // 엔티티 전체를 반환하면 password 해시까지 노출될 수 있으므로 필요한 공개 필드만 골라 응답한다.
      return ResponseEntity.ok(Map.of(
        "id",  currentUser.getId(),
        "username", currentUser.getUsername(),
        "nickname", currentUser.getNickname(),  
        "role", currentUser.getRole() 
      ));
  }
  

}
