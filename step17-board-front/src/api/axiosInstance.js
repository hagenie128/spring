import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8888';

const axiosInstance = axios.create({
  baseURL : API_URL,
  headers : {
    'Content-Type' : 'application/json'
  }
});
// 요청 인터셉터
axiosInstance.interceptors.request.use(
  (config) => {
    //localStorage에서 accessToken을 꺼내서 헤더에 첨부
    const token = localStorage.getItem("accessToken");
    if(token)
      config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => Promise.reject(error)
);

//----
export default axiosInstance;