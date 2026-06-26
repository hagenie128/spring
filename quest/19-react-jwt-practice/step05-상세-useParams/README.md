# Step 5 — 상세 페이지 + useParams (40분)

> **목표:** 목록에서 제목 클릭 → 상세 페이지에서 본문 + 댓글 표시

---

## API

```
GET /api/posts/{bno}

응답:
{
  "board": { "bno", "title", "content", "nickname", ... },
  "commentList": [ { "cno", "content", "nickname", "cdate" }, ... ]
}
```

---

## TODO

### 1. `postApi.js` 추가

```javascript
getPost: (bno) => axiosInstance.get(`/api/posts/${bno}`),
```

### 2. `App.js` 라우트

```jsx
<Route path="/posts/:bno" element={<PostDetailPage />} />
```

⚠️ `:bno` 이름과 `useParams()` 키가 **반드시 같아야** 함

### 3. `PostListPage` — Link 추가

```jsx
import { Link } from "react-router-dom";

<Link to={`/posts/${item.bno}`}>{item.title}</Link>
```

### 4. `PostDetailPage.jsx` 생성

[template/step05/PostDetailPage.jsx](../template/step05/PostDetailPage.jsx)

```javascript
const { bno } = useParams();

useEffect(() => {
  postApi.getPost(bno)
    .then(res => {
      setPost(res.data.board);
      setComments(res.data.commentList);
    });
}, [bno]);
```

---

## 완료 기준

- [ ] 목록 제목 클릭 → `/posts/1` 상세 이동
- [ ] 제목, 본문, 작성자 표시
- [ ] 댓글 목록 표시 (`commentList.map`)
- [ ] Network: `GET /api/posts/1` Status 200

---

## 흔한 실수

| 실수 | 결과 |
|------|------|
| 라우트 `:id`, useParams `bno` | `/api/posts/undefined` → 403 |
| `post` 초기값 `null` vs `{}` | 로딩 처리 주의 |
| 댓글 날짜 `writeDate` | 실제 키는 `cdate` |

---

## 다음

[Step 6 — 미니 과제](../step06-미니과제/)
