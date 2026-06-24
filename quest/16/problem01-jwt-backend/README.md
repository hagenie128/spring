# 문제 1 — JWT 인증 백엔드

회원가입, 로그인, JWT 검증, 로그아웃 흐름을 직접 완성하세요.

## 실습 순서

| TODO | 파일 | 할 일 |
|---|---|---|
| 1 | `SecurityConfig.java` | 세션을 `STATELESS`로 설정하고 공개/보호 URL 구분 |
| 2 | `JwtTokenProvider.java` | Access Token과 Refresh Token 생성 |
| 3 | `JwtTokenProvider.java` | 서명·만료 검증과 username 추출 |
| 4 | `JwtAuthenticationFilter.java` | Bearer Token 추출 |
| 5 | `JwtAuthenticationFilter.java` | 인증 객체를 `SecurityContextHolder`에 저장 |
| 6 | `AuthService.java` | 아이디 중복 검사와 BCrypt 회원가입 |
| 7 | `AuthService.java` | `AuthenticationManager`를 이용한 로그인 |
| 8 | `AuthService.java` | Refresh Token을 사용자당 하나만 저장 |
| 9 | `AuthService.java` | 로그아웃 시 Refresh Token 삭제 |

## 완료 조건

- 비밀번호 원문이 DB에 저장되지 않음
- 로그인 응답에 Access/Refresh Token이 포함됨
- 토큰 없이 `/auth/me` 호출 시 401
- Access Token을 보내면 현재 회원 정보 조회 가능
- 로그아웃 후 DB의 Refresh Token이 삭제됨

## 실행

```sql
CREATE DATABASE auth_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

```powershell
cd backend
.\gradlew.bat bootRun
```

막히면 `step15-security-basic`을 참고하세요.

