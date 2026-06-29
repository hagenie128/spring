import axiosInstance from "./axiosInstance";

/*
  인증 관련 API 함수
*/
export const authApi = {
  // 회원가입
  singup : (data) => axiosInstance.post('/auth/signup',data),
  // 로그인
  login : (data) => axiosInstance.post('/auth/login',data),
  // 로그아웃
  logout : () => axiosInstance.post('/auth/logout'),
  // 내정보
  me : () => axiosInstance.get('/auth/me')
}