# Quest 17 — 6월 26일 React 게시판 프론트 연동

> **📖 통합 가이드:** [../PRACTICE-GUIDE-12-17.html](../PRACTICE-GUIDE-12-17.html) — 문제·힌트·전체코드 (클릭해서 열기)

> Axios · React Router · Context API · JWT · 게시글 목록/상세 UI

수업에서 `step17-board-front`와 `step17-board-backend`를 연결하며 진행한 내용을 복습하는 퀘스트입니다.
백엔드는 quest/16 problem04~05 또는 `step17-board-backend`를 그대로 사용합니다.

| 문제 | 폴더 | 핵심 주제 | 참고 완성본 |
|---|---|---|---|
| 1 | [problem01-board-list-detail](problem01-board-list-detail/) | Axios + 목록/상세 API + Link | `step17-board-front` PostListPage |
| 2 | [problem02-auth-context](problem02-auth-context/) | AuthProvider + useAuth + 로그인 | `step17-board-front` AuthContext |
| 3 | [problem03-post-detail-ui](problem03-post-detail-ui/) | 상세 페이지 UI + CSS | `step17-board-front` PostDetailPage |
| 4 | [problem04-comment-write](problem04-comment-write/) | 댓글 작성 API 연동 | `step17-board-backend` BoardCommentController |
| 5 | [problem05-post-write-nav](problem05-post-write-nav/) | 글쓰기 + NavBar 인증 상태 | `step17-board-front` 전체 |

## 공통 준비

- 백엔드: `cd step17-board-backend` → `gradlew.bat bootRun` (포트 **8888**)
- 프론트: `cd step17-board-front` → `npm install` → `npm start` (포트 **3000**)
- API 테스트: [requests.http](requests.http) 또는 Postman

## 추천 진행 방식

1. Postman으로 API가 정상인지 먼저 확인한다.
2. 프론트는 **API 레이어 → 페이지 → UI** 순서로 구현한다.
3. `useRef`를 쓰면 `.current.value`, `useState`를 쓰면 state 변수를 그대로 넘긴다.
4. `useAuth()`는 반드시 `AuthProvider` 안쪽 컴포넌트에서만 호출한다.
5. React Router 경로(`/`, `/login`)와 백엔드 API 경로(`/auth/login`)를 혼동하지 않는다.

## 오늘 수업에서 자주 나온 오류

| 증상 | 원인 | 해결 |
|---|---|---|
| `useAuth는 AuthProvider에서만...` | Provider 미연결 | `index.js`에서 App을 AuthProvider로 감싸기 |
| 로그인 실패 (정보 맞는데) | ref 객체를 그대로 전달 | `username.current.value` 사용 |
| 상세 403 | URL 파라미터 이름 불일치 | 라우트 `:bno` ↔ `useParams().bno` |
| Postman 글작성 403 | URL에 글번호 붙임 | `POST /api/posts` (끝에 숫자 없음) |
| 댓글은 되는데 글작성 안 됨 | Body 필드 다름 | 글: `title, content` / 댓글: `bno, content` |
