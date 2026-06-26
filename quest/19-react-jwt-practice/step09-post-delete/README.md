# Step 9 — 게시글 삭제 Delete (30분)

> **D in CRUD** — `DELETE /api/posts/{bno}`

---

## API

```
DELETE http://localhost:8888/api/posts/1
Authorization: Bearer {accessToken}
```

성공: `204 No Content`

⚠️ 작성자 본인만 삭제 가능

---

## TODO

### 1. `postApi.js`

```javascript
remove: (bno) => axiosInstance.delete(`/api/posts/${bno}`),
```

`delete`는 JS 예약어라 `remove` 사용

### 2. `PostDetailPage` — 삭제 버튼

```javascript
const handleDelete = async () => {
  if (!window.confirm("정말 삭제하시겠습니까?")) return;
  try {
    await postApi.remove(bno);
    navigate("/");
  } catch (error) {
    alert("삭제 실패 (권한 없음 또는 오류)");
  }
};
```

```jsx
<button type="button" onClick={handleDelete}>삭제</button>
```

### 3. (보너스) 목록으로 버튼

```jsx
<Link to="/">목록으로</Link>
```

---

## 완료 기준

- [ ] Postman DELETE 성공
- [ ] 상세에서 삭제 → 목록으로 이동 → 글 사라짐
- [ ] confirm 취소 시 삭제 안 됨

---

## 다음

[Step 10 — CRUD 종합](../step10-crud-종합/)
