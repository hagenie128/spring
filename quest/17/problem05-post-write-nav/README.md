# 문제 5 — 게시글 작성 + NavBar 인증 상태

글쓰기 페이지를 만들고, NavBar에 로그인 상태를 반영하세요.

## 학습 목표

- `postApi.create`로 게시글 등록
- `PostWritePage` 폼 구현
- `NavBar`에서 `useAuth()`로 메뉴 전환
- 인증 필요 페이지 접근 제어

## TODO

| # | 파일 | 할 일 |
|---|---|---|
| 1 | `src/api/postApi.js` | `create({ title, content })` → `POST /api/posts` |
| 2 | `src/pages/PostWritePage.jsx` | 제목/내용 입력 폼 + 제출 |
| 3 | `src/pages/PostWritePage.jsx` | 성공 시 상세 또는 목록으로 이동 |
| 4 | `src/components/NavBar.jsx` | `useAuth()`의 `isAuthenticated`, `user` 사용 |
| 5 | `src/components/NavBar.jsx` | 로그인 시 닉네임 + 글쓰기 + 로그아웃 표시 |
| 6 | `src/pages/SignupPage.jsx` | `authApi.signup` 연동 (보너스) |

## API

```
POST /api/posts
Authorization: Bearer {accessToken}

{
  "title": "제목",
  "content": "내용"
}
```

⚠️ URL은 `/api/posts` — **끝에 글번호 붙이지 않음**

## NavBar 분기 예시

```javascript
const { isAuthenticated, user, logout } = useAuth();

isAuthenticated
  ? <> {user.nickname} / 글쓰기 / 로그아웃 </>
  : <> 로그인 / 회원가입 </>
```

## 체크 포인트

- [ ] Postman으로 `POST /api/posts` 성공 (Bearer Token)
- [ ] 로그인 후 NavBar에 닉네임과 글쓰기 메뉴가 보인다
- [ ] 글쓰기 페이지에서 등록 후 목록/상세에 새 글이 보인다
- [ ] 로그아웃 후 NavBar가 비로그인 메뉴로 바뀐다
- [ ] `POST /api/posts/1` 같은 잘못된 URL을 쓰지 않는다

## 보너스

- 게시글 수정 (`PATCH /api/posts/{bno}`)
- 게시글 삭제 (`DELETE /api/posts/{bno}`)
- 작성자만 수정/삭제 버튼 표시 (`post.mid === user.id`)

## 참고 완성본

`step17-board-front` + `step17-board-backend` `BoardController.java`
