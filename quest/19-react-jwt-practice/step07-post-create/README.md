# Step 7 — 게시글 등록 Create (40분)

> **C in CRUD** — `POST /api/posts`

---

## API (Postman 먼저!)

```
POST http://localhost:8888/api/posts
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "title": "제목",
  "content": "내용"
}
```

⚠️ URL은 `/api/posts` — **끝에 글번호 없음**

성공: `200 OK`

---

## TODO

### 1. `postApi.js` 추가

```javascript
create: (data) => axiosInstance.post("/api/posts", data),
```

### 2. `PostWritePage.jsx` 생성

[template/step07/PostWritePage.jsx](../template/step07/PostWritePage.jsx)

```javascript
const [title, setTitle] = useState("");
const [content, setContent] = useState("");

const handleSubmit = async (e) => {
  e.preventDefault();
  const res = await postApi.create({ title, content });
  // 수업 기본: navigate("/");
  // 오늘 수업 심화: navigate(`/posts/${res.data.board.bno}`);
};
```

### 3. `App.js` 라우트

```jsx
<Route path="/posts/create" element={<PostWritePage />} />
```

### 4. NavBar — 로그인 시 글쓰기 Link

```jsx
<Link to="/posts/create">글쓰기</Link>
```

---

## 완료 기준

- [ ] Postman으로 글 등록 성공
- [ ] 프론트 글쓰기 폼에서 등록 → 목록에 새 글 표시
- [ ] 비로그인 시 403 (토큰 없으면)

---

## 다음

[Step 8 — 수정 Update](../step08-post-update/)
