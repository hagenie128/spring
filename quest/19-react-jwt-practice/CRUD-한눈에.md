# React 게시글 CRUD 한눈에

> `step17-board-backend` API 기준. 시험에 URL이 바뀌면 **Method와 역할**만 같으면 됨.

---

## postApi.js 전체

```javascript
import axiosInstance from "./axiosInstance";

export const postApi = {
  // R — Read
  getPage: (page, keyword, size) =>
    axiosInstance.get("/api/posts", { params: { page, keyword, size } }),
  getPost: (bno) => axiosInstance.get(`/api/posts/${bno}`),

  // C — Create
  create: (data) => axiosInstance.post("/api/posts", data),

  // U — Update
  update: (bno, data) => axiosInstance.patch(`/api/posts/${bno}`, data),

  // D — Delete
  remove: (bno) => axiosInstance.delete(`/api/posts/${bno}`),
};
```

---

## 페이지 ↔ API 매핑

| 페이지 | 라우트 | API | Hook |
|--------|--------|-----|------|
| 목록 | `/` | GET 목록 | useEffect |
| 상세 | `/posts/:bno` | GET 상세 | useParams + useEffect |
| 글쓰기 | `/posts/create` | POST | useState + onSubmit |
| 수정 | `/posts/:bno/edit` | PATCH | useParams + 기존값 로드 |
| 삭제 | (상세 버튼) | DELETE | onClick + navigate |

---

## 인증 필요 여부

| API | JWT |
|-----|-----|
| GET 목록/상세 | 불필요 |
| POST/PATCH/DELETE | **필수** |

---

## HTTP Status

| 코드 | 의미 |
|------|------|
| 200 | 조회/등록 성공 |
| 204 | 수정/삭제 성공 (body 없음) |
| 403 | 토큰 없음 / 작성자 아님 |
| 404 | 없는 글 |

---

## React 패턴 요약

```
읽기(R):  useEffect → api.get → setState → map/render
쓰기(C):  form onSubmit → api.post → navigate(상세 또는 목록)
수정(U):  기존 load → form → api.patch → navigate(URL의 bno)
삭제(D):  window.confirm → api.delete → navigate
```

---

## 오늘 수업 추가 (Quill + 글번호)

### 등록 후 이동

```javascript
const res = await postApi.create(form);
navigate(`/posts/${res.data.board.bno}`);  // board 안의 bno!
```

백엔드 `board-mapper.xml`:

```xml
<insert id="insertBoard" useGeneratedKeys="true" keyProperty="bno">
```

### 수정 후 이동

```javascript
await postApi.update(bno, form);
navigate(`/posts/${bno}`);  // PATCH는 204 → URL bno 사용
```

### Quill state

```javascript
// ❌ setForm({ ...form, content })  — 제목 사라짐
// ✅ setForm(prev => ({ ...prev, content: html }))
```

### QuillEditor

- 인스턴스 생성: `useEffect([])` **1회**
- 초기 본문 반영: **1회만**
- 상세 출력: `dangerouslySetInnerHTML`

자세히: [step12-quill-write](step12-quill-write/) · [step13-reaction-owner](step13-reaction-owner/)

---

## 댓글 (보너스)

```javascript
// commentApi.js
create: (data) => axiosInstance.post("/api/comments", data),
// Body: { bno, content }
```

---

## Postman 순서 (CRUD 연습)

1. signup → login → token 복사
2. GET 목록
3. POST 등록 (Bearer)
4. GET 상세
5. PATCH 수정 (Bearer, 본인 글)
6. DELETE 삭제 (Bearer, 본인 글)

[requests.http](requests.http) 참고
