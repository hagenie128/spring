# Step 1 — Axios로 목록 API 읽기 (40분)

> **목표:** React 화면에 게시글 제목이 보인다.  
> 아직 Router, 로그인 없음. **API 읽기만** 집중.

---

## 배울 개념

- `useState` — API 결과 저장
- `useEffect` — 컴포넌트 마운트 시 API 호출
- `axios.get` — HTTP GET 요청
- `response.data` — 서버 JSON

---

## API 확인 (Postman 먼저!)

```
GET http://localhost:8888/api/posts?page=1&size=20
```

응답 구조 (외울 것):

```json
{
  "list": [
    { "bno": 1, "title": "제목", "nickname": "작성자", ... }
  ],
  "pagging": { ... }
}
```

**목록 배열 키 = `list`** ← 시험에 `data`로 바뀔 수 있으니 Postman으로 확인!

---

## TODO

### 1. `src/api/axiosInstance.js` 만들기

```javascript
import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8888";

const axiosInstance = axios.create({
  baseURL: API_URL,
  headers: { "Content-Type": "application/json" },
});

export default axiosInstance;
```

### 2. `src/api/postApi.js` 만들기

```javascript
import axiosInstance from "./axiosInstance";

export const postApi = {
  // TODO: getPage 함수 — GET /api/posts, params: page, size, keyword
};
```

### 3. `src/App.js` 수정

[template/step01/App.js](../template/step01/App.js) 참고

```javascript
// TODO:
// 1. useState([]) 로 posts 상태
// 2. useEffect에서 postApi.getPage(1, '', 20) 호출
// 3. response.data.list 를 setPosts
// 4. posts.map으로 <li>{item.title}</li> 출력
```

---

## 완료 기준

- [ ] `npm start` 후 화면에 게시글 제목이 `<ul><li>` 로 보임
- [ ] F12 Network에 `api/posts?page=1` 요청 Status 200
- [ ] 콘솔 에러 없음

---

## 흔한 실수

| 실수 | 증상 |
|------|------|
| `.env` 없음 | 요청이 localhost:3000으로 감 |
| `response.data` 대신 `response.list` | undefined, 빈 화면 |
| `useEffect` 의존성 배열 누락 | 무한 요청 (이번엔 `[]` 사용) |

---

## 다음

[Step 2 — Router](../step02-router/)
