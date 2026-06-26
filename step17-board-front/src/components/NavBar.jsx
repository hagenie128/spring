import { Link, useNavigate } from "react-router-dom"

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