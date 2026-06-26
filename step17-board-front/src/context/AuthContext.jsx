import { createContext, useContext, useEffect, useState } from "react";
import { authApi } from "../api/authApi";
/**
 * 전역 인증 상태 관리
 * 
 * Context API + useState로 구현
 */

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // 앱 시작시 : localStorage에 accessToken이 있으면 사용자 정보 요청해서 받음
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

  // 로그인
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

  // 로그아웃
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

// 커스텀 훅

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth는 AuthProvider에서만 사용 가능합니다');
  return ctx;
}