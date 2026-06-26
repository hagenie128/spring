# 문제 3 — 게시글 상세 UI + CSS

상세 페이지에 게시글 본문·메타정보·댓글 목록 UI를 꾸미세요.

## 학습 목표

- API 응답 필드명과 화면 바인딩 맞추기
- CSS 클래스로 상세/댓글 영역 스타일링
- 로딩·에러 상태 처리

## TODO

| # | 파일 | 할 일 |
|---|---|---|
| 1 | `src/pages/PostDetailPage.jsx` | `post.title`, `post.nickname`, `post.bcount` 등 표시 |
| 2 | `src/pages/PostDetailPage.jsx` | `commentList.map`으로 댓글 렌더링 (`key={cno}`) |
| 3 | `src/pages/PostDetailPage.jsx` | 댓글 날짜는 `item.cdate` 사용 (백엔드 DTO 필드명) |
| 4 | `src/App.css` | `.post-detail`, `.post-meta`, `.post-content` 스타일 |
| 5 | `src/App.css` | `.comment-area`, `.comment-item`, `.comment-form` 스타일 |
| 6 | `src/App.css` | 댓글 textarea 고정 높이 (`resize: none`) |

## BoardDTO / BoardCommentDTO 필드

**게시글 (board)**
- `bno`, `title`, `content`, `nickname`, `bcount`, `blike`, `bhate`, `writeUpdateDate`

**댓글 (commentList[])**
- `cno`, `bno`, `content`, `nickname`, `cdate`, `clike`, `chate`

## 체크 포인트

- [ ] 상세 페이지 레이아웃이 목록과 같은 디자인 톤이다
- [ ] 본문 줄바꿈이 유지된다 (`white-space: pre-wrap`)
- [ ] 댓글 작성 textarea 크기가 고정이다
- [ ] 데이터 로딩 전 빈 화면/로딩 문구가 표시된다
- [ ] API 실패 시 콘솔 또는 화면에 에러가 표시된다

## 보너스

- 좋아요/싫어요/수정/삭제/목록 버튼 배치 (기능 연동은 문제 4~5)
- 모바일에서 버튼이 세로로 정렬되는 반응형 CSS

## 참고 완성본

`step17-board-front`의 `PostDetailPage.jsx`, `App.css`
