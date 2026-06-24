package com.spring.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.LoginRequest;
import com.spring.dto.SignupRequest;
import com.spring.dto.TokenReponse;
import com.spring.entity.RefreshToken;
import com.spring.entity.UserEntity;
import com.spring.repository.RefreshTokenRepository;
import com.spring.repository.UserRepository;
import com.spring.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;


/**
 * 회원가입, 로그인, 로그아웃처럼 인증과 관련된 업무 규칙을 처리한다.
 * Controller는 HTTP 요청/응답을 담당하고, Service는 중복 확인·암호화·토큰 발급 같은 실제 규칙을
 * 담당하도록 나누면 웹 기술이 바뀌어도 핵심 로직을 재사용하고 테스트하기 쉽다.
 */
@Service
// 이 클래스의 public 메서드는 기본적으로 하나의 DB 트랜잭션 안에서 실행된다.
@Transactional
@RequiredArgsConstructor
public class AuthService {
  // 회원 정보를 저장하거나 조회할 때 사용한다.
  private final UserRepository userRepository;
  // Refresh Token을 저장하거나 조회할 때 사용한다. 구체적인 기능은 다음 작업에서 작성한다.
  private final RefreshTokenRepository refreshTokenRepository;
  // 비밀번호 원문을 안전한 해시 값으로 바꾸고, 로그인 시 일치 여부를 확인한다.
  private final PasswordEncoder passwordEncoder;
  // 아이디와 비밀번호를 Spring Security의 인증 절차에 전달한다.
  private final AuthenticationManager authenticationManager;
  // 인증에 성공한 사용자 정보로 Access Token과 Refresh Token을 생성한다.
  private final JwtTokenProvider jwtTokenProvider;

  /** 회원가입 요청을 검증하고 비밀번호를 단방향 해시로 바꾼 뒤 회원을 저장한다. */
  public void signup(SignupRequest signupRequest) {
    // 애플리케이션에서 먼저 확인해 사용자에게 이해하기 쉬운 중복 오류를 전달한다.
    if(userRepository.existsByUsername(signupRequest.getUsername())){
      throw new IllegalArgumentException("이미 사용중인 아이디 입니다. " + signupRequest.getUsername());
    }

    UserEntity entity = new UserEntity();
    entity.setUsername(signupRequest.getUsername());
    entity.setEmail(signupRequest.getEmail());
    // encode() 결과만 DB에 저장한다. BCrypt는 매번 salt를 사용하므로 같은 비밀번호도 다른 해시가 된다.
    entity.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

    userRepository.save(entity);
  }

  public TokenReponse login(LoginRequest req) {
    // 1. 아직 인증되지 않은 아이디/비밀번호 묶음을 Spring Security에 전달한다.
    // 성공하면 인증된 Authentication, 실패하면 BadCredentialsException 등이 반환된다.
    Authentication auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
    );

    // principal은 '인증된 사용자 본인'이다. UserDetailServiceImpl이 UserEntity를 반환했으므로 형변환한다.
    UserEntity user = (UserEntity) auth.getPrincipal();

    // 2. 토큰 생성
    String accessToken = jwtTokenProvider.generateAccessToken(user);
    String refreshToken = jwtTokenProvider.generateRefreshToken(user);

    // 3. Refresh Token은 서버에서도 관리해야 로그아웃이나 강제 만료가 가능하다.
    saveRefreshToken(user,refreshToken);

    return new TokenReponse(accessToken,refreshToken,"Bearer");
  }

  private void saveRefreshToken(UserEntity user, String refreshToken) {
    // 사용자당 토큰을 하나만 유지한다. 새 로그인 시 이전 Refresh Token은 더 이상 재발급에 쓸 수 없다.
    refreshTokenRepository.deleteByUser(user);

    RefreshToken token = new RefreshToken();
    token.setToken(refreshToken);
    token.setUser(user);
    // JWT 내부 exp와 DB 만료 시각을 함께 검사하면 폐기 여부를 서버 정책으로 제어할 수 있다.
    token.setExpiresAt(LocalDateTime.now().plusDays(7));

    refreshTokenRepository.save(token);
  }

  public void logout(UserEntity currentUser) {
    // Access Token은 서버에 저장하지 않으므로 즉시 삭제할 수 없다. 대신 Refresh Token을 지워 재발급을 막는다.
    // 기존 Access Token은 짧은 만료 시간이 끝날 때까지 유효하다는 점이 JWT 로그아웃의 특징이다.
    userRepository.findByUsername(currentUser.getUsername()).ifPresent(refreshTokenRepository::deleteByUser);
  }

  

}
