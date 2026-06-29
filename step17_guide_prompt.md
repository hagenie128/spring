# step17 학습 가이드 HTML 제작 프롬프트
# (집에서 Claude에게 붙여넣기 하면 됩니다)

---

아래 내용을 참고해서 **개발 초보자용 학습 가이드 HTML** 파일을 만들어줘.
파일명: `step17_board_guide.html`

---

## 요구사항

- 개발 초보자가 수업을 못 들어도 이해할 수 있도록
- 기본 의미, 기능, 로직, 코드 설명, 흐름, 설치, 알고리즘, 다른 방법(대안), 요즘 트렌드 포함
- 주요 파일이 아니어도 의도해서 만들어진 파일은 모두 설명
- 아래 제공하는 **디자인 스타일**과 **프로젝트 코드** 전부 활용

---

## 디자인 스타일 (기존 HTML 가이드와 동일하게)

- 폰트: IBM Plex Sans KR, Gowun Dodum, JetBrains Mono, Space Grotesk (Google Fonts)
- 코드 하이라이팅: Prism.js (prism-one-light 테마)
- 색상 변수:
  - `--green:#1f9d4d`, `--blue:#1d6fd1`, `--amber:#b5810b`, `--violet:#7a4ed1`, `--pink:#d23b6b`
- 레이아웃: 왼쪽 sticky 사이드바(312px) + 오른쪽 메인 콘텐츠
- 반응형: 태블릿(≤1024px)에서 사이드바 햄버거 메뉴, 모바일(≤680px) 축소
- 접이식 목차 (그룹별 collapse/expand), 검색 기능, 읽기 진행바, 맨위로 버튼
- 색깔 박스 6종:
  - `.box.basic` (파랑) = 기본 의미
  - `.box.logic` (초록) = 로직·흐름
  - `.box.tip` (노랑) = 팁·알고리즘
  - `.box.warn` (빨강) = 주의·오류
  - `.box.alt` (회색) = 다른 방법
  - `.box.trend` (보라) = 요즘 트렌드
- ASCII `.flow` 다이어그램, SVG 다이어그램, `.tablewrap` 테이블 스타일 포함
- JavaScript: 사이드바 토글, 목차 active 하이라이트, 검색 필터, collapse/expand

---

## 목차 구성 (이 순서로 섹션 만들어줘)

### 시작하기
- 00: 이 문서를 읽는 법 (색깔 박스 의미 설명)
- 01: step17 전체 그림 (풀스택 아키텍처 — React ↔ Spring Boot ↔ MySQL)
- 02: 개발환경 설치 & 실행법 (백엔드/프론트 각각)

### PART 1 · 백엔드 (Spring Boot)
- 03: 프로젝트 구조 & 계층 설명
- 04: build.gradle 의존성 (각 라이브러리 역할 설명)
- 05: application.properties 설정
- 06: DB 설계 & board.sql (테이블, VIEW, 외래키)
- 07: MyBatis XML Mapper (board-mapper.xml, board-comment-mapper.xml)
- 08: JPA Entity (UserEntity, RefreshToken)
- 09: Spring Security 설정 (SecurityConfig)
- 10: JWT 토큰 생성·검증 (JwtTokenProvider)
- 11: JWT 인증 필터 (JwtAuthenticationFilter)
- 12: UserDetails & AuthService (회원가입·로그인·로그아웃)
- 13: 게시글 CRUD (BoardMapper, BoardService, BoardController)
- 14: 댓글 CRUD (BoardCommentController)
- 15: 좋아요/싫어요 토글 알고리즘
- 16: PaggingVO 페이징 계산 알고리즘
- 17: Swagger 설정 (SwaggerConfig)

### PART 2 · 프론트엔드 (React)
- 18: React 프로젝트 구조 & 파일 역할
- 19: index.js & App.js (BrowserRouter, Routes 구조)
- 20: AuthContext (전역 인증 상태 관리 — Context API)
- 21: axiosInstance (Axios 공통 설정 & JWT 자동 첨부 인터셉터)
- 22: API 레이어 (authApi.js, postApi.js, commentApi.js)
- 23: NavBar 컴포넌트 (TODO 미완성 포함 설명)
- 24: QuillEditor 컴포넌트 (Quill.js 리치 텍스트 에디터 — 설치·초기화·useRef 활용)
- 25: 게시글 목록 페이지 (PostListPage — useEffect, useState, map)
- 26: 게시글 상세 페이지 (PostDetailPage — useParams, 좋아요/싫어요, 수정/삭제, 댓글 목록)
- 27: 로그인 페이지 (LoginPage — useRef, useAuth, 버그 3개 해설)
- 28: 페이징 컴포넌트 (PaggingBar — props, 버튼 생성 알고리즘)
- 29: 게시글 작성/수정 페이지 (PostWritePage — isEditorMode 분기, QuillEditor 연동)
- 30: 미완성 페이지 (SignupPage — 앞으로 만들어야 할 것)

### PART 3 · 연동 흐름
- 31: 로그인 전체 흐름 (React → Spring Security → JWT → localStorage)
- 32: 게시글 목록 조회 흐름 (useEffect → axiosInstance → Controller → MyBatis → MySQL → 화면)
- 33: 글쓰기/수정 흐름 (PostWritePage → postApi.create/update → BoardController → insertBoard useGeneratedKeys)
- 34: CORS 설정 (왜 필요한지, SecurityConfig에서 어떻게 설정하는지)
- 35: 자주 막히는 오류 & 해결법

### 마무리
- 36: 요즘 트렌드 (Next.js, TanStack Query, Zustand, MSW, Docker 등)
- 37: 핵심 용어 사전
- 38: 공부 순서 & 다음 단계

---

## 프로젝트 코드 전문

### [백엔드] build.gradle
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.15'
    id 'io.spring.dependency-management' version '1.1.7'
}
group = 'com.spring'
version = '0.0.1-SNAPSHOT'
java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }
repositories { mavenCentral() }
ext { jjwtVersion = '0.13.0' }
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5'
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.16'
    implementation "io.jsonwebtoken:jjwt-api:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-impl:${jjwtVersion}"
    runtimeOnly "io.jsonwebtoken:jjwt-jackson:${jjwtVersion}"
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.mysql:mysql-connector-j'
    annotationProcessor 'org.projectlombok:lombok'
    annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.5'
    testImplementation 'org.springframework.security:spring-security-test'
    testCompileOnly 'org.projectlombok:lombok'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    testAnnotationProcessor 'org.projectlombok:lombok'
}
tasks.named('test') { useJUnitPlatform() }
```

### [백엔드] application.properties
```properties
spring.application.name=step17-board-backend
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
mybatis.mapper-locations=classpath:/mappers/**/*.xml
mybatis.type-aliases-package=com.spring.dto
mybatis.configuration.map-underscore-to-camel-case=true
spring.datasource.url=jdbc:mysql://localhost:3306/new_board_db
spring.datasource.username=root
spring.datasource.password=12345678
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
server.port=8888
jwt.secret=843E26E7163A480B6CAD4B2A98E3EFD7502C8471F75C27ADB4BCF82082D2F9F04C2D702D57D55C25B2591591FF3F305256614EB2797D2F3FE9D5474A0788C371
jwt.access-expiration=1800000
jwt.refresh-expiration=604800000
spring.config.import=optional:file:.env[.properties]
app.frontend-url=http://localhost:3000
```

### [백엔드] SecurityConfig.java
```java
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
 * 인증(Authentication)은 "누구인지" 확인하는 과정이고,
 * 인가(Authorization)는 인증된 사용자가 해당 기능을 사용할 수 있는지 확인하는 과정이다.
 * 이 프로젝트는 서버 세션 대신 JWT를 사용하므로 요청마다 토큰을 검증해 인증 정보를 만든다.
 *
 * 요청 흐름: 클라이언트 요청 → CORS/보안 필터 → JWT 필터 → URL 인가 검사 → Controller
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  // 모든 요청에서 JWT를 먼저 확인하도록 필터 체인에 등록할 필터이다.
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(crsf -> crsf.disable())
        .cors(cors -> cors.configurationSource(corsConfigrationSource()))
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers("/swagger-ui.html").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
            .anyRequest().authenticated())
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
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    // 쿠키나 Authorization 같은 인증 정보를 포함한 교차 출처 요청을 허용한다.
    config.setAllowCredentials(true);
    // OPTIONS 사전 요청(Preflight)의 결과를 브라우저가 1시간 동안 재사용한다.
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
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
```

### [백엔드] JwtTokenProvider.java
```java
package com.spring.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.access-expiration}") long accessExpiration,
        @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
            .subject(userDetails.getUsername())
            .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + accessExpiration))
            .signWith(key)
            .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
            .signWith(key)
            .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token) {
        try { parseClaims(token); return true; }
        catch (ExpiredJwtException | JwtException e) { return false; }
    }

    public String getUsername(String token) { return parseClaims(token).getSubject(); }
    public String getRole(String token) { return parseClaims(token).get("role", String.class); }
}
```

### [백엔드] JwtAuthenticationFilter.java
```java
package com.spring.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
```

### [백엔드] UserDetailServiceImpl.java
```java
package com.spring.security;

import com.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }
}
```

### [백엔드] UserEntity.java
```java
package com.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "board_member")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String nickname;

    @Column(length = 20)
    @Builder.Default
    private String role = "ROLE_USER";

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
```

### [백엔드] RefreshToken.java
```java
package com.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, unique = true, length = 600)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
```

### [백엔드] AuthService.java
```java
package com.spring.service;

import com.spring.dto.SignupRequest;
import com.spring.entity.RefreshToken;
import com.spring.entity.UserEntity;
import com.spring.repository.RefreshTokenRepository;
import com.spring.repository.UserRepository;
import com.spring.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserEntity signup(SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername()))
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        UserEntity user = UserEntity.builder()
            .username(req.getUsername())
            .password(passwordEncoder.encode(req.getPassword()))
            .nickname(req.getNickname())
            .build();
        return userRepository.save(user);
    }

    @Transactional
    public String[] login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
        UserEntity user = (UserEntity) auth.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        saveRefreshToken(user, refreshToken);
        return new String[]{accessToken, refreshToken};
    }

    @Transactional
    public void logout(UserEntity user) {
        refreshTokenRepository.deleteByUser(user);
    }

    private void saveRefreshToken(UserEntity user, String token) {
        refreshTokenRepository.deleteByUser(user);
        RefreshToken rt = RefreshToken.builder()
            .user(user).token(token)
            .expiresAt(LocalDateTime.now().plusDays(7))
            .build();
        refreshTokenRepository.save(rt);
    }
}
```

### [백엔드] AuthController.java
```java
package com.spring.controller;

import com.spring.dto.*;
import com.spring.entity.UserEntity;
import com.spring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        authService.signup(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenReponse> login(@RequestBody LoginRequest req) {
        String[] tokens = authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(new TokenReponse(tokens[0], tokens[1], "Bearer"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserEntity user) {
        authService.logout(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "nickname", user.getNickname(),
            "role", user.getRole()
        ));
    }
}
```

### [백엔드] BoardController.java
```java
package com.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
import com.spring.dto.BoardReactionReq;
import com.spring.dto.ReactionCountDTO;
import com.spring.entity.UserEntity;
import com.spring.service.BoardService;
import com.spring.vo.PaggingVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  // 게시글 목록 조회 (페이징 + 검색)
  @GetMapping
  public ResponseEntity<Map<String,Object>> boardList(
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "20") int size
  ){
    List<BoardDTO> boardList;
    if(keyword.isBlank())
      boardList = boardService.getBoardList(page, size);
    else
      boardList = boardService.searchBoardList(keyword, page, size);

    int count = boardService.boardCount();
    PaggingVO paggingVO = new PaggingVO(count, page); // 2인자 생성자

    Map<String,Object> map = new HashMap<>();
    map.put("list", boardList);
    map.put("pagging", paggingVO);
    return ResponseEntity.ok(map);
  }

  // 게시글 상세 조회 + 댓글 목록
  @GetMapping("/{bno}")
  public ResponseEntity<Map<String,Object>> boardContent(@PathVariable int bno) {
    Map<String, Object> map = new HashMap<>();
    BoardDTO board = boardService.selectBoard(bno);
    List<BoardCommentDTO> commentList = boardService.selectBoardComment(bno);
    map.put("board", board);
    map.put("commentList", commentList);
    return ResponseEntity.ok(map);
  }

  // 게시글 작성 → 생성된 bno를 응답으로 반환 (useGeneratedKeys)
  @PostMapping
  public ResponseEntity<Map<String,Object>> addBoard(
    @RequestBody BoardDTO board,
    @AuthenticationPrincipal UserEntity entity
  ) {
    Map<String, Object> map = new HashMap<>();
    map.put("board", board);
    board.setMid(entity.getId());
    boardService.addBoard(board);   // insertBoard 후 board.bno 자동 설정
    return ResponseEntity.ok(map);
  }

  // 게시글 삭제 — 작성자 본인만 가능
  @DeleteMapping("/{bno}")
  public ResponseEntity<Map<String,Object>> deleteBoard(
    @PathVariable int bno,
    @AuthenticationPrincipal UserEntity userEntity
  ){
    Map<String, Object> map = new HashMap<>();
    BoardDTO board = boardService.selectBoard(bno);
    if(board == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    if(board.getMid() != userEntity.getId())
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
    boardService.deleteBoard(bno);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  // 게시글 수정 — 작성자 본인만 가능
  @PatchMapping("/{bno}")
  public ResponseEntity<?> updateBoard(
    @PathVariable int bno,
    @RequestBody BoardDTO reqBoard,
    @AuthenticationPrincipal UserEntity userEntity
  ){
    BoardDTO board = boardService.selectBoard(bno);
    if(board == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    if(board.getMid() != userEntity.getId())
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    reqBoard.setBno(bno);
    boardService.updateBoard(reqBoard);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  // 좋아요/싫어요 토글 — 응답: { count: { likeCount, dislikeCount } }
  @PostMapping("/reaction")
  public ResponseEntity<Map<String,Object>> boardReaction(
    @RequestBody BoardReactionReq reactionReq,
    @AuthenticationPrincipal UserEntity userEntity
  ) {
    Map<String, Object> map = new HashMap<>();
    BoardReactionReq req = boardService.selectBoardReaction(reactionReq.getBno(), userEntity.getId());

    if(req == null){
      reactionReq.setMid(userEntity.getId());
      boardService.addBoardReaction(reactionReq);
    } else {
      reactionReq.setId(req.getId());
      if(reactionReq.getType().equals(req.getType()))
        boardService.deleteBoardReaction(reactionReq);
      else
        boardService.updateBoardReaction(reactionReq);
    }

    ReactionCountDTO reactionCount = boardService.getBoardReactionCount(reactionReq.getBno());
    map.put("count", reactionCount);
    return ResponseEntity.ok(map);
  }
}
```

### [백엔드] BoardCommentController.java
```java
package com.spring.controller;

import com.spring.dto.*;
import com.spring.entity.UserEntity;
import com.spring.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody BoardCommentDTO comment,
                                           @AuthenticationPrincipal UserEntity user) {
        comment.setMid(user.getId().intValue());
        boardCommentService.insertBoardComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{cno}")
    public ResponseEntity<?> deleteComment(@PathVariable int cno,
                                           @AuthenticationPrincipal UserEntity user) {
        boardCommentService.deleteBoardComment(cno);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cno}")
    public ResponseEntity<?> updateComment(@PathVariable int cno,
                                           @RequestBody BoardCommentDTO comment,
                                           @AuthenticationPrincipal UserEntity user) {
        comment.setCno(cno);
        boardCommentService.updateBoardComment(comment);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reaction")
    public ResponseEntity<?> reaction(@RequestBody BoardCommentReactionReq req,
                                      @AuthenticationPrincipal UserEntity user) {
        req.setMid(user.getId().intValue());
        BoardCommentReactionReq existing = boardCommentService.selectBoardCommentReaction(user.getId().intValue(), req.getCno());
        if (existing == null) {
            boardCommentService.insertBoardCommentReaction(req);
        } else if (existing.getType().equals(req.getType())) {
            boardCommentService.deleteBoardCommentReaction(existing.getId());
        } else {
            existing.setType(req.getType());
            boardCommentService.updateBoardCommentReaction(existing);
        }
        ReactionCountDTO counts = boardCommentService.selectBoardCommentReactionCount(req.getCno());
        return ResponseEntity.ok(counts);
    }
}
```

### [백엔드] PaggingVO.java
```java
package com.spring.vo;

/**
 * 게시판 하단에 페이지 번호(1, 2, 3, 4, 5 ...)를 보여주기 위해
 * 필요한 계산을 담당하는 클래스입니다.
 * Lombok @Getter 대신 직접 get 메서드를 작성해 계산 로직을 포함시켰습니다.
 */
public class PaggingVO {
  private int count;          // 전체 게시글 개수
  private int currentPage;    // 현재 페이지 번호
  private final int PAGE_CONTENT_COUNT = 30; // 한 페이지에 보여줄 게시글 수 (고정)
  private final int PAGE_GROUP_COUNT = 5;    // 하단에 보여줄 페이지 번호 개수 (고정)

  // 2인자 생성자: size는 PAGE_CONTENT_COUNT(30)로 고정
  public PaggingVO(int count, int currentPage) {
    this.count = count;
    this.currentPage = currentPage;
  }

  public int getCurrentPage() { return currentPage; }

  // 전체 페이지 수 = 전체글수 / 페이지당글수 (나머지 있으면 +1)
  public int getTotalPage() {
    return count / PAGE_CONTENT_COUNT + (count % PAGE_CONTENT_COUNT != 0 ? 1 : 0);
  }

  // 전체 그룹 수 (예: 12페이지 → 3그룹: 1~5, 6~10, 11~12)
  public int getTotalPageGroup() {
    return getTotalPage() / PAGE_GROUP_COUNT + (getTotalPage() % PAGE_GROUP_COUNT != 0 ? 1 : 0);
  }

  // 현재 페이지가 속한 그룹 번호 (예: 페이지 7 → 2번째 그룹)
  public int getCurrentPageGroupNo() {
    return currentPage / PAGE_GROUP_COUNT + (currentPage % PAGE_GROUP_COUNT != 0 ? 1 : 0);
  }

  // 현재 그룹의 시작 페이지 (예: 2번째 그룹 → 6)
  public int getStartPageOfPageGroup() {
    return (getCurrentPageGroupNo() - 1) * PAGE_GROUP_COUNT + 1;
  }

  // 현재 그룹의 끝 페이지 (예: 2번째 그룹 → 10, 단 실제 최대값 초과 불가)
  public int getEndPageOfPageGroup() {
    return Math.min(getTotalPage(), getCurrentPageGroupNo() * PAGE_GROUP_COUNT);
  }

  // 이전 그룹 존재 여부 (첫 번째 그룹이면 false)
  public boolean isPriviousPageGroup() { return getCurrentPageGroupNo() > 1; }

  // 다음 그룹 존재 여부 (마지막 그룹이면 false)
  public boolean isNextPageGroup() { return getCurrentPageGroupNo() < getTotalPageGroup(); }
}
```

### [백엔드] SwaggerConfig.java
```java
package com.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI()
            .info(new Info().title("Step17 Board API").version("v1").description("게시판 + JWT 인증 API"))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components().addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
}
```

### [백엔드] board-mapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.spring.mapper.BoardMapper">

  <select id="selectBoardList" resultType="board">
    select * from (select ROW_NUMBER() OVER(ORDER BY b.bno desc) as rw, b.*
    from board_view b) bv
    where ceil(bv.rw/#{size}) = #{page}
  </select>

  <select id="searchBoardList" resultType="board">
    select * from (select ROW_NUMBER() OVER(ORDER BY b.bno desc) as rw, b.*
    from board_view b where b.title like concat('%',#{keyword},'%')
      OR b.content like concat('%',#{keyword},'%')) bv
    where ceil(bv.rw/${size}) = #{page}
  </select>

  <select id="boardCount" resultType="int">
    select count(*) from board_view
  </select>

  <select id="selectBoard" resultType="board">
    select * from board_view bv where bv.bno = #{bno}
  </select>

  <select id="selectBoardComment" resultType="comment">
    select * from board_comment_view bcv where bcv.bno = #{bno}
  </select>

  <!--
    useGeneratedKeys="true" keyProperty="bno":
    INSERT 후 MySQL이 생성한 AUTO_INCREMENT 값을 board.bno 필드에 자동으로 넣어준다.
    덕분에 Controller에서 board.getBno()로 새 글번호를 바로 읽을 수 있다.
  -->
  <insert id="insertBoard" useGeneratedKeys="true" keyProperty="bno">
    insert into board(title, content, mid)
    values(#{title}, #{content}, #{mid})
  </insert>

  <delete id="deleteBoard">
    delete from board where bno = #{bno}
  </delete>

  <update id="updateBoard">
    update board set title = #{title}, content = #{content}, write_update_date = CURRENT_TIMESTAMP
    where bno = #{bno}
  </update>

  <select id="selectBoardReaction" resultType="boardReq">
    select * from board_reaction where mid = #{id} and bno = #{bno}
  </select>

  <insert id="insertBoardReaction">
    insert into board_reaction(mid, bno, type) values(#{mid}, #{bno}, #{type});
  </insert>

  <!-- deleteBoardReaction/updateBoardReaction: BoardReactionReq 객체 전체를 받아 id로 처리 -->
  <delete id="deleteBoardReaction">
    DELETE FROM board_reaction WHERE id = #{id}
  </delete>

  <update id="updateBoardReaction">
    update board_reaction set type = #{type} where id = #{id}
  </update>

  <select id="selectBoardReactionCount" resultType="reaction">
    SELECT
      count(case when br.`type` = 'like' then 1 end) as likeCount,
      count(case when br.`type` = 'dislike' then 1 end) as dislikeCount
    FROM board b LEFT OUTER JOIN board_reaction br ON b.bno = br.bno
    WHERE b.bno = #{bno}
  </select>
</mapper>
```

### [백엔드] board-comment-mapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.spring.mapper.BoardCommentMapper">

  <insert id="insertBoardComment">
    INSERT INTO board_comment (bno,mid,content) VALUES (#{bno},#{mid},#{content})
  </insert>

  <select id="selectBoardComment" resultType="comment">
    SELECT * FROM board_comment_view WHERE cno = #{cno}
  </select>

  <delete id="deleteBoardComment">
    DELETE FROM board_comment WHERE cno = #{cno}
  </delete>

  <update id="updateBoardComment">
    update board_comment set content = #{content} where cno = #{cno}
  </update>

  <select id="selectBoardCommentReaction" resultType="boardCommentReq">
    select * from board_comment_reaction where mid = #{id} and cno = #{cno}
  </select>

  <insert id="insertBoardCommentReaction">
    insert into board_comment_reaction(mid,cno,type) values(#{mid},#{cno},#{type});
  </insert>

  <delete id="deleteBoardCommentReaction">
    DELETE FROM board_comment_reaction WHERE id = #{id}
  </delete>

  <update id="updateBoardCommentReaction">
    update board_comment_reaction set type = #{type} where id = #{id}
  </update>

  <select id="selectBoardCommentReactionCount" resultType="reaction">
    SELECT
      count(case when br.`type` = 'like' then 1 end) as likeCount,
      count(case when br.`type` = 'dislike' then 1 end) as dislikeCount
    FROM board_comment b LEFT OUTER JOIN board_comment_reaction br ON b.cno = br.cno
    WHERE b.cno = #{cno}
  </select>
</mapper>
```

### [백엔드] BoardService.java
```java
package com.spring.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
import com.spring.dto.BoardReactionReq;
import com.spring.dto.ReactionCountDTO;
import com.spring.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardMapper boardMapper;

  public List<BoardDTO> getBoardList(int page, int size) {
    return boardMapper.selectBoardList(page, size);
  }
  public List<BoardDTO> searchBoardList(String keyword, int page, int size) {
    return boardMapper.searchBoardList(keyword, page, size);
  }
  public int boardCount() { return boardMapper.boardCount(); }
  public BoardDTO selectBoard(int bno) { return boardMapper.selectBoard(bno); }
  public List<BoardCommentDTO> selectBoardComment(int bno) { return boardMapper.selectBoardComment(bno); }
  public void deleteBoard(int bno) { boardMapper.deleteBoard(bno); }
  public void addBoard(BoardDTO board) { boardMapper.insertBoard(board); }  // insertBoard 후 board.bno 자동 설정
  public void updateBoard(BoardDTO reqBoard) { boardMapper.updateBoard(reqBoard); }

  // 반응 관련: 파라미터가 bno + userId (Long)
  public BoardReactionReq selectBoardReaction(int bno, Long id) {
    return boardMapper.selectBoardReaction(bno, id);
  }
  public void addBoardReaction(BoardReactionReq req) { boardMapper.insertBoardReaction(req); }
  public void deleteBoardReaction(BoardReactionReq req) { boardMapper.deleteBoardReaction(req); }
  public void updateBoardReaction(BoardReactionReq req) { boardMapper.updateBoardReaction(req); }
  public ReactionCountDTO getBoardReactionCount(int bno) { return boardMapper.selectBoardReactionCount(bno); }
}
```

### [백엔드] BoardMapper.java
```java
package com.spring.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
import com.spring.dto.BoardReactionReq;
import com.spring.dto.ReactionCountDTO;

@Mapper
public interface BoardMapper {
  List<BoardDTO> selectBoardList(@Param("page") int page, @Param("size") int size);
  List<BoardDTO> searchBoardList(@Param("keyword") String keyword, @Param("page") int page, @Param("size") int size);
  int boardCount();
  BoardDTO selectBoard(int bno);
  List<BoardCommentDTO> selectBoardComment(int bno);
  void deleteBoard(int bno);
  void insertBoard(BoardDTO board);       // useGeneratedKeys → board.bno 자동 세팅
  void updateBoard(BoardDTO reqBoard);
  BoardReactionReq selectBoardReaction(@Param("bno") int bno, @Param("id") Long id);
  void insertBoardReaction(BoardReactionReq reactionReq);
  void deleteBoardReaction(BoardReactionReq reactionReq);
  void updateBoardReaction(BoardReactionReq reactionReq);
  ReactionCountDTO selectBoardReactionCount(int bno);
}
```

### [백엔드] BoardCommentMapper.java
```java
package com.spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardCommentReactionReq;
import com.spring.dto.ReactionCountDTO;

@Mapper
public interface BoardCommentMapper {
  void insertBoardComment(BoardCommentDTO comment);
  BoardCommentDTO selectBoardComment(int cno);
  void deleteBoardComment(int cno);
  void updateBoardComment(BoardCommentDTO reqBoard);
  BoardCommentReactionReq selectBoardCommentReaction(@Param("cno") int cno, @Param("id") Long id);
  void insertBoardCommentReaction(BoardCommentReactionReq reactionReq);
  void deleteBoardCommentReaction(BoardCommentReactionReq reactionReq);
  void updateBoardCommentReaction(BoardCommentReactionReq reactionReq);
  ReactionCountDTO selectBoardCommentReactionCount(int cno);
  void deleteBoardCommentByBno(int bno); // 게시글 삭제 시 해당 게시글 댓글 일괄 삭제용
}
```

### [백엔드] DTO 클래스들 (필드 목록)
- **BoardDTO** (`@Alias("board")`): bno, title, content, writeDate, writeUpdateDate, bcount, blike, bhate, mid, nickname
- **BoardCommentDTO** (`@Alias("comment")`): cno, bno, content, cdate, nickname, clike, chate, mid
- **BoardReactionReq** (`@Alias("boardReq")`): id, bno, type, mid
- **BoardCommentReactionReq** (`@Alias("boardCommentReq")`): id, cno, type, mid
- **ReactionCountDTO** (`@Alias("reaction")`): likeCount, dislikeCount
- **LoginRequest**: username, password
- **SignupRequest**: username, password, nickname
- **TokenReponse**: accessToken, refreshToken, tokenType

### [백엔드] Repository 인터페이스
```java
// UserRepository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}

// RefreshTokenRepository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(UserEntity user);
}
```

---

### [프론트엔드] package.json (주요 의존성)
```json
{
  "dependencies": {
    "axios": "^1.18.1",
    "react": "^19.2.7",
    "react-dom": "^19.2.7",
    "react-router-dom": "^7.18.0",
    "react-scripts": "5.0.1"
  }
}
```

### [프론트엔드] .env
```
REACT_APP_API_URL=http://localhost:8888
```

### [프론트엔드] src/index.js
```js
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <AuthProvider>
      <App />
    </AuthProvider>
  </BrowserRouter>
);
reportWebVitals();
```

### [프론트엔드] src/App.js
```js
import NavBar from './components/NavBar';
import { Route, Routes } from 'react-router-dom';
import PostListPage from './pages/PostListPage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import PostDetailPage from './pages/PostDetailPage';
import PostWritePage from './pages/PostWritePage';

function App() {
  return <>
    <NavBar/>
    <hr />
    <Routes>
      <Route path='/' element={<PostListPage/>}/>
      <Route path='/login' element={<LoginPage/>}/>
      <Route path='/signup' element={<SignupPage/>}/>
      <Route path='/posts/:bno' element={<PostDetailPage/>}/>
      <Route path='/posts/create' element={<PostWritePage/>}/>
      <Route path='/posts/:bno/edit' element={<PostWritePage/>}/>
    </Routes>
  </>;
}
export default App;
```

### [프론트엔드] src/api/axiosInstance.js
```js
import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8888';

const axiosInstance = axios.create({
  baseURL: API_URL,
  headers: { 'Content-Type': 'application/json' }
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosInstance;
```

### [프론트엔드] src/api/authApi.js
```js
import axiosInstance from "./axiosInstance";

export const authApi = {
  signup: (data) => axiosInstance.post('/auth/signup', data),
  login:  (data) => axiosInstance.post('/auth/login', data),
  logout: ()     => axiosInstance.post('/auth/logout'),
  me:     ()     => axiosInstance.get('/auth/me'),
};
```

### [프론트엔드] src/api/postApi.js
```js
import axiosInstance from "./axiosInstance";

export const postApi = {
  getPage: (page, keyword, size) => axiosInstance.get('/api/posts', {
    params: { page, keyword, size }
  }),
  getPost:      (bno)        => axiosInstance.get(`/api/posts/${bno}`),
  create:       (data)       => axiosInstance.post(`/api/posts`, data),
  update:       (bno, data)  => axiosInstance.patch(`/api/posts/${bno}`, data),
  remove:       (bno)        => axiosInstance.delete(`/api/posts/${bno}`),
  postReaction: (data)       => axiosInstance.post(`/api/posts/reaction`, data),
};
```

### [프론트엔드] src/api/commentApi.js
```js
import axiosInstance from "./axiosInstance";

// 미완성 — API 엔드포인트는 /api/comments 기준으로 작성 예정
const commentApi = {
  // 추가 예정
};
```
> ※ `commentApi.js`는 현재 스텁 상태. `BoardCommentController`의 엔드포인트(`POST /api/comments`, `DELETE /api/comments/{cno}`, `PATCH /api/comments/{cno}`, `POST /api/comments/reaction`)를 구현할 예정.

### [프론트엔드] src/context/AuthContext.jsx
```jsx
import { createContext, useContext, useEffect, useState } from "react";
import { authApi } from "../api/authApi";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      authApi.me()
        .then(res => setUser(res.data))
        .catch(() => localStorage.clear())
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const login = async (username, password) => {
    const loginRes = await authApi.login({ username, password });
    localStorage.setItem('accessToken', loginRes.data.accessToken);
    localStorage.setItem('refreshToken', loginRes.data.refreshToken);
    const meRes = await authApi.me();
    setUser(meRes.data);
    return meRes.data;
  };

  const logout = async () => {
    try { await authApi.logout(); } catch {}
    localStorage.clear();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, isAuthenticated: !!user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth는 AuthProvider에서만 사용 가능합니다');
  return ctx;
};
```

### [프론트엔드] src/components/QuillEditor.jsx
```jsx
import Quill from "quill";
import "quill/dist/quill.snow.css";
import { useEffect, useRef } from "react";

// 설치: npm install quill
export default ({ onChange, defaultValue }) => {
  const editorRef = useRef(null);          // 에디터 DOM 컨테이너
  const quillInstance = useRef(null);      // Quill 인스턴스 (리렌더링해도 유지)
  const onChangeRef = useRef(onChange);    // onChange 최신 참조 유지 (클로저 문제 방지)
  const initializedRef = useRef(false);   // 수정모드 초기값 1회만 설정 여부

  onChangeRef.current = onChange; // 리렌더링마다 최신 onChange 반영

  // Quill 인스턴스는 최초 1회만 생성 (빈 의존성 배열)
  useEffect(() => {
    if (editorRef.current && !quillInstance.current) {
      quillInstance.current = new Quill(editorRef.current, {
        theme: "snow",
        modules: {
          toolbar: [
            ["bold", "italic", "underline", "strike"],
            ["blockquote", "code-block"],
            ["link", "image", "video", "formula"],
            [{ header: 1 }, { header: 2 }],
            [{ list: "ordered" }, { list: "bullet" }, { list: "check" }],
            [{ script: "sub" }, { script: "super" }],
            [{ indent: "-1" }, { indent: "+1" }],
            [{ direction: "rtl" }],
            [{ size: ["small", false, "large", "huge"] }],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ color: [] }, { background: [] }],
            [{ font: [] }],
            [{ align: [] }],
            ["clean"],
          ],
        },
      });

      // 텍스트 변경 이벤트: HTML을 상위 컴포넌트로 전달
      quillInstance.current.on("text-change", () => {
        if (onChangeRef.current) {
          onChangeRef.current(quillInstance.current.getSemanticHTML());
        }
      });
    }
  }, []); // 빈 배열: 마운트 시 1회만

  // 수정 모드: API에서 본문을 받아온 뒤 최초 1회만 반영
  useEffect(() => {
    if (quillInstance.current && defaultValue && !initializedRef.current) {
      quillInstance.current.root.innerHTML = defaultValue;
      initializedRef.current = true;
    }
  }, [defaultValue]);

  return (
    <div style={{ margin: "50px" }}>
      <div ref={editorRef} style={{ height: "500px" }}></div>
    </div>
  );
};
```

### [프론트엔드] src/components/NavBar.jsx
```jsx
import { Link, useNavigate } from "react-router-dom"

export default () => {
  const isAuthenticate = false; // TODO: useAuth()로 교체 필요
  const navigate = useNavigate();
  return <nav>
    <Link to="/">📄 Spring Board</Link>
    <div>
      {isAuthenticate ? (
        <>
          <span>사용자이름</span>
          <Link to="/posts/create">글쓰기</Link>
          <button type="button">로그아웃</button>
        </>
      ) : (
        <>
          <Link to="/login">로그인</Link>
          <Link to="/signup">회원가입</Link>
        </>
      )}
    </div>
  </nav>
}
```

### [프론트엔드] src/components/PaggingBar.jsx
```jsx
export default ({ pagging, onPageChange }) => {
  const pageNumbers = [];
  for (let i = pagging.startPageOfPageGroup; i <= pagging.endPageOfPageGroup; i++)
    pageNumbers.push(i);

  return <ul>
    <li>
      <button disabled={!pagging.priviousPageGroup}
        onClick={() => onPageChange(pagging.startPageOfPageGroup - 1)}>◀◀</button>
    </li>
    {pageNumbers.map(item => (
      <li key={item}>
        <button onClick={() => onPageChange(item)}
          disabled={item === pagging.currentPage}>{item}</button>
      </li>
    ))}
    <li>
      <button disabled={!pagging.nextPageGroup}
        onClick={() => onPageChange(pagging.endPageOfPageGroup + 1)}>▶▶</button>
    </li>
  </ul>
}
```

### [프론트엔드] src/pages/LoginPage.jsx
```jsx
import { useRef, useState } from "react"
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default () => {
  const username = useRef(null);
  const password = useRef(null);
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState(null);

  const handleLogin = async () => {
    setLoading(true);
    try {
      await login(username, password);       // 버그1: .current.value 빠짐
      navigate('/api/board/list');           // 버그2: 잘못된 경로 (→ '/' 이어야 함)
    } catch (error) {
      setErrorMessage(error.response?.data?.message || '로그인 실패');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h2>로그인 페이지</h2>
      <div className="login-form">
        <input type="text" placeholder="아이디 입력" ref={username} />
        <input type="password" placeholder="비밀번호 입력" ref={password} />
        <div className="error-message">{errorMessage}</div>
        {loading
          ? <p>현재 로그인 중입니다</p>
          : <button type="button" onClick={handleLogin}>로그인</button>
        }
        <button type="button"
          onClick={() => navigate('/api/auth/signup')}>회원가입</button>  {/* 버그3: → '/signup' */}
      </div>
    </div>
  );
}
```

### [프론트엔드] src/pages/PostListPage.jsx
```jsx
import { useCallback, useEffect, useState } from "react"
import { postApi } from "../api/postApi";
import PaggingBar from "../components/PaggingBar";
import { Link } from "react-router-dom";

export default () => {
  const [posts, setPosts] = useState([]);
  const [pagging, setPagging] = useState({});

  useEffect(() => {
    postApi.getPage(1, '', 20).then(res => {
      setPosts(res.data.list);
      setPagging(res.data.pagging);
    });
  }, []);

  const fetchPostData = useCallback((pageNo) => {
    postApi.getPage(pageNo, '', 20).then(res => {
      setPosts(res.data.list);
      setPagging(res.data.pagging);
    });
  }, []);

  return <div className="container">
    <h2>게시글 목록</h2>
    <table>
      <thead>
        <tr>
          <th>글번호</th><th>제목</th><th>작성자</th>
          <th>작성일</th><th>조회수</th><th>좋아요</th><th>싫어요</th>
        </tr>
      </thead>
      <tbody>
        {posts && posts.map(item => (
          <tr key={item.bno}>
            <td>{item.bno}</td>
            <td><Link to={`/posts/${item.bno}`}>{item.title}</Link></td>
            <td>{item.nickname}</td>
            <td>{item.writeUpdateDate}</td>
            <td>{item.bcount}</td>
            <td>{item.blike}</td>
            <td>{item.bhate}</td>
          </tr>
        ))}
      </tbody>
      <tfoot>
        <tr>
          <td colSpan={7}><PaggingBar pagging={pagging} onPageChange={fetchPostData}/></td>
        </tr>
      </tfoot>
    </table>
  </div>
}
```

### [프론트엔드] src/pages/PostDetailPage.jsx
```jsx
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { postApi } from "../api/postApi";
import "quill/dist/quill.snow.css";      // Quill 스타일 — content HTML 렌더링에 필요
import { useAuth } from "../context/AuthContext";

export default () => {
  const { bno } = useParams();
  const [post, setPost] = useState({});
  const [commentList, setCommentList] = useState([]);
  const { user } = useAuth();             // 로그인 사용자 정보
  const navigate = useNavigate();

  useEffect(() => {
    postApi.getPost(bno).then(reponse => {
      setPost(reponse.data.board);
      setCommentList(reponse.data.commentList);
    }).catch(error => console.log(error));
  }, [bno]);

  // 게시글 삭제 — 작성자 본인만 가능 (서버에서도 403 반환)
  const handleDelete = async () => {
    if (window.confirm('삭제하시겠습니까?')) {
      try {
        await postApi.remove(bno);
        navigate('/');
      } catch (error) {
        if (error.response.status === 403) alert('삭제 권한이 없습니다.');
        else alert('삭제에 실패했습니다.');
      }
    }
  };

  // 좋아요/싫어요 토글 — 서버 응답의 count로 UI 즉시 갱신
  const handleReaction = async (type) => {
    await postApi.postReaction({ mid: user.id, bno: post.bno, type })
      .then(res => {
        setPost(prev => ({
          ...prev,
          blike: res.data.count.likeCount,
          bhate: res.data.count.dislikeCount
        }));
      })
      .catch(error => console.log(error));
  };

  // 작성자 본인일 때만 수정/삭제 버튼 표시
  const isEdit = user && (user.id === post.mid);

  return <div className="post-detail-container">
    {!post ? <div className="post-loading">현재 게시글 읽어오고 있습니다.</div> :
      <>
        <h2 className="post-detail-title">{post.title}</h2>
        <div className="post-detail-meta">
          <span><span className="meta-label">작성자</span> {post.nickname}</span>
          <span><span className="meta-label">조회수</span> {post.bcount}</span>
          <span><span className="meta-label">작성일</span> {post.writeUpdateDate}</span>
        </div>

        {/* Quill HTML을 그대로 렌더링 — ql-container ql-snow 클래스로 Quill 스타일 적용 */}
        <div className="post-detail-content ql-container ql-snow" style={{ border: 'none' }}>
          <div dangerouslySetInnerHTML={{ __html: post.content }}></div>
        </div>

        <div className="post-detail-footer">
          <div className="post-footer-group">
            <button onClick={() => handleReaction('like')}>좋아요 👍 {post.blike}</button>
            <button onClick={() => handleReaction('dislike')}>싫어요 👎 {post.bhate ?? 0}</button>
          </div>
          {isEdit && (
            <div className="post-footer-group">
              <button onClick={() => navigate(`/posts/${post.bno}/edit`)}>수정</button>
              <button onClick={handleDelete}>삭제</button>
            </div>
          )}
        </div>

        <div className="comment-section">
          <h3>댓글 목록 ({commentList ? commentList.length : 0})</h3>
          <div className="comment-form">
            <textarea placeholder="댓글을 입력해 주세요."></textarea>
            <button>댓글 등록</button>
          </div>
          <div className="comment-list">
            {commentList && commentList.map((item, index) => (
              <div key={item.cno || index} className="comment-item">
                <div className="comment-header">
                  <div className="comment-info">
                    <span>👤 {item.nickname}</span>
                    <span>📅 {item.cdate}</span>
                  </div>
                  <div className="comment-action">
                    <button>좋아요 👍</button>
                    <button>싫어요 👎</button>
                    <button>수정</button>
                    <button>삭제</button>
                  </div>
                </div>
                <div className="comment-content">{item.content}</div>
              </div>
            ))}
          </div>
        </div>
      </>
    }
  </div>;
}
```

### [프론트엔드] src/pages/PostWritePage.jsx (글쓰기 + 수정 통합)
```jsx
import { useEffect, useState } from "react";
import QuillEditor from "../components/QuillEditor";
import { useNavigate, useParams } from "react-router-dom";
import { postApi } from "../api/postApi";

export default () => {
  const { bno } = useParams();               // URL에 bno 있으면 수정 모드
  const isEditorMode = !!bno;               // 수정 모드 여부
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({ title: '', content: '' });
  const [error, setError] = useState('');

  // 수정 모드: 마운트 시 기존 게시글 내용 불러오기
  useEffect(() => {
    if (!isEditorMode) return;
    postApi.getPost(bno)
      .then(reponse => {
        setForm(prev => ({
          ...prev,
          title: reponse.data.board.title,
          content: reponse.data.board.content
        }));
      })
      .catch(() => { alert('게시글 정보를 불러오는데 실패했습니다.'); navigate('/'); })
      .finally(() => setLoading(false));
  }, [bno, isEditorMode]);

  const onChangePostDetail = (newContent) => {
    setForm(prev => ({ ...prev, content: newContent }));
  };
  const onChangeTitle = (e) => {
    setForm(prev => ({ ...prev, title: e.target.value }));
  };

  const handleSubmit = async () => {
    if (!form.title.trim() || !form.content.trim()) {
      alert('제목과 내용을 입력해 주세요');
      return;
    }
    try {
      setLoading(true);
      if (isEditorMode) {
        await postApi.update(bno, form);          // PATCH → 204 No Content
        navigate(`/posts/${bno}`);
      } else {
        const res = await postApi.create(form);   // POST → 200 with { board: { bno, ... } }
        navigate(`/posts/${res.data.board.bno}`); // useGeneratedKeys로 받은 bno로 이동
      }
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  return <div className="container">
    <h2>{isEditorMode ? '게시글 수정' : '게시글 쓰기'}</h2>
    <div className="post-title">
      <label>제목</label>
      <input type="text" placeholder="제목을 입력하세요" onChange={onChangeTitle} value={form.title} />
    </div>
    <div className="post-detail">
      <label>내용</label>
      {/* key를 bno || "create"로 설정 → 수정/작성 전환 시 QuillEditor 완전 재생성 */}
      <QuillEditor key={bno || "create"} onChange={onChangePostDetail} defaultValue={form.content} />
    </div>
    {error && <div className="error-box">{error}</div>}
    <div className="post-actions">
      <button onClick={handleSubmit} disabled={loading}>
        {loading ? '저장 중.....' : (isEditorMode ? '수정하기' : '글쓰기')}
      </button>
      <button onClick={() => navigate(-1)}>취소</button>
    </div>
  </div>;
}
```

### [프론트엔드] src/pages/SignupPage.jsx (미완성)
```jsx
// 현재 스텁 상태 — 회원가입 폼은 미구현
export default () => <div className="container"><h2>회원가입 페이지</h2></div>
```

---

## DB 주요 구조 (board.sql 핵심)

```sql
-- ① 테이블 생성
CREATE TABLE board_member (
  id       int         NOT NULL AUTO_INCREMENT,  -- JPA UserEntity의 PK (id)
  username VARCHAR(20) NOT NULL UNIQUE,
  password CHAR(255)   NULL,
  nickname VARCHAR(10) NULL,
  role     varchar(20) NULL,
  PRIMARY KEY (id)
);

CREATE TABLE board (
  bno               BIGINT      NOT NULL AUTO_INCREMENT,
  title             VARCHAR(50) NULL,
  content           LONGTEXT    NULL,
  write_date        DATETIME    NULL DEFAULT now(),
  mid               int         NOT NULL,          -- board_member.id 참조
  bcount            int         NULL DEFAULT 0,
  write_update_date DATETIME    NULL DEFAULT now(),
  PRIMARY KEY (bno)
);

CREATE TABLE board_comment (
  cno     BIGINT        NOT NULL AUTO_INCREMENT,
  content VARCHAR(1000) NULL,
  cdate   DATETIME      NULL DEFAULT now(),
  mid     int           NOT NULL,
  bno     BIGINT        NOT NULL,
  PRIMARY KEY (cno)
);

CREATE TABLE board_reaction (
  id   int    NOT NULL AUTO_INCREMENT,
  mid  int    NOT NULL,
  bno  BIGINT NOT NULL,
  type varchar(10),
  PRIMARY KEY (id)
);

CREATE TABLE board_comment_reaction (
  id   int    NOT NULL AUTO_INCREMENT,
  mid  int    NOT NULL,
  cno  BIGINT NOT NULL,
  type varchar(10),
  PRIMARY KEY (id)
);

CREATE TABLE refresh_tokens (
  expires_at datetime(6) NOT NULL,
  id         int         NOT NULL AUTO_INCREMENT,
  user_id    int         NOT NULL UNIQUE,
  token      varchar(600) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

-- ② 외래키 + CASCADE 설정
ALTER TABLE board
  ADD CONSTRAINT FK_board_member_TO_board FOREIGN KEY (mid) REFERENCES board_member(id);

ALTER TABLE board_comment
  ADD CONSTRAINT FK_board_member_TO_board_comment FOREIGN KEY (mid) REFERENCES board_member(id);

-- 게시글 삭제 시 댓글도 자동 삭제 (ON DELETE CASCADE)
ALTER TABLE board_comment
  ADD CONSTRAINT FK_board_TO_board_comment FOREIGN KEY (bno) REFERENCES board(bno) ON DELETE CASCADE;

-- 게시글/댓글 삭제 시 반응도 자동 삭제
ALTER TABLE board_reaction
  ADD CONSTRAINT FK_board_TO_board_reaction FOREIGN KEY (bno) REFERENCES board(bno) ON DELETE CASCADE;

ALTER TABLE board_comment_reaction
  ADD CONSTRAINT FK_board_comment_TO_board_comment_reaction
    FOREIGN KEY (cno) REFERENCES board_comment(cno) ON DELETE CASCADE;

ALTER TABLE refresh_tokens
  ADD CONSTRAINT FK_board_member_TO_refresh FOREIGN KEY (user_id) REFERENCES board_member(id);

-- ③ 중복 반응 방지 UNIQUE 제약
ALTER TABLE board_reaction ADD CONSTRAINT UNIQUE (mid, bno);
ALTER TABLE board_comment_reaction ADD CONSTRAINT UNIQUE (mid, cno);

-- ④ VIEW 정의 (MySQL 8.0 WITH CTE 문법 사용)
CREATE OR REPLACE VIEW board_view AS
WITH board_content_like AS (
  SELECT bno, count(*) AS blike FROM board_reaction WHERE type = 'like' GROUP BY bno
),
board_content_hate AS (
  SELECT bno, count(*) AS bhate FROM board_reaction WHERE type = 'dislike' GROUP BY bno
)
SELECT
  b.bno, b.title, b.write_date, b.write_update_date,
  b.content, b.bcount, bm.id AS mid, bm.nickname,
  IFNULL(bcl.blike, 0) AS blike, IFNULL(bch.bhate, 0) AS bhate
FROM board b
LEFT JOIN board_member bm ON b.mid = bm.id
LEFT JOIN board_content_like bcl ON bcl.bno = b.bno
LEFT JOIN board_content_hate bch ON bch.bno = b.bno;

CREATE OR REPLACE VIEW board_comment_view AS
WITH board_comment_like AS (
  SELECT cno, count(*) AS clike FROM board_comment_reaction WHERE type = 'LIKE' GROUP BY cno
),
board_comment_dislike AS (
  SELECT cno, count(*) AS chate FROM board_comment_reaction WHERE type = 'DISLIKE' GROUP BY cno
)
SELECT bc.*, bm.nickname,
  IFNULL(bcl.clike, 0) AS clike, IFNULL(bch.chate, 0) AS chate
FROM board_comment bc
LEFT JOIN board_member bm ON bm.id = bc.mid
LEFT JOIN board_comment_like bcl ON bc.cno = bcl.cno
LEFT JOIN board_comment_dislike bch ON bc.cno = bch.cno;

-- ⑤ 페이징 방법 2가지
-- 방법1: LIMIT + OFFSET (단순)
SELECT * FROM board_view ORDER BY bno DESC LIMIT 30 OFFSET 0; -- 1페이지

-- 방법2: ROW_NUMBER() (MyBatis XML에서 사용하는 방식)
SELECT * FROM (
  SELECT ROW_NUMBER() OVER(ORDER BY b.bno DESC) AS rw, b.*
  FROM board_view b
) bv WHERE CEIL(bv.rw / 30) = 1; -- 1페이지
```

## 기존 DB FK 마이그레이션 (board-fk-migration.sql)

기존 DB에 CASCADE가 없는 경우 이 파일로 FK를 재설정합니다.

```sql
USE new_board_db;

-- 기존 FK 삭제 후 CASCADE로 재설정
ALTER TABLE board_comment DROP FOREIGN KEY fk_comment_board;
ALTER TABLE board_comment
  ADD CONSTRAINT FK_board_TO_board_comment
    FOREIGN KEY (bno) REFERENCES board(bno) ON DELETE CASCADE;

ALTER TABLE board_reaction
  ADD CONSTRAINT FK_board_TO_board_reaction
    FOREIGN KEY (bno) REFERENCES board(bno) ON DELETE CASCADE;
```

---

## 오늘 추가된 핵심 포인트 (2025-06-29)

1. **QuillEditor.jsx** — Quill.js 리치 텍스트 에디터 컴포넌트. `useRef` 3개 패턴(editorRef / quillInstance / onChangeRef)과 `initializedRef`로 수정 모드 초기값 1회 반영 처리.
2. **PostWritePage.jsx** — 스텁에서 완성 페이지로 전환. `isEditorMode = !!bno`로 글쓰기/수정 분기. `useGeneratedKeys`로 받은 bno로 작성 후 상세 페이지 이동.
3. **PostDetailPage.jsx** — `dangerouslySetInnerHTML`로 Quill HTML 렌더링, `handleReaction`으로 좋아요/싫어요 토글 후 서버 응답으로 UI 즉시 갱신, `isEdit`로 본인 글만 수정/삭제 버튼 노출.
4. **postApi.js** — `create`, `update`, `remove`, `postReaction` 추가.
5. **commentApi.js** — 신규(미완성 스텁). 내일 댓글 CRUD와 함께 구현 예정.
6. **board-mapper.xml** — `insertBoard`에 `useGeneratedKeys="true" keyProperty="bno"` 추가 → 작성 후 생성된 bno를 Controller에서 바로 응답 가능.
7. **BoardController/BoardService** — 메서드명 정리(`selectBoardList→getBoardList`, `insertBoard→addBoard` 등), 반응(reaction) 파라미터를 DTO 객체로 통일, 좋아요 응답 포맷 `{ count: { likeCount, dislikeCount } }`.
8. **PaggingVO.java** — 생성자를 3인자→2인자로 변경, size는 내부 상수(30)로 고정.
9. **board.sql** — WITH CTE 문법으로 VIEW 재정의, `board_member.id` 컬럼명 확인.
10. **board-fk-migration.sql** — 기존 DB에 CASCADE FK가 없는 경우 재설정용 마이그레이션 파일.

---

## 내일 진도 예정 (댓글 기능 완성)

- `commentApi.js` 완성: `createComment`, `deleteComment`, `updateComment`, `postCommentReaction`
- `PostDetailPage.jsx` 댓글 입력폼 연동 (댓글 작성/수정/삭제/좋아요)
- `BoardCommentController` & `BoardCommentService` 복습 및 활용
- `deleteBoardCommentByBno` — 게시글 삭제 시 댓글 일괄 처리

---

## 로그인 버그 3개 (LoginPage.jsx) — 꼭 해설 섹션 만들어줘

1. `login(username, password)` → `useRef` 객체 자체가 전달됨. 정답: `login(username.current.value, password.current.value)`
2. `navigate('/api/board/list')` → 백엔드 URL을 프론트 경로로 잘못 사용. 정답: `navigate('/')`
3. `navigate('/api/auth/signup')` → 마찬가지. 정답: `navigate('/signup')`

---

## 실행 방법

### 백엔드
```bash
cd step17-board-backend
./gradlew bootRun
# http://localhost:8888
# Swagger: http://localhost:8888/swagger-ui/index.html
```

### 프론트엔드
```bash
cd step17-board-front
npm install
npm start
# http://localhost:3000
```

---

## 기능 추가할 때 프론트↔백 작업 순서 (초보자용 체크리스트)

> "어떤 순서로 파일을 건드려야 할지 모르겠다"는 분들을 위한 가이드.
> 새 기능(댓글 작성, 좋아요 등)을 추가할 때마다 아래 순서를 따르면 됩니다.

### 전체 흐름 다이어그램

```
[React 화면 클릭]
      ↓
[commentApi.js / postApi.js]  ← ① 프론트 API 파일
      ↓  (axios HTTP 요청)
[BoardCommentController.java]  ← ② 백엔드 Controller
      ↓
[BoardCommentService.java]     ← ③ 백엔드 Service
      ↓
[BoardCommentMapper.java]      ← ④ 백엔드 Mapper 인터페이스
      ↓
[board-comment-mapper.xml]     ← ⑤ SQL XML
      ↓
[MySQL DB]                     ← ⑥ 실제 데이터
      ↑ (결과 반환)
[React useState로 화면 갱신]   ← ⑦ 프론트 상태 업데이트
```

---

### STEP ① DB 테이블/VIEW 확인 (MySQL)

새 기능에 필요한 컬럼이 있는지 먼저 확인합니다.

```sql
-- 댓글 기능이라면 이 테이블들이 준비되어 있는지 확인
DESC board_comment;
DESC board_comment_reaction;
SELECT * FROM board_comment_view LIMIT 5;
```

아직 없으면 `ALTER TABLE` 또는 `CREATE TABLE` 먼저 실행.

---

### STEP ② Mapper 인터페이스에 메서드 선언 (Java)

`BoardCommentMapper.java`에 메서드 시그니처만 추가합니다.
SQL은 아직 안 써도 됩니다. 인터페이스니까 몸통 없음.

```java
// 예: 댓글 작성
void insertBoardComment(BoardCommentDTO comment);

// 예: 댓글 반응 조회
BoardCommentReactionReq selectBoardCommentReaction(
    @Param("cno") int cno, @Param("id") Long id);
```

---

### STEP ③ Mapper XML에 SQL 작성

`board-comment-mapper.xml`에 실제 SQL을 씁니다.
id는 반드시 Mapper 인터페이스 메서드 이름과 일치해야 합니다.

```xml
<insert id="insertBoardComment">
  INSERT INTO board_comment (bno, mid, content)
  VALUES (#{bno}, #{mid}, #{content})
</insert>

<select id="selectBoardCommentReaction" resultType="boardCommentReq">
  SELECT * FROM board_comment_reaction
  WHERE mid = #{id} AND cno = #{cno}
</select>
```

---

### STEP ④ Service에 비즈니스 로직 작성

Mapper를 주입받아 필요한 처리를 합니다.
단순 CRUD라면 Mapper 호출만 해도 충분합니다.

```java
// BoardCommentService.java
public void insertBoardComment(BoardCommentDTO comment) {
    boardCommentMapper.insertBoardComment(comment);
}

public BoardCommentReactionReq selectBoardCommentReaction(int cno, Long id) {
    return boardCommentMapper.selectBoardCommentReaction(cno, id);
}
```

---

### STEP ⑤ Controller에 API 엔드포인트 만들기

HTTP 메서드와 URL을 결정합니다.
RESTful 규칙: 생성=POST, 조회=GET, 수정=PATCH, 삭제=DELETE

```java
// BoardCommentController.java
@PostMapping
public ResponseEntity<?> createComment(
    @RequestBody BoardCommentDTO comment,
    @AuthenticationPrincipal UserEntity user   // JWT에서 자동으로 꺼내줌
) {
    comment.setMid(user.getId().intValue());
    boardCommentService.insertBoardComment(comment);
    return ResponseEntity.status(HttpStatus.CREATED).build();
}
```

> **팁**: Swagger(`http://localhost:8888/swagger-ui/index.html`)에서 바로 테스트 가능합니다.  
> Controller가 잘 동작하는지 확인한 뒤 프론트로 넘어가세요.

---

### STEP ⑥ 프론트 API 파일 작성 (JavaScript)

axios로 백엔드 엔드포인트를 호출하는 함수를 추가합니다.

```js
// commentApi.js
import axiosInstance from "./axiosInstance";

export const commentApi = {
  createComment: (data)  => axiosInstance.post('/api/comments', data),
  deleteComment: (cno)   => axiosInstance.delete(`/api/comments/${cno}`),
  updateComment: (cno, data) => axiosInstance.patch(`/api/comments/${cno}`, data),
  postReaction:  (data)  => axiosInstance.post('/api/comments/reaction', data),
};
```

---

### STEP ⑦ React 컴포넌트에서 호출 + UI 연결

`useState`로 데이터를 관리하고, 이벤트 핸들러에서 API를 호출합니다.

```jsx
// PostDetailPage.jsx — 댓글 작성 예시
import { commentApi } from "../api/commentApi";

const [newComment, setNewComment] = useState('');

const handleCommentSubmit = async () => {
    if (!newComment.trim()) return;
    try {
        await commentApi.createComment({ bno: post.bno, content: newComment });
        setNewComment('');                     // 입력창 초기화
        // 댓글 목록 새로 불러오기
        const res = await postApi.getPost(bno);
        setCommentList(res.data.commentList);
    } catch (err) {
        alert('댓글 작성 실패');
    }
};

// JSX
<textarea value={newComment} onChange={(e) => setNewComment(e.target.value)} />
<button onClick={handleCommentSubmit}>댓글 등록</button>
```

---

### 리액트 핵심 개념 3가지 (이것만 이해하면 됩니다)

| 개념 | 언제 쓰나 | 한 줄 설명 |
|------|-----------|-----------|
| `useState` | 화면에 보이는 데이터가 바뀔 때 | 값이 바뀌면 화면을 다시 그려줌 |
| `useEffect` | 컴포넌트가 화면에 나타날 때 (또는 특정 값이 바뀔 때) API 호출 | 의존성 배열의 값이 바뀔 때 실행 |
| `useRef` | 화면을 다시 그리지 않고 값을 기억할 때, DOM 직접 접근 | 렌더링과 무관하게 값 보존 |

```
useState  → "이 값이 바뀌면 화면도 바꿔줘"
useEffect → "이 타이밍에 이 코드를 실행해줘"
useRef    → "렌더링 없이 이 값/DOM을 기억해줘"
```

---

## 내일 진도 — 댓글 기능 완성 구현 가이드

### 구현 목표
- 댓글 작성 (POST /api/comments)
- 댓글 삭제 (DELETE /api/comments/{cno})
- 댓글 수정 (PATCH /api/comments/{cno})
- 댓글 좋아요/싫어요 토글 (POST /api/comments/reaction)

### 댓글 작성 전체 흐름

```
[PostDetailPage.jsx]
  댓글 textarea 입력
  → "댓글 등록" 버튼 클릭
  → commentApi.createComment({ bno, content })
  → POST /api/comments  (JWT 토큰 자동 첨부)
  → BoardCommentController.createComment()
  → comment.setMid(user.getId())  // 로그인 사용자 ID 세팅
  → BoardCommentService.insertBoardComment(comment)
  → BoardCommentMapper.insertBoardComment(comment)
  → INSERT INTO board_comment ...
  → 201 Created 응답
  → 프론트: 댓글 목록 재조회 후 화면 갱신
```

### 댓글 좋아요/싫어요 토글 알고리즘

게시글 좋아요와 똑같은 패턴입니다. 토글 3가지 경우:

```
기존 반응 없음   → INSERT (새로 추가)
같은 타입 클릭   → DELETE (취소)
다른 타입 클릭   → UPDATE (변경)
```

```java
// BoardCommentController.java — reaction 엔드포인트
@PostMapping("/reaction")
public ResponseEntity<Map<String,Object>> reaction(
    @RequestBody BoardCommentReactionReq req,
    @AuthenticationPrincipal UserEntity user
) {
    Map<String, Object> map = new HashMap<>();
    BoardCommentReactionReq existing =
        boardCommentService.selectBoardCommentReaction(req.getCno(), user.getId());

    if (existing == null) {
        req.setMid(user.getId().intValue());
        boardCommentService.insertBoardCommentReaction(req);
    } else {
        req.setId(existing.getId());
        if (req.getType().equals(existing.getType()))
            boardCommentService.deleteBoardCommentReaction(req);
        else
            boardCommentService.updateBoardCommentReaction(req);
    }

    ReactionCountDTO count = boardCommentService.selectBoardCommentReactionCount(req.getCno());
    map.put("count", count);
    return ResponseEntity.ok(map);
}
```

### 댓글 수정 UX 패턴

수정 버튼 클릭 → 해당 댓글을 textarea로 전환 → 저장 클릭 → PATCH 요청

```jsx
// 수정 중인 댓글 번호를 state로 관리
const [editingCno, setEditingCno] = useState(null);
const [editContent, setEditContent] = useState('');

// 수정 저장
const handleCommentUpdate = async (cno) => {
    await commentApi.updateComment(cno, { content: editContent });
    setEditingCno(null);   // 수정 모드 종료
    // 목록 재조회
};

// JSX — 수정 모드 분기
{editingCno === item.cno ? (
    <>
        <textarea value={editContent} onChange={e => setEditContent(e.target.value)} />
        <button onClick={() => handleCommentUpdate(item.cno)}>저장</button>
        <button onClick={() => setEditingCno(null)}>취소</button>
    </>
) : (
    <div>{item.content}</div>
)}
```

### 오늘 배운 패턴과 비교

| 오늘 (게시글) | 내일 (댓글) |
|--------------|------------|
| `postApi.create(form)` | `commentApi.createComment({bno, content})` |
| `postApi.remove(bno)` | `commentApi.deleteComment(cno)` |
| `postApi.update(bno, form)` | `commentApi.updateComment(cno, {content})` |
| `postApi.postReaction(data)` | `commentApi.postReaction(data)` |
| `BoardController` | `BoardCommentController` |
| `BoardService` | `BoardCommentService` |
| `board_reaction` 테이블 | `board_comment_reaction` 테이블 |

> 구조가 완전히 동일합니다. 오늘 게시글 기능을 이해했다면 댓글도 같은 패턴입니다.
