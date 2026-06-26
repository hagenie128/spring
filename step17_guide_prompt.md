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
- 22: API 레이어 (authApi.js, postApi.js)
- 23: NavBar 컴포넌트 (TODO 미완성 포함 설명)
- 24: 게시글 목록 페이지 (PostListPage — useEffect, useState, map)
- 25: 게시글 상세 페이지 (PostDetailPage — useParams, 댓글 목록)
- 26: 로그인 페이지 (LoginPage — useRef, useAuth, 버그 3개 해설)
- 27: 페이징 컴포넌트 (PaggingBar — props, 버튼 생성 알고리즘)
- 28: 미완성 페이지 (SignupPage, PostWritePage — 앞으로 만들어야 할 것)

### PART 3 · 연동 흐름
- 29: 로그인 전체 흐름 (React → Spring Security → JWT → localStorage)
- 30: 게시글 목록 조회 흐름 (useEffect → axiosInstance → Controller → MyBatis → MySQL → 화면)
- 31: CORS 설정 (왜 필요한지, SecurityConfig에서 어떻게 설정하는지)
- 32: 자주 막히는 오류 & 해결법

### 마무리
- 33: 요즘 트렌드 (Next.js, TanStack Query, Zustand, MSW, Docker 등)
- 34: 핵심 용어 사전
- 35: 공부 순서 & 다음 단계

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

import com.spring.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

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

import com.spring.dto.*;
import com.spring.entity.UserEntity;
import com.spring.service.BoardService;
import com.spring.vo.PaggingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<?> getBoardList(
        @RequestParam(defaultValue = "") String keyword,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        int total = boardService.boardCount();
        PaggingVO pagging = new PaggingVO(total, page, size);
        var list = keyword.isBlank()
            ? boardService.selectBoardList(page, size)
            : boardService.searchBoardList(keyword, page, size);
        return ResponseEntity.ok(Map.of("list", list, "pagging", pagging));
    }

    @GetMapping("/{bno}")
    public ResponseEntity<?> getBoard(@PathVariable int bno) {
        BoardDTO board = boardService.selectBoard(bno);
        var commentList = boardService.selectBoardComment(bno);
        return ResponseEntity.ok(Map.of("board", board, "commentList", commentList));
    }

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardDTO board,
                                         @AuthenticationPrincipal UserEntity user) {
        board.setMid(user.getId().intValue());
        boardService.insertBoard(board);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{bno}")
    public ResponseEntity<?> deleteBoard(@PathVariable int bno,
                                         @AuthenticationPrincipal UserEntity user) {
        boardService.deleteBoard(bno);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{bno}")
    public ResponseEntity<?> updateBoard(@PathVariable int bno,
                                         @RequestBody BoardDTO board,
                                         @AuthenticationPrincipal UserEntity user) {
        board.setBno(bno);
        boardService.updateBoard(board);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reaction")
    public ResponseEntity<?> reaction(@RequestBody BoardReactionReq req,
                                      @AuthenticationPrincipal UserEntity user) {
        req.setMid(user.getId().intValue());
        BoardReactionReq existing = boardService.selectBoardReaction(user.getId().intValue(), req.getBno());
        if (existing == null) {
            boardService.insertBoardReaction(req);
        } else if (existing.getType().equals(req.getType())) {
            boardService.deleteBoardReaction(existing.getId());
        } else {
            existing.setType(req.getType());
            boardService.updateBoardReaction(existing);
        }
        ReactionCountDTO counts = boardService.selectBoardReactionCount(req.getBno());
        return ResponseEntity.ok(counts);
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

import lombok.Getter;

@Getter
public class PaggingVO {
    private static final int PAGE_CONTENT_COUNT = 30; // 한 페이지에 보여줄 게시글 수
    private static final int PAGE_GROUP_COUNT = 5;    // 한 페이지 그룹에 보여줄 페이지 번호 수

    private int totalCount;       // 전체 게시글 수
    private int currentPage;      // 현재 페이지
    private int size;             // 페이지당 항목 수
    private int totalPage;        // 전체 페이지 수
    private int totalPageGroup;   // 전체 페이지 그룹 수
    private int currentPageGroupNo; // 현재 페이지 그룹 번호
    private int startPageOfPageGroup; // 현재 그룹의 시작 페이지
    private int endPageOfPageGroup;   // 현재 그룹의 끝 페이지
    private boolean priviousPageGroup; // 이전 그룹 존재 여부
    private boolean nextPageGroup;     // 다음 그룹 존재 여부

    public PaggingVO(int totalCount, int currentPage, int size) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.size = size;

        this.totalPage = (int) Math.ceil((double) totalCount / size);
        this.totalPageGroup = (int) Math.ceil((double) totalPage / PAGE_GROUP_COUNT);
        this.currentPageGroupNo = (int) Math.ceil((double) currentPage / PAGE_GROUP_COUNT);
        this.startPageOfPageGroup = (currentPageGroupNo - 1) * PAGE_GROUP_COUNT + 1;
        this.endPageOfPageGroup = Math.min(currentPageGroupNo * PAGE_GROUP_COUNT, totalPage);
        this.priviousPageGroup = currentPageGroupNo > 1;
        this.nextPageGroup = currentPageGroupNo < totalPageGroup;
    }
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
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
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

  <insert id="insertBoard">
    insert into board(title,content,mid) values(#{title},#{content},#{mid})
  </insert>

  <delete id="deleteBoard">
    delete from board where bno = #{bno}
  </delete>

  <update id="updateBoard">
    update board set title = #{title}, content = #{content},
    write_update_date = CURRENT_TIMESTAMP where bno = #{bno}
  </update>

  <select id="selectBoardReaction" resultType="boardReq">
    select * from board_reaction where mid = #{id} and bno = #{bno}
  </select>

  <insert id="insertBoardReaction">
    insert into board_reaction(mid,bno,type) values(#{mid},#{bno},#{type});
  </insert>

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
  getPost: (bno) => axiosInstance.get(`/api/posts/${bno}`),
};
```

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
import { useParams } from "react-router-dom";
import { postApi } from "../api/postApi";

export default () => {
  const { bno } = useParams();
  const [post, setPost] = useState({});
  const [commentList, setCommentList] = useState([]);

  useEffect(() => {
    postApi.getPost(bno).then(res => {
      setPost(res.data.board);
      setCommentList(res.data.commentList);
    }).catch(err => console.error(err));
  }, [bno]);

  return <div className="container post-detail">
    {!post ? <div>Loading...</div> : (
      <>
        <h2 className="post-title">{post.title}</h2>
        <div className="post-meta">
          <span>작성자: {post.nickname}</span>
          <span>조회수: {post.bcount}</span>
          <span>최종 수정일: {post.writeUpdateDate}</span>
        </div>
        <div className="post-content">{post.content}</div>
        <div className="post-footer">
          <button>좋아요 👍 {post.blike}</button>
          <button>싫어요 👎 {post.bhate}</button>
          <button>수정</button>
          <button>삭제</button>
          <button>목록으로</button>
        </div>
        <div className="comment-area">
          <h3>댓글 {commentList.length}개</h3>
          <div className="comment-form">
            <textarea placeholder="댓글을 입력하세요" rows={4} />
            <button>댓글 작성</button>
          </div>
          <div className="comment-list">
            {commentList.map(item => (
              <div className="comment-item" key={item.cno}>
                <div className="comment-info">
                  <span>👤 {item.nickname}</span>
                  <span>📅 {item.cdate}</span>
                </div>
                <div className="comment-content">{item.content}</div>
                <div className="comment-actions">
                  <button>좋아요 👍</button>
                  <button>싫어요 👎</button>
                  <button>수정</button>
                  <button>삭제</button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </>
    )}
  </div>
}
```

### [프론트엔드] src/pages/SignupPage.jsx & PostWritePage.jsx
```jsx
// SignupPage.jsx — 미완성 스텁
export default () => <div className="container"><h2>회원가입 페이지</h2></div>

// PostWritePage.jsx — 미완성 스텁
export default () => <div className="container"><h2>글쓰기</h2></div>
```

---

## DB 주요 구조 (board.sql 핵심)

```sql
-- 회원
CREATE TABLE board_member (
  mid BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(50),
  role VARCHAR(20) DEFAULT 'ROLE_USER'
);

-- 게시글
CREATE TABLE board (
  bno INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  content TEXT,
  mid BIGINT,
  write_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  write_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  bcount INT DEFAULT 0,
  FOREIGN KEY (mid) REFERENCES board_member(mid)
);

-- 댓글
CREATE TABLE board_comment (
  cno INT PRIMARY KEY AUTO_INCREMENT,
  bno INT, mid BIGINT, content TEXT,
  cdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (bno) REFERENCES board(bno),
  FOREIGN KEY (mid) REFERENCES board_member(mid)
);

-- 게시글 반응 (좋아요/싫어요)
CREATE TABLE board_reaction (
  id INT PRIMARY KEY AUTO_INCREMENT,
  mid BIGINT, bno INT,
  type ENUM('like','dislike'),
  UNIQUE KEY (mid, bno)
);

-- 댓글 반응
CREATE TABLE board_comment_reaction (
  id INT PRIMARY KEY AUTO_INCREMENT,
  mid BIGINT, cno INT,
  type ENUM('like','dislike'),
  UNIQUE KEY (mid, cno)
);

-- Refresh Token
CREATE TABLE refresh_tokens (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT UNIQUE,
  token VARCHAR(600) UNIQUE,
  expires_at DATETIME,
  FOREIGN KEY (user_id) REFERENCES board_member(mid)
);

-- 뷰 (좋아요/싫어요 집계 포함)
CREATE VIEW board_view AS
  SELECT b.*, bm.nickname,
    COUNT(CASE WHEN br.type='like' THEN 1 END) AS blike,
    COUNT(CASE WHEN br.type='dislike' THEN 1 END) AS bhate
  FROM board b
  JOIN board_member bm ON b.mid = bm.mid
  LEFT JOIN board_reaction br ON b.bno = br.bno
  GROUP BY b.bno;

CREATE VIEW board_comment_view AS
  SELECT bc.*, bm.nickname,
    COUNT(CASE WHEN bcr.type='like' THEN 1 END) AS clike,
    COUNT(CASE WHEN bcr.type='dislike' THEN 1 END) AS chate
  FROM board_comment bc
  JOIN board_member bm ON bc.mid = bm.mid
  LEFT JOIN board_comment_reaction bcr ON bc.cno = bcr.cno
  GROUP BY bc.cno;
```

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
