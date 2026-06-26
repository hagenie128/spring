# 문제 4 — 댓글 작성 API 연동

상세 페이지에서 댓글을 입력하고 `POST /api/comments`로 등록하세요.

## 학습 목표

- `commentApi` 레이어 추가
- 로그인 사용자만 댓글 작성 가능
- 작성 후 댓글 목록 새로고침

## TODO

| # | 파일 | 할 일 |
|---|---|---|
| 1 | `src/api/commentApi.js` | `create({ bno, content })` → `POST /api/comments` |
| 2 | `src/pages/PostDetailPage.jsx` | 댓글 내용 `useState` 관리 |
| 3 | `src/pages/PostDetailPage.jsx` | 작성 버튼 클릭 시 API 호출 |
| 4 | `src/pages/PostDetailPage.jsx` | 성공 후 `getPost(bno)`로 목록 갱신 |
| 5 | `src/pages/PostDetailPage.jsx` | 비로그인 시 "로그인이 필요합니다" 안내 |

## API

```
POST /api/comments
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "bno": 1,
  "content": "댓글 내용"
}
```

성공: `201 Created`

## Postman vs 프론트 비교

| | 댓글 | 게시글 |
|---|---|---|
| URL | `POST /api/comments` | `POST /api/posts` |
| Body | `bno`, `content` | `title`, `content` |
| 토큰 | 필요 | 필요 |

## 체크 포인트

- [ ] Postman으로 댓글 작성이 된다
- [ ] 로그인 후 프론트에서 댓글 작성이 된다
- [ ] 작성 직후 댓글 목록에 새 댓글이 보인다
- [ ] 비로그인 시 403 또는 안내 메시지가 표시된다
- [ ] textarea가 작성 후 비워진다

## 흔한 실수

- 버튼만 있고 `onClick`/`onSubmit` 없음 → 클릭해도 아무 일 없음
- `bno`를 Body에 안 넣음 → 서버 오류
- 토큰 없이 요청 → 403

## 참고 완성본

백엔드: `step17-board-backend` `BoardCommentController.java`
