import { Link, useNavigate } from "react-router-dom"

/**
 * [6/26 진도] 상단 네비게이션
 * TODO: useAuth()의 isAuthenticated, user, logout 연동
 * 현재 isAuthenticate = false 하드코딩 → 로그인해도 글쓰기 메뉴가 안 보임
 */
export default () => {
  const isAuthenticate = false;
  const navigate = useNavigate();
  return <nav>
    <Link to="/">📄 Spring Board</Link>
    <div>
      {
        isAuthenticate ?
          <>
            <span>사용자이름</span>
            <Link to="/posts/create">글쓰기</Link>
            <button type="button">로그아웃</button>
          </>
          :
          <>
            <Link to="/login">로그인</Link>
            <Link to="/signup">회원가입</Link>
          </>
      }
    </div>

  </nav>
}