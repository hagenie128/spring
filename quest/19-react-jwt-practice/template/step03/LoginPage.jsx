import { useState } from "react";
import { useNavigate } from "react-router-dom";
// Step 3: authApi 직접 호출
// Step 4: useAuth로 변경
// import { authApi } from "../api/authApi";
// import { useAuth } from "../context/AuthContext";

/**
 * Step 3 LoginPage — TODO 채우기
 */
export default function LoginPage() {
  const navigate = useNavigate();

  // 방법 A: useState (추천)
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    setLoading(true);
    setErrorMessage("");
    try {
      // TODO 1: authApi.login({ username, password }) 호출
      // const res = await ...

      // TODO 2: localStorage에 accessToken, refreshToken 저장
      // localStorage.setItem("accessToken", res.data.???);

      // TODO 3: navigate("/") — 목록으로 이동
    } catch (error) {
      setErrorMessage(error.response?.data?.message || "로그인 실패");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>로그인</h2>
      <input
        type="text"
        placeholder="아이디"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
      <button type="button" onClick={handleLogin} disabled={loading}>
        {loading ? "로그인 중..." : "로그인"}
      </button>
    </div>
  );
}
