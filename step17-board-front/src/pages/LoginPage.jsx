import { useRef, useState } from "react"
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

/**
 * [6/26 진도] 로그인 페이지
 * - useAuth()로 AuthContext의 login 함수 사용
 * - useRef로 input DOM 참조 (값은 .current.value로 꺼내야 함)
 * - TODO: login(username.current.value, password.current.value) 로 수정 필요
 * - TODO: 성공 후 navigate('/') — 프론트 라우트로 이동 (백엔드 URL 아님)
 */
export default () => {
  const username = useRef(null);
  const password = useRef(null);
  const [loading, setLoading] = useState(false);
  const {login} = useAuth();
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState(null);

  const handleLogin = async () => {
    setLoading(true);
    try {
      // TODO: useRef 사용 시 .current.value로 문자열 전달
      await login(username,password);
      navigate('/api/board/list');
    } catch (error) {
      setErrorMessage(error.response?.data?.message || '로그인 실패');
    }finally{
      setLoading(false);
    }
  };

  return (  <>
  <div className="container">
    <h2>로그인 페이지</h2>
    <div className="login-form">
        <input type="text" placeholder="아이디 입력" ref={username} />
        <input type="password" placeholder="비밀번호 입력" ref={password} />
        <div className="error-message">{errorMessage}</div>
        {loading? <p>현재 로그인 중입니다</p> : <button type="button" onClick={handleLogin}>로그인</button>}
        <button type="button" onClick={() => {navigate('/api/auth/signup')}}>회원가입</button>
      </div>
    </div>
  </>);
}