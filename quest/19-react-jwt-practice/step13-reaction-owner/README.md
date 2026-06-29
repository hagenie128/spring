# Step 13 — 좋아요/싫어요 + 작성자만 수정/삭제

> 오늘 수업 `PostDetailPage` 보너스 연습

---

## API

```
POST /api/posts/reaction
Authorization: Bearer {token}

{
  "bno": 1,
  "type": "like"    // 또는 "dislike"
}
```

응답:

```json
{
  "count": {
    "likeCount": 3,
    "dislikeCount": 1
  }
}
```

---

## TODO

### 1. `postApi.js`

```javascript
postReaction: (data) => axiosInstance.post("/api/posts/reaction", data),
```

### 2. 상세 페이지 — 반응 버튼

```javascript
const handleReaction = async (type) => {
  const res = await postApi.postReaction({ bno: post.bno, type });
  setPost(prev => ({
    ...prev,
    blike: res.data.count.likeCount,
    bhate: res.data.count.dislikeCount,
  }));
};
```

### 3. 작성자만 수정/삭제

```javascript
const { user } = useAuth();
const isOwner = user && user.id === post.mid;

{isOwner && (
  <>
    <button onClick={() => navigate(`/posts/${bno}/edit`)}>수정</button>
    <button onClick={handleDelete}>삭제</button>
  </>
)}
```

### 4. 삭제

```javascript
if (window.confirm("삭제하시겠습니까?")) {
  await postApi.remove(bno);
  navigate("/");
}
```

---

## 완료 기준

- [ ] 로그인 후 좋아요/싫어요 숫자 갱신
- [ ] 본인 글만 수정/삭제 버튼 표시
- [ ] 삭제 후 목록으로 이동
