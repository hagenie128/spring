import axios from "axios";

// TODO frontend-1: axios.create()로 baseURL이 "http://localhost:8080/api"인 HTTP 인스턴스를 만드세요.
const http = axios.create({
  baseURL: "http://localhost:8080/api",
});

export default http;
