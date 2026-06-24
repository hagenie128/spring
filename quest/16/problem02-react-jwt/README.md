# 문제 2 — React에서 JWT 사용

문제 1의 백엔드를 React에서 호출하고 로그인 상태를 관리하세요.

## TODO

1. Axios 공통 인스턴스를 만듭니다.
2. 회원가입과 로그인 요청을 구현합니다.
3. 로그인 응답의 두 토큰을 `localStorage`에 저장합니다.
4. 보호 API 요청에 `Authorization: Bearer ...` 헤더를 붙입니다.
5. 새로고침 후 저장된 Access Token을 다시 읽습니다.
6. 로그아웃 성공 후 두 토큰을 삭제합니다.
7. 401 응답을 사용자가 이해할 수 있는 메시지로 표시합니다.

## 보너스

- Axios 요청 인터셉터로 Authorization 헤더 자동 추가
- Axios 응답 인터셉터로 401 공통 처리
- Access Token을 화면 전체에 그대로 출력하지 않기

## 실행

```powershell
cd frontend
npm install
npm start
```

백엔드는 문제 1의 서버를 `8888` 포트로 실행해야 합니다.

