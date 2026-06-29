# step17 가이드 HTML 생성 — 집에서 할 일

## 현재 상태 (2025-06-29 수업 종료 기준)

| 파일 | 상태 |
|------|------|
| `step17_guide_prompt.md` | ✅ 완성 (오늘 추가분 전부 반영) |
| `spring_study_guide_v2.html` | ✅ 기존 HTML 가이드 (디자인 참고용) |
| `step17_board_guide.html` | ❌ 아직 미생성 — 집에서 아래 방법으로 만들기 |

---

## 집에서 할 일: HTML 가이드 생성

### 방법 1 — 빠른 방법 (권장)

1. `step17_guide_prompt.md` 파일 전체 내용 복사
2. Claude (claude.ai) 새 대화 열기
3. 아래 지시문 + 복사한 내용 붙여넣기:

---

**붙여넣기용 지시문 (앞에 붙이기):**

```
아래 프롬프트 내용을 바탕으로 step17_board_guide.html 파일을 만들어줘.

디자인 기준:
- 폰트: IBM Plex Sans KR, Gowun Dodum, JetBrains Mono, Space Grotesk (Google Fonts)
- 코드 하이라이팅: Prism.js (prism-one-light 테마)
- CSS 변수:
  --green:#1f9d4d  --blue:#1d6fd1  --amber:#b5810b  --violet:#7a4ed1  --pink:#d23b6b
  --bg:#f6f7f9  --bg-soft:#fff  --panel:#fff  --line:#e0e4ea
  --ink:#1c2430  --ink-soft:#46505e  --ink-dim:#7a8694
- 레이아웃: 왼쪽 sticky 사이드바(312px) + 오른쪽 스크롤 메인
- 반응형: ≤1024px 햄버거 메뉴, ≤680px 모바일 축소
- 읽기 진행바 (상단), 맨위로 버튼, 사이드바 검색 필터
- 사이드바 목차: 그룹별 접이식 (collapse/expand), 스크롤 시 active 하이라이트

색깔 박스 6종 (반드시 포함):
  .box.basic  { border-left: 4px solid var(--blue);   background: #e6f0fd; }  → 기본 의미
  .box.logic  { border-left: 4px solid var(--green);  background: #e7f6ec; }  → 로직·흐름
  .box.tip    { border-left: 4px solid var(--amber);  background: #fdf4e0; }  → 팁·알고리즘
  .box.warn   { border-left: 4px solid #d23b6b;       background: #fceef3; }  → 주의·오류
  .box.alt    { border-left: 4px solid #7a8694;       background: #f0f2f5; }  → 다른 방법
  .box.trend  { border-left: 4px solid var(--violet); background: #f0eafd; }  → 요즘 트렌드

기타 컴포넌트:
  .flow (ASCII 다이어그램 박스, monospace 배경)
  .tablewrap (가로 스크롤 테이블)
  SVG 다이어그램 (아키텍처 그림)

목차 구성 (이 순서대로 섹션 만들기):

[시작하기]
00: 이 문서를 읽는 법
01: step17 전체 그림 (React ↔ Spring Boot ↔ MySQL 아키텍처 SVG)
02: 개발환경 설치 & 실행법

[PART 1 · 백엔드]
03: 프로젝트 구조 & 계층 설명
04: build.gradle 의존성
05: application.properties 설정
06: DB 설계 & board.sql (테이블, VIEW, 외래키, CASCADE)
07: MyBatis XML Mapper (board-mapper.xml)
08: JPA Entity (UserEntity, RefreshToken)
09: Spring Security 설정 (SecurityConfig — 주석 설명 포함)
10: JWT 토큰 생성·검증 (JwtTokenProvider)
11: JWT 인증 필터 (JwtAuthenticationFilter)
12: UserDetails & AuthService (회원가입·로그인·로그아웃)
13: 게시글 CRUD (BoardMapper → BoardService → BoardController)
14: 댓글 CRUD (BoardCommentController)
15: 좋아요/싫어요 토글 알고리즘 (3가지 경우 흐름도)
16: PaggingVO 페이징 계산 알고리즘 (메서드별 수식 설명)
17: Swagger 설정 (SwaggerConfig)

[PART 2 · 프론트엔드]
18: React 프로젝트 구조 & 파일 역할
19: index.js & App.js (BrowserRouter, Routes)
20: AuthContext (전역 인증 상태 — Context API)
21: axiosInstance (Axios 공통 설정 & JWT 자동 첨부 인터셉터)
22: API 레이어 (authApi.js, postApi.js, commentApi.js)
23: NavBar 컴포넌트 (TODO 미완성 설명)
24: QuillEditor 컴포넌트 (Quill.js — useRef 3개 패턴, initializedRef)
25: 게시글 목록 페이지 (PostListPage — useEffect, useState, map)
26: 게시글 상세 페이지 (PostDetailPage — 반응 토글, 수정/삭제, dangerouslySetInnerHTML)
27: 로그인 페이지 (LoginPage — useRef, useAuth, 버그 3개 해설)
28: 페이징 컴포넌트 (PaggingBar — props, 버튼 생성 알고리즘)
29: 게시글 작성/수정 페이지 (PostWritePage — isEditorMode 분기, QuillEditor key 패턴)
30: 미완성 페이지 (SignupPage)

[PART 3 · 연동 흐름]
31: 로그인 전체 흐름 (React → Spring Security → JWT → localStorage)
32: 게시글 목록 조회 흐름 (useEffect → axiosInstance → Controller → MyBatis → MySQL)
33: 글쓰기/수정 흐름 (PostWritePage → postApi → BoardController → useGeneratedKeys)
34: CORS 설정 (왜 필요한지, SecurityConfig corsConfigrationSource 설명)
35: 자주 막히는 오류 & 해결법

[마무리]
36: 요즘 트렌드 (Next.js, TanStack Query, Zustand, MSW, Docker)
37: 핵심 용어 사전
38: 공부 순서 & 다음 단계

[보너스 — 수업에서 추가 설명한 내용]
A: 기능 추가할 때 프론트↔백 작업 순서 7단계 체크리스트
B: 내일 진도 — 댓글 기능 완성 구현 가이드

--- 아래부터 프로젝트 코드 전문 붙여넣기 ---
```

그 다음 `step17_guide_prompt.md` 전체 내용을 붙여넣기.

---

### 방법 2 — 디자인 참고 파일 있는 경우 (더 정확한 디자인)

`spring_study_guide_v2.html` 파일의 CSS 부분(`:root { }` ~ `</style>`)을 따로 복사해서
Claude에게 "이 CSS 스타일 그대로 써줘" 라고 함께 전달하면 디자인이 더 정확하게 나옵니다.

---

## 오늘 수업에서 업데이트한 내용 요약 (guide_prompt에 이미 반영됨)

- [x] `SecurityConfig.java` — 상세 주석 추가, `maxAge(3600L)`, Swagger URL 분리
- [x] `BoardController.java` — `getBoardList`, 2인자 `PaggingVO`, 삭제/수정 권한 체크, 반응 응답 `{ count }` 구조
- [x] `BoardService.java` — 메서드명 정리 (`selectBoardList→getBoardList`, `insertBoard→addBoard` 등)
- [x] `BoardMapper.java` / `BoardCommentMapper.java` — `@Param` 정리, `deleteBoardCommentByBno` 추가
- [x] `PaggingVO.java` — 3인자→2인자 생성자, size 내부 상수(30) 고정, 계산 메서드로 변경
- [x] `board-mapper.xml` — `insertBoard` useGeneratedKeys 추가
- [x] `board.sql` — WITH CTE VIEW, CASCADE FK, UNIQUE 반응 제약
- [x] `board-fk-migration.sql` — 기존 DB용 FK 재설정 파일 (신규)
- [x] `postApi.js` — `create`, `update`, `remove`, `postReaction` 추가
- [x] `commentApi.js` — 신규 (미완성 스텁)
- [x] `QuillEditor.jsx` — 신규 (useRef 3개 패턴)
- [x] `PostDetailPage.jsx` — 반응 토글, 수정/삭제, `dangerouslySetInnerHTML`, 권한 체크
- [x] `PostWritePage.jsx` — 스텁→완성 (isEditorMode, QuillEditor 연동)
- [x] 목차 추가: QuillEditor (24번), PostWritePage 완성 (29번), 댓글 기능 가이드 (보너스 B)

---

## 내일 수업 예정 (참고)

- `commentApi.js` 완성
- `PostDetailPage.jsx` 댓글 작성/수정/삭제/좋아요 연동
- `BoardCommentController` / `BoardCommentService` 복습
- `board-comment-mapper.xml` 댓글 반응 SQL 확인
