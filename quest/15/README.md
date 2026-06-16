# quest/15 - 프론트/백엔드 분리 스터디 모집 앱

> Spring Boot REST API + React + Axios  
> 지금까지 배운 JPA, 검색/페이징, 상세 조회, 등록/수정, 조회수, 좋아요/싫어요, 파일 업로드/다운로드를 **백엔드 API와 React 화면으로 분리**해서 연습합니다.

---

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 01 | [problem01/](problem01/) | ★★★★★ | Spring REST API + React Axios 종합 |

**백엔드 실행:** `cd quest/15/problem01/backend` -> `gradlew.bat bootRun`  
**프론트 실행:** `cd quest/15/problem01/frontend` -> `npm install` -> `npm run dev`

---

## 전체 목표

스터디 모집 서비스를 프론트/백엔드 분리 구조로 만드세요.

- 백엔드: JSON API 제공
- 프론트: React에서 Axios로 API 호출
- 목록: 검색, 모집 상태 필터, 페이징
- 상세: 조회수 증가, 신청자/합격자 목록
- 등록/수정: React 폼 + Spring API
- 신청/합격: 대기/합격/거절 상태 관리
- 좋아요/싫어요: 토글 API
- 파일: 업로드/다운로드 API

---

## API 설계

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/studies` | 목록, 검색, 페이징 |
| GET | `/api/studies/{id}` | 상세 + 조회수 증가 |
| POST | `/api/studies` | 모집글 등록 |
| PUT | `/api/studies/{id}` | 모집글 수정 |
| POST | `/api/studies/{id}/applications` | 스터디 신청 |
| PATCH | `/api/applications/{id}/accept` | 신청 합격 처리 |
| PATCH | `/api/applications/{id}/reject` | 신청 거절 처리 |
| POST | `/api/studies/{id}/reactions` | 좋아요/싫어요 토글 |
| POST | `/api/studies/{id}/materials` | 파일 업로드 |
| GET | `/api/materials/{id}/download` | 파일 다운로드 |

---

## 백엔드 TODO

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | `StudyRecruit.java` | 엔티티 필드/연관관계 매핑 |
| 2 | `StudyApplication.java` | 신청 엔티티 매핑 |
| 3 | `StudyReaction.java` | 좋아요/싫어요 엔티티 매핑 |
| 4 | `StudyMaterial.java` | 첨부파일 엔티티 매핑 |
| 5 | `StudyRecruitRepository.java` | 검색 + 페이징 쿼리 |
| 6 | `StudyRecruitService.java` | 목록/상세/등록/수정 서비스 |
| 7 | `StudyApplicationService.java` | 신청/합격/거절 서비스 |
| 8 | `StudyReactionService.java` | 좋아요/싫어요 토글 서비스 |
| 9 | `StudyMaterialService.java` | 업로드/다운로드 서비스 |
| 10 | `StudyApiController.java` | REST API 핸들러 |

---

## 프론트 TODO

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | `src/api/http.js` | Axios 인스턴스 생성 |
| 2 | `src/api/studyApi.js` | API 함수 작성 |
| 3 | `src/pages/StudyListPage.jsx` | 목록, 검색, 페이징 |
| 4 | `src/pages/StudyDetailPage.jsx` | 상세, 신청자/합격자, 좋아요/싫어요 |
| 5 | `src/pages/StudyFormPage.jsx` | 등록/수정 폼 |
| 6 | `src/components/StudyCard.jsx` | 목록 카드 |
| 7 | `src/components/Pagination.jsx` | 페이지 이동 |
| 8 | `src/components/FileUpload.jsx` | 파일 업로드 |

---

## 완성 기준

- React 화면에서 Spring API를 Axios로 호출합니다.
- 목록 검색/필터/페이징이 동작합니다.
- 상세 진입 시 조회수가 증가합니다.
- 모집글 등록/수정이 React 폼으로 동작합니다.
- 신청자는 대기 목록에 들어가고, 합격 처리하면 합격 목록으로 이동합니다.
- 정원이 차면 추가 합격이 막히고 모집 상태가 마감됩니다.
- 좋아요/싫어요는 토글로 동작합니다.
- 파일 업로드 후 다운로드 링크로 받을 수 있습니다.

---

## 힌트

- 백엔드는 Thymeleaf를 쓰지 않습니다. `@RestController`와 JSON DTO를 사용하세요.
- 프론트는 서버 상태를 `useState`, `useEffect`로 관리하세요.
- Axios 요청 실패는 `try/catch`로 잡아서 화면에 에러 메시지를 보여주세요.
- 파일 업로드는 `FormData`를 사용하세요.
- 다운로드는 `<a href="백엔드다운로드URL">` 방식부터 구현하면 충분합니다.
