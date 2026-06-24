package com.spring.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService
    implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;
  private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

  @Override
  @Transactional
  public OAuth2User loadUser(OAuth2UserRequest userRequest)
      throws OAuth2AuthenticationException {

    OAuth2User oauthUser = delegate.loadUser(userRequest);

    // TODO 3: registrationId, sub, email, name 속성을 꺼내세요.
    // TODO 4: email 회원이 없으면 provider/providerId와 함께 자동 가입시키세요.
    // 주의: 반환값은 Spring Security가 인증에 사용할 OAuth2User여야 합니다.

    return oauthUser;
  }
}
