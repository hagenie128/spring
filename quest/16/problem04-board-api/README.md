# 문제 4 — JWT + MyBatis 게시판 API

JWT 인증을 게시판 API에 결합하고 MyBatis로 목록·상세·등록을 구현하세요.

## TODO

| TODO | 파일 | 할 일 |
|---|---|---|
| 1 | `board.sql` | DB, 테이블, View 생성 |
| 2 | `BoardMapper.java` | 목록·검색·상세·댓글 조회 메서드 확인 |
| 3 | `board-mapper.xml` | `#{}` 파라미터로 페이징 쿼리 완성 |
| 4 | `BoardService.java` | Mapper 호출 서비스 구현 |
| 5 | `BoardController.java` | 목록과 페이징 정보를 JSON으로 응답 |
| 6 | `BoardController.java` | 상세와 댓글 목록 응답 |
| 7 | `BoardController.java` | 인증 회원을 작성자로 사용해 게시글 등록 |
| 8 | 별도 count 쿼리 | 검색 결과용 전체 개수 구현 |
| 9 | `PaggingVO.java` | 요청의 `size`와 페이지 계산 크기 통일 |

## 필수 수정 과제

완성본에도 학습용 중간 코드가 남아 있습니다. 다음 두 문제를 직접 고치세요.

1. 검색 SQL의 `${size}`를 안전한 `#{size}`로 변경
2. Controller의 기본 `size=20`과 `PaggingVO`의 30 고정값 통일

## 실행

1. `backend/src/main/resources/board.sql`을 MySQL에서 실행합니다.
2. 백엔드를 실행합니다.

```powershell
cd backend
.\gradlew.bat bootRun
```

Swagger:

```text
http://localhost:8888/swagger-ui/index.html
```

## 완료 조건

- 비로그인 사용자가 목록과 상세 조회 가능
- 비로그인 게시글 등록은 401
- 로그인 사용자는 게시글 등록 가능
- 검색 결과에 맞는 전체 페이지 수가 표시됨
- 페이지 크기를 바꿔도 목록과 페이징 계산이 일치함

