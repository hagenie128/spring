import axios from "axios";

/**
 * [6/26 진도] Axios 공통 인스턴스
 * - .env의 REACT_APP_API_URL(기본 8888)을 baseURL로 사용
 * - 모든 API 요청은 이 인스턴스를 통해 보낸다 (authApi, postApi 등)
 */
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8888';

const axiosInstance = axios.create({
  baseURL : API_URL,
  headers : {
    'Content-Type' : 'application/json'
  }
});

/**
 * [6/26 진도] 요청 인터셉터 — JWT 자동 첨부
 * 로그인 후 localStorage에 저장된 accessToken을 읽어
 * Authorization: Bearer {토큰} 헤더를 붙인다.
 * Postman에서 Bearer Token 설정하는 것과 같은 역할이다.
 */
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    if(token)
      config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => Promise.reject(error)
);

//----
export default axiosInstance;