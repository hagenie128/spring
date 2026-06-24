package com.spring.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.spring.entity.RefreshToken;
import com.spring.entity.UserEntity;
import com.spring.repository.RefreshTokenRepository;
import com.spring.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * OAuth2 로그인 성공 핸들러이다.
 * 
 * <p>Google 인증 자체가 성공하면 Spring Security는 Authentication 객체를 만든다.
 * 하지만 프론트엔드가 우리 백엔드 API를 계속 호출하려면 이 프로젝트에서 사용하는 JWT가 필요하다.
 * 그래서 이 핸들러는 Google 사용자 정보를 기준으로 우리 서비스의 UserEntity를 찾고,
 * 자체 Access Token과 Refresh Token을 발급한 뒤 React 콜백 화면으로 리다이렉트한다.</p>
 */


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler{

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtTokenProvider jwtTokenProvider;

  // application.properties의 app.frontend-url 값을 읽는다.
  // 로컬 개발에서는 보통 http://localhost:3000이며, 운영에서는 실제 프론트 도메인으로 바꾼다.
  @Value("${app.frontend-url}")
  private String frontendUrl;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
        // 1. OAuth2User에서 email을 추출한다.
        // OAuth2User는 Google이 내려준 사용자 속성(attributes)을 들고 있는 객체이다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 2. DB에서 UserEntity를 조회한다.
        // CustomOAuth2UserService.loadUser()에서 최초 로그인 사용자를 저장해 두었기 때문에 여기서는 조회할 수 있어야 한다.
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("OAuth2 사용자를 찾을 수 없습니다."));

        // 3. 우리 서비스용 JWT를 발급한다.
        // Google Access Token은 Google API 호출용이고, 아래 JWT는 우리 백엔드 API 인증용이다.
        String accessToken = jwtTokenProvider.generateAccessToken(userEntity);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userEntity);

        // 4. Refresh Token을 DB에 저장한다.
        // 사용자가 다시 소셜 로그인하면 이전 Refresh Token은 삭제하고 새 토큰 하나만 유지한다.
        RefreshToken refresh = new RefreshToken();
        refreshTokenRepository.deleteByUser(userEntity);
        refresh.setToken(refreshToken);
        refresh.setUser(userEntity);
        refresh.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refresh);
        
        // 5. 프론트엔드로 리다이렉트한다.
        // 학습용으로 토큰을 쿼리스트링에 담았지만, 실제 운영 환경에서는 URL 기록에 토큰이 남지 않도록
        // Secure + HttpOnly 쿠키 또는 백엔드 세션 연계 방식을 검토하는 것이 좋다.
        String url = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth2/callback").queryParam("accessToken", accessToken).queryParam("refreshToken", refreshToken).build().toUriString();

        // sendRedirect는 브라우저에게 "이 URL로 다시 이동하세요"라는 302 응답을 보낸다.
        response.sendRedirect(url);
      }
}
