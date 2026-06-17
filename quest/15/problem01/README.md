# quest/15 problem01 - React + Axios + Spring REST API

## 구조

```
problem01/
  backend/   Spring Boot REST API (포트 8080)
  frontend/  React + Vite + Axios (포트 5173)
```

## 진행 순서

1. 백엔드 엔티티와 샘플 데이터를 완성합니다. (`entity/`, `DataInitializer.java`)
2. Repository를 완성합니다. (`repository/`)
3. CORS를 설정합니다. (`config/WebConfig.java`)
4. Service를 완성합니다. (`service/`)
5. Controller를 완성합니다. (`controller/StudyApiController.java`)
6. 프론트 API 레이어를 완성합니다. (`frontend/src/api/`)
7. 페이지 컴포넌트를 완성합니다. (`frontend/src/pages/`)
8. 카드/페이징/파일 컴포넌트를 완성합니다. (`frontend/src/components/`)

## 실행 방법

### 백엔드
```bash
cd quest/15/problem01/backend
gradlew.bat bootRun
```

### 프론트엔드
```bash
cd quest/15/problem01/frontend
npm install
npm run dev
```

정답 코드는 들어 있지 않습니다. 메서드 이름과 파일 이름만 길잡이로 사용하세요.
