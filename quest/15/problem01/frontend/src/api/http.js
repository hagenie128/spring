import axios from "axios";

// TODO frontend-1: baseURL, timeout 등 Axios 기본 설정을 작성하세요.
const http = axios.create({
  baseURL: "http://localhost:8080/api",
});

export default http;
