# Step 10 — CRUD 종합 + 댓글 Create (60분)

> 게시글 **CRUD 전체** + 댓글 **등록**까지 한 번에 점검

---

## 게시글 CRUD 체크

| | Method | URL | 인증 | postApi |
|---|--------|-----|------|---------|
| **R** 목록 | GET | `/api/posts` | X | `getPage` |
| **R** 상세 | GET | `/api/posts/{bno}` | X | `getPost` |
| **C** 등록 | POST | `/api/posts` | O | `create` |
| **U** 수정 | PATCH | `/api/posts/{bno}` | O | `update` |
| **D** 삭제 | DELETE | `/api/posts/{bno}` | O | `remove` |

---

## 댓글 등록 (보너스 C)

### API

```
POST /api/comments
Authorization: Bearer {token}

{ "bno": 1, "content": "댓글" }
```

### TODO

1. `commentApi.js` — `create({ bno, content })`
2. `PostDetailPage` — textarea + 작성 버튼
3. 성공 후 `getPost(bno)` 다시 호출해 댓글 목록 갱신

[template/step10/commentApi.js](../template/step10/commentApi.js)

---

## 종합 자가 시험 (90분 타이머)

새 CRA 프로젝트에서 아래를 **참고 없이** 구현:

1. [ ] 목록 조회 (공개)
2. [ ] 상세 조회 (공개)
3. [ ] 로그인 + AuthContext
4. [ ] 글 등록
5. [ ] 글 수정 (본인만)
6. [ ] 글 삭제 (본인만)
7. [ ] (보너스) 댓글 등록

**7개 중 5개** = 시험 대비 OK  
**7개 전부** = CRUD 완전 숙달

---

## 작성자 권한 패턴 (시험에 자주 나옴)

```javascript
const { user, isAuthenticated } = useAuth();
const isOwner = isAuthenticated && post.mid === user.id;

{isOwner && (
  <>
    <Link to={`/posts/${bno}/edit`}>수정</Link>
    <button onClick={handleDelete}>삭제</button>
  </>
)}
```

`/auth/me` 응답의 `id`와 게시글 `mid` 비교

---

## 막히면 볼 파일 (step17-board-front)

| 기능 | 참고 |
|------|------|
| postApi | `src/api/postApi.js` |
| AuthContext | `src/context/AuthContext.jsx` |
| 상세 | `src/pages/PostDetailPage.jsx` |

글쓰기/수정/삭제는 아직 미완일 수 있음 — 이번 Step에서 직접 완성하는 것이 연습 목표
