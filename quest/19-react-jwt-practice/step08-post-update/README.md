# Step 8 — 게시글 수정 Update (40분)

> **U in CRUD** — `PATCH /api/posts/{bno}`

---

## API

```
PATCH http://localhost:8888/api/posts/1
Authorization: Bearer {accessToken}

{
  "title": "수정 제목",
  "content": "수정 내용"
}
```

성공: `204 No Content` (응답 body 없음)

⚠️ **작성자 본인만** 수정 가능 — 다른 사람 글이면 `403`

---

## TODO

### 1. `postApi.js`

```javascript
update: (bno, data) => axiosInstance.patch(`/api/posts/${bno}`, data),
```

### 2. 라우트 — 작성/수정 페이지 재사용

```jsx
<Route path="/posts/:bno/edit" element={<PostWritePage />} />
```

### 3. `PostWritePage.jsx` — 등록 vs 수정 분기

```javascript
const { bno } = useParams(); // edit 라우트일 때만 값 있음
const isEdit = Boolean(bno);

useEffect(() => {
  if (isEdit) {
    postApi.getPost(bno).then(res => {
      setTitle(res.data.board.title);
      setContent(res.data.board.content);
    });
  }
}, [bno, isEdit]);

const handleSubmit = async (e) => {
  e.preventDefault();
  if (isEdit) {
    await postApi.update(bno, { title, content });
    navigate(`/posts/${bno}`);
  } else {
    await postApi.create({ title, content });
    navigate("/");
  }
};
```

### 4. `PostDetailPage` — 수정 버튼

```jsx
<Link to={`/posts/${bno}/edit`}>수정</Link>
```

### (보너스) 작성자만 버튼 표시

```javascript
const { user } = useAuth();
// post.mid === user.id 일 때만 수정/삭제 버튼
```

---

## 완료 기준

- [ ] Postman PATCH 성공
- [ ] 상세 → 수정 → 저장 → 상세에 반영
- [ ] 본인 글이 아니면 403 처리 (에러 메시지)

---

## 다음

[Step 9 — 삭제 Delete](../step09-post-delete/)
