# Step 2 — React Router (30분)

> **목표:** 목록 페이지와 로그인 페이지를 URL로 분리한다.

---

## 배울 개념

- `BrowserRouter` — SPA 라우팅
- `Routes` / `Route` — URL ↔ 컴포넌트 매핑
- `Link` — `<a>` 대신 페이지 새로고침 없이 이동
- `useNavigate` — 코드로 페이지 이동

---

## TODO

### 1. `src/index.js`

```jsx
import { BrowserRouter } from "react-router-dom";

root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
```

### 2. 파일 분리

```
src/pages/PostListPage.jsx   ← Step 1의 목록 코드 이동
src/pages/LoginPage.jsx      ← <h2>로그인 (껍데기만)
```

### 3. `src/App.js`

```jsx
import { Routes, Route, Link } from "react-router-dom";
import PostListPage from "./pages/PostListPage";
import LoginPage from "./pages/LoginPage";

function App() {
  return (
    <>
      <nav>
        <Link to="/">게시판</Link>
        <Link to="/login">로그인</Link>
      </nav>
      <Routes>
        <Route path="/" element={<PostListPage />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </>
  );
}
```

---

## 완료 기준

- [ ] `/` → 게시글 목록
- [ ] `/login` → 로그인 페이지 (빈 폼이어도 OK)
- [ ] nav Link 클릭 시 새로고침 없이 전환

---

## 흔한 실수

| 실수 | 증상 |
|------|------|
| BrowserRouter 없음 | Link 클릭 시 전체 새로고침 |
| `Route path` 오타 | 빈 화면 |
| 백엔드 URL을 path로 사용 | `/auth/login` (X) → `/login` (O) |

---

## 다음

[Step 3 — 로그인](../step03-login/)
