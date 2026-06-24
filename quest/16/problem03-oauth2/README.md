# 문제 3 — Google OAuth2 로그인

일반 JWT 로그인에 Google OAuth2 로그인을 추가하세요.

## TODO

| TODO | 파일 | 할 일 |
|---|---|---|
| 1 | `.env` | Google Client ID와 Secret 설정 |
| 2 | `SecurityConfig.java` | `oauth2Login()` 설정 |
| 3 | `CustomOAuth2UserService.java` | Google 사용자 속성 조회 |
| 4 | `CustomOAuth2UserService.java` | 최초 로그인 사용자를 DB에 자동 등록 |
| 5 | `OAuth2SuccessHandler.java` | DB 회원 조회 후 자체 JWT 발급 |
| 6 | `OAuth2SuccessHandler.java` | Refresh Token 교체 저장 |
| 7 | 프론트 `App.js` | Google 로그인 시작 URL로 이동 |
| 8 | 프론트 `App.js` | `/oauth2/callback`에서 토큰 저장 |

## 꼭 구분하기

- Google Access Token: Google 사용자 정보 조회용
- 우리 Access Token: 우리 백엔드 API 인증용

## Google Console 설정

승인된 리다이렉트 URI:

```text
http://localhost:8888/login/oauth2/code/google
```

`backend/.env.example`을 복사해 `.env`를 만들고 실제 값을 넣으세요.

## 완료 조건

- Google 로그인 버튼을 누르면 Google 인증 화면으로 이동
- 최초 로그인 회원이 DB에 자동 저장됨
- 성공 후 React `/oauth2/callback`으로 돌아옴
- 콜백 이후 `/auth/me` 호출 성공
- 실제 Client Secret이 Git 변경 목록에 나타나지 않음

