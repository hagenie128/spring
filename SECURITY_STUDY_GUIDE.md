# Step15~17 보충 학습 가이드

> 선생님 저장소 `nam2626/green-04-spring`의 2026-06-24 최신 커밋
> `e9600b5` 기준으로 새 수업 프로젝트를 가져와 정리한 문서입니다.

## 먼저 큰 그림부터

이번 수업은 기존의 서버 세션 로그인에서 다음 구조로 넘어가는 과정입니다.

```text
Step14: Thymeleaf + 세션 기반 게시판
   ↓
Step15: React + REST API + Spring Security + JWT
   ↓
Step16: JWT 인증에 Google OAuth2 로그인 추가
   ↓
Step17: JWT 인증을 게시판 MyBatis API에 결합
```

핵심 변화는 서버가 HTML을 만들어 주는 방식에서, React가 화면을 그리고 Spring Boot는
JSON만 주고받는 REST API 서버가 된다는 점입니다.

## 프로젝트별 역할

| 프로젝트 | 역할 | 먼저 볼 파일 |
|---|---|---|
| `step15-security-basic` | 일반 회원가입·로그인과 JWT 인증 백엔드 | `SecurityConfig`, `AuthService`, `JwtTokenProvider`, `JwtAuthenticationFilter` |
| `step15-security-basic-front` | JWT API를 호출하는 React 연습 화면 | `src/App.js` |
| `step16-security-oauth2-back` | Google OAuth2 로그인과 자체 JWT 연결 | `CustomOAuth2UserService`, `OAuth2SuccessHandler` |
| `step16-security-oauth2-front` | 일반 로그인과 Google 로그인을 함께 테스트 | `src/App.js` |
| `step17-board-backend` | JWT 인증 + MyBatis 게시판 REST API | `BoardController`, `BoardService`, `BoardMapper`, `board-mapper.xml` |

## Step15: JWT 인증

### 1. 인증과 인가

- 인증(Authentication): 사용자가 누구인지 확인하는 것
- 인가(Authorization): 인증된 사용자가 해당 기능을 사용할 수 있는지 확인하는 것

예를 들어 로그인은 인증이고, 로그인한 사용자만 글을 작성하게 막는 것은 인가입니다.

### 2. JWT의 모양과 역할

JWT는 다음 세 부분으로 구성됩니다.

```text
header.payload.signature
```

- Header: 서명 알고리즘 등 토큰 정보
- Payload: 사용자 아이디, 권한, 발급·만료 시각
- Signature: 토큰 위변조를 확인하는 서명

Payload는 암호문이 아닙니다. Base64 형태라 누구나 내용을 볼 수 있으므로 비밀번호나
주민번호 같은 민감 정보는 넣으면 안 됩니다.

### 3. Access Token과 Refresh Token

| 토큰 | 용도 | 이 프로젝트의 만료 시간 |
|---|---|---|
| Access Token | 보호된 API 호출 | 30분 |
| Refresh Token | Access Token 재발급 | 7일 |

Access Token은 요청마다 다음 헤더에 넣습니다.

```http
Authorization: Bearer eyJ...
```

Refresh Token은 DB에도 저장합니다. 그래야 로그아웃할 때 삭제하거나 서버에서 강제로
재발급을 막을 수 있습니다.

### 4. 회원가입 흐름

```text
POST /auth/signup
  → AuthController
  → AuthService.signup()
  → 아이디 중복 검사
  → BCrypt로 비밀번호 해시
  → UserRepository.save()
```

`PasswordEncoder.encode()` 결과만 DB에 저장합니다. BCrypt는 단방향 해시이므로 원래
비밀번호를 복호화하지 않고 `matches()`로 일치 여부만 검사합니다.

### 5. 로그인 흐름

```text
POST /auth/login
  → AuthenticationManager.authenticate()
  → UserDetailServiceImpl이 DB 회원 조회
  → PasswordEncoder가 비밀번호 비교
  → AuthService가 Access/Refresh Token 생성
  → Refresh Token DB 저장
  → 토큰 두 개를 JSON으로 응답
```

`AuthenticationManager`를 쓰는 이유는 서비스가 직접 비밀번호를 비교하지 않고 Spring
Security의 표준 인증 절차를 이용하기 위해서입니다.

### 6. 로그인 이후 요청 흐름

```text
React 요청
  → Authorization 헤더
  → JwtAuthenticationFilter
  → JwtTokenProvider.validateToken()
  → 토큰의 username으로 DB 회원 조회
  → SecurityContextHolder에 Authentication 저장
  → URL 권한 검사
  → Controller
```

`JwtAuthenticationFilter`가 성공적으로 인증 객체를 저장하면 Controller에서는 다음과
같이 현재 회원을 받을 수 있습니다.

```java
@AuthenticationPrincipal UserEntity currentUser
```

### 7. 왜 STATELESS인가

```java
sessionCreationPolicy(SessionCreationPolicy.STATELESS)
```

서버 세션에 로그인 정보를 저장하지 않고 매 요청의 JWT로 사용자를 확인하겠다는 뜻입니다.
서버 여러 대로 확장하기 쉽지만, 발급된 Access Token을 서버가 즉시 회수하기 어렵다는
특징도 있습니다.

### 8. SecurityConfig 읽는 법

규칙은 위에서 아래 순서로 적용됩니다.

```java
.requestMatchers("/auth/**").permitAll()
.requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
.anyRequest().authenticated()
```

- 회원가입·로그인: 누구나 가능
- 게시글 GET 조회: 누구나 가능
- 나머지 요청: JWT 인증 필요

`JwtAuthenticationFilter`는 기본 아이디/비밀번호 필터보다 먼저 실행됩니다.

## React에서 JWT 사용하기

`step15-security-basic-front/src/App.js`의 핵심은 세 가지입니다.

### 토큰 저장

```javascript
localStorage.setItem("accessToken", token);
```

새로고침 후에도 유지되어 학습용으로 편하지만, XSS 공격이 발생하면 JavaScript가 토큰을
읽을 수 있습니다. 운영 환경에서는 보안 요구에 따라 Secure + HttpOnly 쿠키 방식도
검토해야 합니다.

### 보호된 API 호출

```javascript
axios.get(url, {
  headers: {
    Authorization: `Bearer ${accessToken}`
  }
});
```

`Bearer` 뒤에 공백 한 칸이 반드시 필요합니다. 백엔드 필터는 `"Bearer "`로 시작하는지
확인한 뒤 앞의 7글자를 제거합니다.

### CORS

React는 `localhost:3000`, Spring Boot는 `localhost:8888`에서 실행됩니다. 포트가 다르면
브라우저는 서로 다른 Origin으로 판단하므로 백엔드에서 CORS 허용 설정이 필요합니다.

## Step16: Google OAuth2

### 1. OAuth2 전체 흐름

```text
1. React에서 /oauth2/authorization/google 이동
2. Spring Security가 Google 로그인 화면으로 리다이렉트
3. 사용자가 Google 로그인과 정보 제공 승인
4. Google이 Authorization Code를 백엔드에 전달
5. Spring이 Code를 Google Access Token으로 교환
6. CustomOAuth2UserService가 Google 사용자 정보 조회
7. 최초 사용자면 우리 DB에 자동 회원가입
8. OAuth2SuccessHandler가 우리 서비스용 JWT 발급
9. React /oauth2/callback으로 리다이렉트
10. React가 JWT를 저장하고 이후 API 호출에 사용
```

Google Access Token과 프로젝트의 JWT는 역할이 다릅니다.

- Google Access Token: Google API에서 사용자 정보를 가져올 때 사용
- 자체 JWT: 우리 Spring Boot API의 인증에 사용

### 2. CustomOAuth2UserService

Google에서 주로 받는 값은 다음과 같습니다.

| 속성 | 의미 |
|---|---|
| `sub` | Google 계정의 안정적인 고유 ID |
| `email` | 이메일 |
| `name` | 표시 이름 |
| `picture` | 프로필 이미지 URL |

현재 코드는 이메일로 기존 회원을 찾고, 없으면 자동 가입합니다. 실무에서는 같은 이메일의
일반 계정과 소셜 계정을 어떻게 합칠지 정책을 먼저 정해야 합니다.

### 3. OAuth2SuccessHandler

OAuth2 인증에 성공했다고 해서 React가 우리 API용 JWT를 가진 것은 아닙니다.
`OAuth2SuccessHandler`가 DB 회원을 찾고 자체 Access/Refresh Token을 발급합니다.

현재 학습 코드는 토큰을 URL 쿼리스트링으로 전달합니다.

```text
http://localhost:3000/oauth2/callback?accessToken=...&refreshToken=...
```

이 방식은 브라우저 기록이나 로그에 토큰이 남을 수 있어 운영용으로는 권장되지 않습니다.

### 4. Google 설정

`step16-security-oauth2-back/.env` 파일을 만들고 다음 값을 넣어야 합니다.

```properties
GOOGLE_CLIENT_ID=Google에서_발급받은_Client_ID
GOOGLE_CLIENT_SECRET=Google에서_발급받은_Client_Secret
```

Google Cloud Console의 승인된 리다이렉트 URI에는 보통 다음 주소를 등록합니다.

```text
http://localhost:8888/login/oauth2/code/google
```

Client Secret은 Git에 커밋하면 안 됩니다. `.env.example`에는 변수 이름과 가짜 예시만
두고, 실제 값은 `.env`에 작성합니다.

## Step17: 인증이 붙은 게시판 API

### 1. 기술 조합

Step17은 한 프로젝트에서 두 DB 접근 방식을 같이 사용합니다.

- JPA: 회원과 Refresh Token
- MyBatis: 게시글·댓글 조회

JPA가 있다고 반드시 모든 테이블을 JPA로 처리해야 하는 것은 아닙니다. 복잡한 조회 SQL을
직접 제어하고 싶을 때 MyBatis를 섞어 사용할 수 있습니다.

### 2. 게시글 목록 흐름

```text
GET /api/posts?page=1&size=20&keyword=
  → BoardController
  → BoardService
  → BoardMapper 인터페이스
  → board-mapper.xml
  → board_view 조회
  → JSON 응답
```

응답에는 목록과 페이징 정보가 함께 들어갑니다.

```json
{
  "list": [],
  "pagging": {
    "currentPage": 1,
    "totalPage": 10
  }
}
```

### 3. Mapper 인터페이스와 XML 연결

```java
List<BoardDTO> selectBoardList(
    @Param("page") int page,
    @Param("size") int size);
```

```xml
<select id="selectBoardList" resultType="board">
```

- XML의 `namespace`는 Mapper 인터페이스 전체 이름과 같아야 합니다.
- XML의 `id`는 Java 메서드 이름과 같아야 합니다.
- `@Param("size")` 이름은 XML의 `#{size}`와 연결됩니다.
- `resultType="board"`는 `mybatis.type-aliases-package`에서 찾은 `BoardDTO` 별칭입니다.
- `map-underscore-to-camel-case=true`는 `write_date`를 `writeDate`에 연결합니다.

### 4. `#{}`와 `${}` 차이

- `#{size}`: PreparedStatement 파라미터로 안전하게 바인딩
- `${size}`: 문자열을 SQL에 그대로 삽입

현재 검색 쿼리에 `${size}`가 한 군데 있습니다. 숫자 매개변수라 바로 문제가 드러나지 않을
수 있지만, 원칙적으로 외부 입력에는 `#{}`를 사용해야 SQL Injection 위험을 줄일 수 있습니다.

### 5. View를 사용한 이유

`board_view`는 게시글, 작성자 닉네임, 좋아요·싫어요 개수를 하나의 조회 대상으로 합칩니다.

```text
board
  + board_member
  + 게시글 LIKE 집계
  + 게시글 DISLIKE 집계
  = board_view
```

Java 코드에서 복잡한 조인과 집계 SQL을 반복하지 않는 장점이 있습니다. 반면 View 정의가
바뀌면 이를 사용하는 모든 쿼리에 영향을 주므로 DB 스키마도 코드처럼 버전 관리해야 합니다.

### 6. 현재 구현된 범위

| 기능 | 상태 |
|---|---|
| 게시글 목록 | 구현 |
| 제목·내용 검색 | 구현 |
| 게시글 상세 | 구현 |
| 댓글 목록 | 구현 |
| JWT 인증 회원 확인 | 구현 |
| 게시글 등록 | 요청 데이터와 회원을 응답하는 단계까지만 구현 |
| 수정·삭제·댓글 등록 | 아직 구현되지 않음 |

`POST /api/posts`는 현재 DB에 저장하지 않습니다. `BoardController.addBoard()`가 전달받은
게시글과 인증 회원을 그대로 응답하는 실습 중간 단계입니다.

### 7. 페이징 코드에서 확인할 점

Controller는 요청의 기본 `size`를 20으로 받지만 `PaggingVO`는 한 페이지 크기를 30으로
고정합니다. 목록 SQL과 페이지 계산의 크기가 달라질 수 있으므로 이후에는 `size`를
`PaggingVO` 생성자에도 전달하는 구조가 자연스럽습니다.

검색 결과에서도 `boardCount()`가 전체 게시글 수를 반환하므로 검색 페이징의 전체 페이지가
부정확할 수 있습니다. 이후 `searchBoardCount(keyword)` 같은 쿼리를 추가할 수 있습니다.

## 실행 순서

### Step15

1. MySQL에 `auth_db`를 만듭니다.

   ```sql
   CREATE DATABASE auth_db
     CHARACTER SET utf8mb4
     COLLATE utf8mb4_unicode_ci;
   ```

2. `application.properties`의 DB 계정을 확인합니다.
3. 백엔드를 실행합니다.
4. 프론트 폴더에서 `npm install`, `npm start`를 실행합니다.
5. 회원가입 → 로그인 → 내 정보 확인 순서로 테스트합니다.

### Step16

1. Google Cloud Console에서 OAuth Client를 발급합니다.
2. 백엔드 폴더에 `.env`를 만들고 Client ID/Secret을 넣습니다.
3. 승인된 리다이렉트 URI를 등록합니다.
4. 백엔드와 프론트를 실행합니다.
5. Google 로그인 → `/oauth2/callback` → JWT 저장 흐름을 확인합니다.

### Step17

1. MySQL에서 `src/main/resources/board.sql`을 실행합니다.
2. `new_board_db`와 View가 생성됐는지 확인합니다.
3. 백엔드를 실행합니다.
4. Swagger UI 또는 React에서 `/api/posts`를 호출합니다.

Swagger UI 주소:

```text
http://localhost:8888/swagger-ui/index.html
```

## 추천 학습 순서

1. `SecurityConfig`에서 공개 URL과 인증 필요 URL을 구분합니다.
2. `AuthService.login()`에서 토큰이 발급되는 지점을 찾습니다.
3. `JwtAuthenticationFilter`에서 토큰이 사용자 객체로 바뀌는 과정을 따라갑니다.
4. React에서 `Authorization` 헤더를 붙이는 코드를 확인합니다.
5. Step16의 OAuth2 흐름에서 Google 토큰과 자체 JWT를 구분합니다.
6. Step17에서 Controller → Service → Mapper → XML → DB View 순서로 추적합니다.

## 자주 막히는 오류

| 증상 | 확인할 곳 |
|---|---|
| `Unknown database 'auth_db'` | 위 SQL로 `auth_db`를 먼저 생성 |
| 백엔드 시작 시 DB 연결 실패 | DB 이름, 계정, 비밀번호, MySQL 실행 여부 |
| `401 Unauthorized` | Authorization 헤더, `Bearer ` 공백, 토큰 만료 |
| React에서 CORS 오류 | 백엔드 허용 Origin과 React 포트 |
| Google `redirect_uri_mismatch` | Google Console의 리다이렉트 URI |
| OAuth 환경변수를 못 찾음 | `.env` 위치와 `GOOGLE_CLIENT_SECRET` 철자 |
| Step17 Mapper 오류 | XML namespace/id, type alias, View 생성 여부 |
| 목록은 나오지만 페이지 수가 이상함 | 요청 `size`와 `PaggingVO`의 30 고정값 |
