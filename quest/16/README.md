# Quest 16 — 6월 19일~25일 보충 실습

> Spring Security · JWT · React · OAuth2 · MyBatis 게시판 API

수업이 진행된 평일 기준으로 다섯 문제를 순서대로 풀도록 구성했습니다.
각 문제는 독립 실행할 수 있지만, 앞 문제에서 배운 인증 흐름이 뒤 문제에 계속 이어집니다.

| 날짜 | 문제 | 핵심 주제 | 참고 완성본 |
|---|---|---|---|
| 6월 19일 | [problem01-jwt-backend](problem01-jwt-backend/) | Spring Security와 JWT 인증 | `step15-security-basic` |
| 6월 22일 | [problem02-react-jwt](problem02-react-jwt/) | React에서 JWT API 호출 | `step15-security-basic-front` |
| 6월 23일 | [problem03-oauth2](problem03-oauth2/) | Google OAuth2와 자체 JWT 연결 | `step16-security-oauth2-back/front` |
| 6월 24일 | [problem04-board-api](problem04-board-api/) | JWT + MyBatis 게시판 API | `step17-board-backend` |
| 6월 25일 | [problem05-board-crud-reaction](problem05-board-crud-reaction/) | 게시글·댓글 CRUD와 반응 토글 | `step17-board-backend` |

## 추천 진행 방식

1. 각 문제의 README와 `TODO`를 먼저 읽습니다.
2. 참고 완성본은 최대한 늦게 엽니다.
3. 백엔드는 `compileJava`로 자주 컴파일합니다.
4. API는 브라우저보다 Postman, Swagger 또는 `.http` 파일로 먼저 확인합니다.
5. 완료 조건을 모두 만족하면 다음 문제로 넘어갑니다.

## 공통 준비

- Java 21
- MySQL 8
- Node.js 20 이상
- 백엔드 기본 포트: `8888`
- 프론트 기본 포트: `3000`
