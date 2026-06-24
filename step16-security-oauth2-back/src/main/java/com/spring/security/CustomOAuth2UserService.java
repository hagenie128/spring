package com.spring.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.UserEntity;
import com.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * OAuth2 소셜 로그인 사용자 처리 서비스
 *
 * ┌─────────────────────────────────────────────────────────────────┐
 * │  OAuth2 Authorization Code Flow (6단계)                         │
 * │                                                                 │
 * │  1. 사용자가 "Google로 로그인" 클릭                              │
 * │     → GET /oauth2/authorization/google                          │
 * │                                                                 │
 * │  2. Spring이 Google 로그인 페이지로 리다이렉트                   │
 * │                                                                 │
 * │  3. 사용자가 Google 계정으로 로그인 승인                          │
 * │                                                                 │
 * │  4. Google이 Authorization Code를 가지고 redirect_uri로 리다이렉트│
 * │     → GET /login/oauth2/code/google?code=4/...                  │
 * │                                                                 │
 * │  5. Spring이 Authorization Code로 Google에 Access Token 요청    │
 * │                                                                 │
 * │  6. Google Access Token으로 사용자 정보(sub, email, name) 요청   │
 * │     → loadUser() 호출 ← 여기가 이 클래스의 역할                   │
 * └─────────────────────────────────────────────────────────────────┘
 *
 * Google 사용자 속성 (attributes):
 *   sub    — 사용자 고유 ID (변하지 않음)
 *   email  — 이메일
 *   name   — 이름
 *   picture — 프로필 사진 URL
 */

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
  
  private final UserRepository userRepository;

  // Google API 호출 및 속성 파싱을 Spring 기본 구현에 맡기기 위한 위임 객체이다.
  // 직접 HTTP 요청을 만들지 않아도 access token으로 사용자 정보 endpoint를 호출해 준다.
  private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // 1. Google에서 사용자 정보를 가져온다.
    // 이 시점에는 Spring Security가 이미 Authorization Code를 Google Access Token으로 교환해 둔 상태이다.
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    // 2. 속성을 추출한다.
    // registrationId는 application.properties에 등록한 provider 이름이며 여기서는 "google"이다.
    String provider = userRequest.getClientRegistration().getRegistrationId();
    String email = oAuth2User.getAttribute("email");
    // sub는 Google 계정의 고유 식별자이다. 이메일은 바뀔 수 있지만 sub는 같은 계정에서 안정적으로 유지된다.
    String providerId = oAuth2User.getAttribute("sub");
    String name = oAuth2User.getAttribute("name");

    System.out.printf("OAuth2 로그인 : provider=%s, email=%s, name=%s",provider,email,name);

    // 3. 기존 사용자 조회 또는 신규 등록을 수행한다.
    // 소셜 로그인은 사용자가 별도의 회원가입 폼을 작성하지 않으므로, 최초 로그인 시 자동 회원가입 처리한다.
    userRepository.findByEmail(email).orElseGet(() -> {
      // 최초 소셜 로그인 -> 자동 회원 가입
      UserEntity newUser = new UserEntity();
      newUser.setUsername(name);
      newUser.setEmail(email);
      newUser.setProvider(provider);
      newUser.setProviderId(providerId);

      System.out.println("신규 사용자 등록 : " + email);

      return userRepository.save(newUser);
    });

    // DefaultOAuth2User를 그대로 반환해야 Spring Security가 OAuth2 인증 성공으로 처리한다.
    // 다음 단계인 OAuth2SuccessHandler에서 email로 우리 DB 회원을 다시 조회해 JWT를 발급한다.
    return oAuth2User;
  }

}
