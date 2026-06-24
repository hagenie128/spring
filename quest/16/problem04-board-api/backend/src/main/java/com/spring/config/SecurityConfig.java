package com.spring.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.spring.security.JwtAuthenticationFilter;


import lombok.RequiredArgsConstructor;

/**
 * Spring Security의 인증 방식, URL 접근 규칙, 보안 관련 객체를 설정한다.
 *
 * <p><b>인증(Authentication)</b>은 "누구인지" 확인하는 과정이고,
 * <b>인가(Authorization)</b>는 인증된 사용자가 해당 기능을 사용할 수 있는지 확인하는 과정이다.
 * 이 프로젝트는 서버 세션 대신 JWT를 사용하므로 요청마다 토큰을 검증해 인증 정보를 만든다.</p>
 *
 * <p>요청 흐름: 클라이언트 요청 → CORS/보안 필터 → JWT 필터 → URL 인가 검사 → Controller</p>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  // 모든 요청에서 JWT를 먼저 확인하도록 필터 체인에 등록할 필터이다.
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
    .csrf(crsf -> crsf.disable())
    .cors(cors -> cors.configurationSource(corsConfigrationSource()))
    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth -> auth
      .requestMatchers("/auth/**").permitAll()
      .requestMatchers("/v3/api-docs/**").permitAll()
      .requestMatchers("/swagger-ui.html").permitAll()
      .requestMatchers("/swagger-ui/**").permitAll()
      .requestMatchers(HttpMethod.GET,"/api/posts/**").permitAll()
      .anyRequest().authenticated()
    ) 
    // 필터 추가
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigrationSource() {
    // CORS는 출처(origin)가 다른 프론트엔드가 브라우저를 통해 API를 호출할 수 있게 하는 규칙이다.
    // localhost라도 포트가 다르면 서로 다른 출처이다(React 3000, Spring Boot 8888).
    CorsConfiguration config = new CorsConfiguration();
    // 허용할 프론트엔드 주소를 정확히 지정한다. 운영 환경에서는 실제 도메인으로 바꿔야 한다.
    config.setAllowedOrigins(List.of("http://localhost:3000"));
    // 브라우저가 사용할 수 있는 HTTP 메서드와 요청 헤더를 허용한다.
    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    // 쿠키나 Authorization 같은 인증 정보를 포함한 교차 출처 요청을 허용한다.
    config.setAllowCredentials(true);
    // OPTIONS 사전 요청(Preflight)의 결과를 브라우저가 1시간 동안 재사용한다.
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // 모든 경로에 적용
    source.registerCorsConfiguration("/**", config);

    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    // 비밀번호를 복호화 가능한 형태로 저장하지 않고 BCrypt 단방향 해시로 변환한다.
    return new BCryptPasswordEncoder();
  }

  /**
   * AuthService의 로그인 기능에서 아이디와 비밀번호 인증을 시작할 객체를 Bean으로 등록한다.
   * authenticate()가 호출되면 UserDetailsService로 회원을 찾고 PasswordEncoder로 비밀번호를 비교한다.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
