# 문제 2 — AuthContext + 로그인

Context API로 로그인 상태를 전역 관리하고, 로그인 페이지를 완성하세요.

## 학습 목표

- `AuthProvider`로 앱 감싸기
- `useAuth` 커스텀 훅 만들기
- 로그인 → 토큰 저장 → `/auth/me`로 사용자 정보 복원
- Axios 인터셉터로 JWT 자동 첨부

## TODO

| # | 파일 | 할 일 |
|---|---|---|
| 1 | `src/api/authApi.js` | signup, login, logout, me 함수 |
| 2 | `src/api/axiosInstance.js` | 요청 인터셉터에 `Bearer` 토큰 첨부 |
| 3 | `src/context/AuthContext.jsx` | user, login, logout, isAuthenticated 구현 |
| 4 | `src/context/AuthContext.jsx` | `useAuth()` — Provider 밖이면 에러 throw |
| 5 | `src/index.js` | `BrowserRouter` → `AuthProvider` → `App` 순서 |
| 6 | `src/pages/LoginPage.jsx` | 로그인 폼 + `login()` 호출 + 에러 메시지 |
| 7 | `src/pages/LoginPage.jsx` | 성공 시 `navigate('/')` |

## 로그인 흐름

```
1. POST /auth/login  { username, password }
2. localStorage에 accessToken, refreshToken 저장
3. GET /auth/me (Authorization: Bearer ...)
4. setUser(응답) → isAuthenticated = true
```

## 체크 포인트

- [ ] Postman으로 로그인 API가 200을 반환한다
- [ ] 로그인 성공 후 localStorage에 accessToken이 있다
- [ ] 새로고침해도 로그인 상태가 유지된다
- [ ] `useAuth`를 Provider 밖에서 쓰면 에러가 난다 (의도된 동작)
- [ ] 로그인 실패 시 화면에 에러 메시지가 표시된다

## 필수 수정 과제

`LoginPage`에서 `useRef`를 사용한다면:

```javascript
// ❌ ref 객체 자체를 넘김
await login(username, password);

// ✅ 입력값 문자열을 넘김
await login(username.current.value, password.current.value);
```

또는 `useState`로 controlled input을 사용해도 된다.

## 흔한 실수

| 실수 | 결과 |
|---|---|
| AuthProvider 미연결 | `useAuth는 AuthProvider에서만...` 에러 |
| ref `.value` 미사용 | 아이디/비번 맞아도 로그인 실패 |
| `navigate('/api/board/list')` | 백엔드 URL로 이동 시도 (404) |

## 참고 완성본

`step17-board-front`의 `AuthContext.jsx`, `LoginPage.jsx`, `index.js`
