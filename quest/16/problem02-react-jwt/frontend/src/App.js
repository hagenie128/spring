import './App.css';
import { useEffect, useState } from 'react';
import axios from 'axios';

const BASE_URL = 'http://localhost:8888';

const tokenStore = {
  getAccessToken: () => localStorage.getItem('accessToken'),
  getRefreshToken: () => localStorage.getItem('refreshToken'),

  save(accessToken, refreshToken) {
    // TODO 3: 두 토큰을 localStorage에 저장하세요.
  },

  clear() {
    // TODO 6: 두 토큰을 삭제하세요.
  }
};

function App() {
  const [accessToken, setAccessToken] = useState('');
  const [message, setMessage] = useState('');

  useEffect(() => {
    // TODO 5: 새로고침 후 저장된 Access Token을 상태로 복원하세요.
  }, []);

  const signup = async (event) => {
    event.preventDefault();
    // TODO 2: FormData에서 username/password/email을 읽어 회원가입 API를 호출하세요.
  };

  const login = async (event) => {
    event.preventDefault();
    // TODO 2~3: 로그인 API 호출 후 토큰 저장과 상태 갱신을 구현하세요.
  };

  const loadMe = async () => {
    // TODO 4: Authorization 헤더를 넣어 /auth/me를 호출하세요.
  };

  const logout = async () => {
    // TODO 4·6: 인증 헤더로 로그아웃 요청 후 토큰을 삭제하세요.
  };

  return (
    <main className="container">
      <h1>JWT 퀘스트</h1>

      <form onSubmit={signup} className="card">
        <h2>회원가입</h2>
        <input name="username" placeholder="아이디" />
        <input name="password" type="password" placeholder="비밀번호" />
        <input name="email" type="email" placeholder="이메일" />
        <button>회원가입</button>
      </form>

      <form onSubmit={login} className="card">
        <h2>로그인</h2>
        <input name="username" placeholder="아이디" />
        <input name="password" type="password" placeholder="비밀번호" />
        <button>로그인</button>
      </form>

      <section className="card">
        <button onClick={loadMe}>내 정보</button>
        <button onClick={logout}>로그아웃</button>
        <p>로그인 상태: {accessToken ? '인증됨' : '비로그인'}</p>
        <pre>{message}</pre>
      </section>
    </main>
  );
}

export default App;
