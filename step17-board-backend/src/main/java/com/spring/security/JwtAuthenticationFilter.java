package com.spring.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * 요청이 Controller에 도착하기 전에 Authorization 헤더의 JWT를 검사한다.
 *
 * <p>{@link OncePerRequestFilter}를 상속하면 한 HTTP 요청 안에서 이 필터가 한 번만 실행된다.
 * 인증에 성공하면 {@link SecurityContextHolder}에 인증 객체를 저장하고, 이후 Spring Security와
 * {@code @AuthenticationPrincipal}이 이 값을 현재 로그인 사용자로 사용한다.</p>
 */
@Component
// final 필드를 매개변수로 받는 생성자를 Lombok이 자동으로 만들어 준다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
  
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
        // 1. HTTP Authorization 헤더에서 실제 JWT 문자열을 꺼낸다.
        String token = extractToken(request);
        // 2. 토큰이 존재하고 서명·만료 검사를 통과한 요청만 인증 처리한다.
        if(token != null && jwtTokenProvider.validateToken(token)){
          try {
            // 3. 토큰에 적힌 아이디로 DB의 최신 회원 정보와 권한을 조회한다.
            String username = jwtTokenProvider.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 4. 로그인용 생성자와 달리 권한까지 전달하는 생성자는 '이미 인증된 객체'를 만든다.
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          } catch (RuntimeException e) {
            // 만료·위변조가 아닌 토큰이어도 회원이 없으면 비인증으로 처리한다.
            SecurityContextHolder.clearContext();
          }
        }
        // 6. 인증 성공 여부와 관계없이 다음 필터로 요청을 넘겨야 전체 필터 체인이 계속 동작한다.
        filterChain.doFilter(request, response);
  }

  /**
   * 표준 Authorization 헤더 형식인 {@code Bearer 토큰값}에서 실제 토큰만 분리한다.
   * Bearer는 토큰을 가진 사람이 권한을 행사한다는 인증 스킴의 이름이며, 뒤의 공백도 형식에 포함된다.
   */
  private String extractToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if(header != null && header.startsWith("Bearer ")){
      return header.substring(7);
    }
    return null;
  }

}
