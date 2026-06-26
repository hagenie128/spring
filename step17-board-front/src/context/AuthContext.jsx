import { createContext, useContext, useEffect, useState } from "react";
import { authApi } from "../api/authApi";
/**
 * [6/26 진도] 전역 인증 상태 관리 (Context API)
 *
 * 역할:
 * 1. 앱 시작 시 localStorage의 accessToken으로 자동 로그인(/auth/me)
 * 2. login() — 토큰 저장 + user state 갱신
 * 3. logout() — 토큰 삭제 + user null
 * 4. isAuthenticated — 로그인 여부 판단
 *
 * 사용법:
 * - index.js에서 <AuthProvider>로 App을 감싼다
 * - 하위 컴포넌트에서 const { login, user } = useAuth() 로 사용
 */
const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // [진도] 새로고침해도 로그인 유지 — 토큰이 있으면 /auth/me로 사용자 정보 복원
  useEffect(() => {
    const token = localStorage.getItem('accessToken');

    if (token) {
      authApi.me()
        .then(response => {
          setUser(response.data);
        })
        .catch(error => localStorage.clear())
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  // [진도] 로그인 흐름: login API → 토큰 localStorage 저장 → me API → user state
  const login = async (username, password) => {

    // 로그인 요청
    const loginRes = await authApi.login({ username, password });
    // 토큰 저장
    localStorage.setItem('accessToken', loginRes.data.accessToken);
    localStorage.setItem('refreshToken', loginRes.data.refreshToken);
    // 사용자 정보 저장
    const meRes = await authApi.me();
    setUser(meRes.data);
    return meRes.data;
  }

  // [진도] 로그아웃: 서버에 알림 후 localStorage 비우고 user null
  const logout = async () => {
    try {
      await authApi.logout();
    } catch (error) {
      //토큰 만료시
    }
    localStorage.clear();
    setUser(null);
  }

  const isAuthenticated = !!user;// user가 null이 아니면 true, null이면 false

  return (<AuthContext.Provider value={{
    user, loading, isAuthenticated, login, logout
  }}>{children}</AuthContext.Provider>);
}

// [진도] 커스텀 훅 — AuthProvider 밖에서 호출하면 에러를 던져 실수를 방지한다
export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth는 AuthProvider에서만 사용 가능합니다');
  return ctx;
}