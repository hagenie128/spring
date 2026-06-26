# Step 4 — AuthContext + useAuth (50분)

> **목표:** 로그인 로직을 Context로 올리고, 새로고침해도 로그인 유지

---

## 왜 Context?

Step 3은 LoginPage만 로그인을 알고 있음.  
NavBar, 글쓰기 등 **여러 컴포넌트**가 로그인 상태를 써야 하면 Context가 필요.

```
AuthProvider (user, login, logout)
  ├── NavBar        → isAuthenticated ?
  ├── LoginPage     → login()
  └── PostListPage  → (공개)
```

---

## TODO

### 1. `src/api/authApi.js` 추가

```javascript
me: () => axiosInstance.get("/auth/me"),
logout: () => axiosInstance.post("/auth/logout"),
```

### 2. `src/api/axiosInstance.js` — JWT 인터셉터

```javascript
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

### 3. `src/context/AuthContext.jsx`

[template/step04/AuthContext.jsx](../template/step04/AuthContext.jsx) — TODO 채우기

핵심 함수 `login`:

```javascript
const login = async (username, password) => {
  const loginRes = await authApi.login({ username, password });
  localStorage.setItem("accessToken", loginRes.data.accessToken);
  localStorage.setItem("refreshToken", loginRes.data.refreshToken);
  const meRes = await authApi.me();
  setUser(meRes.data);
};
```

### 4. `src/index.js`

```jsx
import { AuthProvider } from "./context/AuthContext";

<BrowserRouter>
  <AuthProvider>
    <App />
  </AuthProvider>
</BrowserRouter>
```

### 5. `LoginPage` 수정

Step 3의 `authApi.login` 직접 호출 → `useAuth().login` 으로 변경

```javascript
const { login } = useAuth();
await login(username, password);
```

---

## 완료 기준

- [ ] 로그인 후 `user.nickname` 콘솔에 출력 가능
- [ ] **새로고침(F5)** 해도 로그인 유지 (`/auth/me` 자동 호출)
- [ ] AuthProvider 없이 useAuth 쓰면 에러 (의도된 동작)

---

## /auth/me 응답 예시

```json
{
  "id": 1,
  "username": "reactlab",
  "nickname": "리액트연습",
  "role": "ROLE_USER"
}
```

---

## 다음

[Step 5 — 상세 페이지](../step05-상세-useParams/)
