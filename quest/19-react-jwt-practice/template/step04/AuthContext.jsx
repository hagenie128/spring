import { createContext, useContext, useEffect, useState } from "react";
import { authApi } from "../api/authApi";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // TODO 1: 앱 시작 시 token 있으면 /auth/me 호출
  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      // authApi.me() → setUser → finally setLoading(false)
    } else {
      setLoading(false);
    }
  }, []);

  // TODO 2: login 함수
  const login = async (username, password) => {
    // 1) authApi.login
    // 2) localStorage 저장
    // 3) authApi.me → setUser
  };

  // TODO 3: logout 함수
  const logout = async () => {
    // authApi.logout (실패해도)
    // localStorage.clear()
    // setUser(null)
  };

  const isAuthenticated = !!user;

  return (
    <AuthContext.Provider value={{ user, loading, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth는 AuthProvider 안에서만 사용하세요");
  return ctx;
};
