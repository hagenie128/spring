import './App.css';
import { useEffect, useState } from 'react';
import axios from 'axios';

const BASE_URL = 'http://localhost:8888';

function App() {
  const [message, setMessage] = useState('');

  useEffect(() => {
    // TODO 8:
    // 1. 현재 경로가 /oauth2/callback인지 확인하세요.
    // 2. URLSearchParams로 accessToken과 refreshToken을 꺼내세요.
    // 3. 두 토큰을 localStorage에 저장하세요.
    // 4. 토큰이 남은 URL을 브라우저 주소창에서 제거해 보세요(보너스).
  }, []);

  const startGoogleLogin = () => {
    // TODO 7: 브라우저를 백엔드의 OAuth2 시작 URL로 이동시키세요.
    // GET http://localhost:8888/oauth2/authorization/google
  };

  const loadMe = async () => {
    // TODO 8: 저장된 Access Token으로 /auth/me를 호출하세요.
  };

  return (
    <main className="container">
      <h1>Google OAuth2 퀘스트</h1>
      <section className="card">
        <button onClick={startGoogleLogin}>Google로 로그인</button>
        <button onClick={loadMe}>JWT로 내 정보 확인</button>
        <pre>{message}</pre>
      </section>
    </main>
  );
}

export default App;
