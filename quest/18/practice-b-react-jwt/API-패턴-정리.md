# API 패턴 정리 — 시험 전 30분 암기용

> API URL이 달라져도 **이 순서**는 같습니다.

---

## 1. 레이어 구조

```
Page (useEffect)
  → xxxApi.js
    → axiosInstance.js
      → Spring Boot API
```

---

## 2. 인증 흐름 (JWT)

```
1. POST /auth/login     Body: { username, password }
2. localStorage.setItem('accessToken', ...)
3. 이후 모든 요청 Header: Authorization: Bearer {token}
4. (선택) GET /auth/me  → 현재 사용자
```

### Postman vs React

| | Postman | React |
|---|---------|-------|
| 토큰 저장 | Environment 변수 | localStorage |
| 헤더 | Bearer Token 탭 | axios 인터셉터 |

---

## 3. 데이터 읽기 흐름

```javascript
useEffect(() => {
  itemApi.getList()
    .then(res => setItems(res.data.목록키))
}, []);
```

**시험 첫 1분:** Postman으로 목록 API 호출 → JSON **최상위 키 이름** 확인

---

## 4. 자주 나는 오류

| 증상 | 원인 |
|------|------|
| useAuth 에러 | AuthProvider 미연결 |
| 로그인 실패 (정보 맞는데) | useRef `.current.value` 안 씀 |
| 403 | 토큰 없음 / Bearer 빠짐 |
| 상세 403 | useParams 이름 ≠ 라우트 `:id` |
| CORS | 백엔드 포트·origin 설정 |

---

## 5. 문제지 받으면 적을 표 (1분)

```
Base URL: http://localhost:____

[로그인]
POST _______
Body: { _______ }
Token 키: _______

[목록]
GET _______
응답 배열 키: _______
항목 PK: _______
표시할 필드: _______

[인증 필요?] 목록 Y / N   등록 Y / N
```

이 표만 채우면 코딩 시작 가능.
