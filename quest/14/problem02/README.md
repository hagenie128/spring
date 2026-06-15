# quest/14 problem02 - 모집글 등록, 정원, 마감 상태

> 참고 완성본: [step14](../../../step14)

## 목표

스터디 모집글 등록 화면을 만들고, 모집 정원과 마감 상태를 관리하세요.

## TODO

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | `RecruitStatus.java` | `OPEN`, `CLOSED` enum 작성 |
| 2 | `StudyRecruit.java` | 모집 정원은 1명 이상으로 검증 |
| 3 | `StudyRecruit.java` | 기본 상태를 `OPEN`으로 설정 |
| 4 | `StudyRecruit.java` | `isFull()` 메서드 작성 |
| 5 | `StudyRecruit.java` | `closeIfFull()` 메서드 작성 |
| 6 | `StudyRecruitService.java` | 등록 요청으로 StudyRecruit 생성 |
| 7 | `StudyRecruitService.java` | 작성자 Member 조회 후 연결 |
| 8 | `StudyRecruitService.java` | 정원이 잘못된 경우 예외 처리 |
| 9 | `StudyRecruitController.java` | `GET /studies/new` 핸들러 |
| 10 | `StudyRecruitController.java` | `POST /studies` 핸들러 |
| 11 | `form.html` | 제목, 소개글, 기술스택, 진행방식 입력 |
| 12 | `form.html` | 모집 정원 입력 |
| 13 | `list.html` | 마감 글은 신청 버튼 대신 `마감` 표시 |

## 완료 기준

- 모집글 등록 시 모집 정원을 반드시 입력합니다.
- 새 모집글은 기본적으로 `모집중` 상태입니다.
- 정원이 0 이하이면 등록되지 않습니다.
- 마감된 모집글은 목록에서 명확히 구분됩니다.
