import axiosInstance from "./axiosInstance";

/**
 * [6/26 진도] 인증 API 레이어
 * 백엔드 AuthController(/auth/**)와 1:1로 대응한다.
 * AuthContext에서 login/logout/me 호출 시 이 모듈을 사용한다.
 */
export const authApi = {
  /** POST /auth/signup — Body: { username, password, nickname } */
  signup : (data) => axiosInstance.post('/auth/signup', data),
  /** POST /auth/login — Body: { username, password } → accessToken, refreshToken 반환 */
  login : (data) => axiosInstance.post('/auth/login', data),
  /** POST /auth/logout — Bearer 토큰 필요 */
  logout : () => axiosInstance.post('/auth/logout'),
  /** GET /auth/me — Bearer 토큰으로 현재 로그인 사용자 정보 조회 */
  me : () => axiosInstance.get('/auth/me'),
};