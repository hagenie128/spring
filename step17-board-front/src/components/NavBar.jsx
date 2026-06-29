import { Link, useNavigate } from "react-router-dom"
import { useAuth } from "../context/AuthContext";

export default () => {
  const {isAuthenticated, user, logout} = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate('/');
  }

  return <nav className="navbar">
    <Link to="/" className="navbar-brand">📄 Spring Board</Link>
    <div className="navbar-menu">
      {
        isAuthenticated ?
          <>
            <span className="navbar-user">{user.nickname}</span>
            <Link to="/posts/create" className="nav-link">글쓰기</Link>
            <button type="button" onClick={handleLogout} className="nav-btn-secondary">로그아웃</button>
          </>
          :
          <>
            <Link to="/login" className="nav-link">로그인</Link>
            <Link to="/signup" className="nav-btn">회원가입</Link>
          </>
      }
    </div>

  </nav>
}