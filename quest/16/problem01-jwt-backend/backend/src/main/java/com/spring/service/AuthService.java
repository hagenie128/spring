package com.spring.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.LoginRequest;
import com.spring.dto.SignupRequest;
import com.spring.dto.TokenReponse;
import com.spring.entity.UserEntity;
import com.spring.repository.RefreshTokenRepository;
import com.spring.repository.UserRepository;
import com.spring.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  public void signup(SignupRequest request) {
    // TODO 6: 아이디 중복 검사 → UserEntity 생성 → BCrypt 암호화 → 저장
    throw new UnsupportedOperationException("TODO 6");
  }

  public TokenReponse login(LoginRequest request) {
    // TODO 7: AuthenticationManager로 인증하세요.
    // TODO 8: Access/Refresh Token을 만들고 Refresh Token을 DB에 저장하세요.
    throw new UnsupportedOperationException("TODO 7~8");
  }

  public void logout(UserEntity currentUser) {
    // TODO 9: 현재 회원의 Refresh Token을 삭제하세요.
    throw new UnsupportedOperationException("TODO 9");
  }
}
