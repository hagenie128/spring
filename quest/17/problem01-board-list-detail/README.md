# 문제 1 — 게시글 목록·상세 API 연동

React에서 백엔드 게시판 API를 호출하고, 목록에서 상세로 이동하세요.

## 학습 목표

- Axios 공통 인스턴스(`axiosInstance`) 만들기
- `postApi.getPage`, `postApi.getPost` 구현
- `useEffect`로 마운트 시 데이터 로드
- `Link`로 SPA 페이지 이동
- `useParams`로 URL 글번호(`bno`) 받기

## TODO

| # | 파일 | 할 일 |
|---|---|---|
| 1 | `src/api/axiosInstance.js` | baseURL `http://localhost:8888`, Content-Type 설정 |
| 2 | `src/api/postApi.js` | `getPage(page, keyword, size)`, `getPost(bno)` |
| 3 | `src/pages/PostListPage.jsx` | `useEffect`로 1페이지 조회, `list`/`pagging` state 저장 |
| 4 | `src/components/PaggingBar.jsx` | 페이지 버튼 클릭 시 `onPageChange` 호출 |
| 5 | `src/pages/PostListPage.jsx` | 제목에 `<Link to={/posts/${bno}}>` 적용 |
| 6 | `src/App.js` | `/posts/:bno` 라우트 등록 |
| 7 | `src/pages/PostDetailPage.jsx` | `useParams().bno`로 상세 API 호출 |

## API 정리

```
GET /api/posts?page=1&size=20&keyword=
→ { list: [...], pagging: {...} }

GET /api/posts/{bno}
→ { board: {...}, commentList: [...] }
```

## 체크 포인트

- [ ] 목록 첫 화면에 게시글이 표시된다
- [ ] 페이징 버튼 클릭 시 다른 페이지가 로드된다
- [ ] 제목 클릭 시 상세 페이지로 이동한다
- [ ] 상세에서 제목·본문·댓글 목록이 보인다
- [ ] 라우트 `:bno`와 `useParams().bno` 이름이 같다

## 흔한 실수

- `Link` import 누락 → `react/jsx-no-undef` ESLint 오류
- 라우트는 `:id`인데 `useParams().bno` 사용 → API가 `/api/posts/undefined` 호출 → 403

## 참고 완성본

`step17-board-front`의 `postApi.js`, `PostListPage.jsx`, `PostDetailPage.jsx`
