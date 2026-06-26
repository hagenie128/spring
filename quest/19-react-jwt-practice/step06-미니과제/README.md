# Step 6 — 미니 과제: NavBar + 종합 (60분)

> **목표:** `step17-board-front`와 비슷한 최소 기능을 **혼자** 완성

---

## 과제 명세

### NavBar

| 상태 | 표시 |
|------|------|
| 비로그인 | [로그인] [회원가입] 링크 |
| 로그인 | `{nickname}님` [글쓰기] [로그아웃] |

### 글쓰기

Step 7~9에서 **CRUD 전체** 연습 → [step07-post-create](../step07-post-create/)

---

## 과제 명세 (NavBar만)

### 1. `src/components/NavBar.jsx`

```javascript
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function NavBar() {
  const { isAuthenticated, user, logout } = useAuth();

  return (
    <nav>
      <Link to="/">📄 게시판</Link>
      <div>
        {isAuthenticated ? (
          <>
            <span>{user.nickname}</span>
            <Link to="/posts/create">글쓰기</Link>
            <button onClick={logout}>로그아웃</button>
          </>
        ) : (
          <>
            <Link to="/login">로그인</Link>
          </>
        )}
      </div>
    </nav>
  );
}
```

### 2. `App.js`에 NavBar 추가

글쓰기/수정/삭제는 **Step 7~9**에서 진행합니다.

---

## (삭제됨 — Step 7로 이동) ~~보너스 PostWritePage~~

## 최종 자가 채점 (NavBar)

| # | 항목 | O/X |
|---|------|-----|
| 1 | 비로그인 목록 조회 | |
| 2 | 로그인 → token 저장 | |
| 3 | 새로고침 로그인 유지 | |
| 4 | NavBar 상태 전환 | |
| 5 | 목록 → 상세 Link | |
| 6 | 로그아웃 → token 삭제 | |

**CRUD 전체 채점은 [Step 10](../step10-crud-종합/)** 참고

---

## 시험 시뮬레이션 (90분 타이머)

1. 새 폴더에 CRA 생성 (10분)
2. Step 1~4만 다시 구현 (50분)
3. API URL/키 이름을 종이에 적고 시작 (습관)

참고: `step17-board-front` 전체와 비교하며 부족한 부분 메모
