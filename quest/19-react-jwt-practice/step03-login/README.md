# Step 3 — 로그인 + JWT 저장 (50분)

> **목표:** 로그인 버튼 클릭 → `localStorage`에 `accessToken` 저장

AuthContext는 **다음 Step**에서. 이번엔 LoginPage에서 직접 API 호출해 흐름 이해.

---

## API

```
POST /auth/login
Body: { "username": "reactlab", "password": "1234" }

응답:
{ "accessToken": "eyJ...", "refreshToken": "eyJ...", "tokenType": "Bearer" }
```

---

## TODO

### 1. `src/api/authApi.js`

```javascript
import axiosInstance from "./axiosInstance";

export const authApi = {
  login: (data) => axiosInstance.post("/auth/login", data),
};
```

### 2. `src/pages/LoginPage.jsx`

[template/step03/LoginPage.jsx](../template/step03/LoginPage.jsx) — TODO 채우기

**방법 A — useState (추천, 초보자)**

```javascript
const [username, setUsername] = useState("");
const [password, setPassword] = useState("");

<input value={username} onChange={e => setUsername(e.target.value)} />
```

**방법 B — useRef**

```javascript
await authApi.login({
  username: usernameRef.current.value,
  password: passwordRef.current.value,
});
```

### 3. 로그인 성공 처리

```javascript
const res = await authApi.login({ username, password });
localStorage.setItem("accessToken", res.data.accessToken);
localStorage.setItem("refreshToken", res.data.refreshToken);
navigate("/");  // 목록으로
```

### 4. 에러 처리

```javascript
catch (error) {
  setErrorMessage(error.response?.data?.message || "로그인 실패");
}
```

---

## 완료 기준

- [ ] 로그인 성공 후 F12 → Application → localStorage에 `accessToken`
- [ ] 잘못된 비번 → 화면에 에러 메시지
- [ ] 성공 후 `/` 목록으로 이동

---

## 연습 문제

1. 로그인 중 버튼 비활성화 (`loading` state)
2. 로그인 성공 시 `alert("환영합니다")` 추가

---

## 다음

[Step 4 — AuthContext](../step04-auth-context/)
