# quest/14 problem01 - 스터디 모집 목록, 검색, 페이징

> 참고 완성본: [step14](../../../step14)

## 목표

`/studies`에서 스터디 모집글을 카드 목록으로 보여주고, 키워드/모집상태 검색과 페이징을 완성하세요.

## TODO

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | `StudyRecruit.java` | 제목, 소개글, 기술스택, 진행방식, 조회수 필드 매핑 |
| 2 | `StudyRecruit.java` | 모집 정원 `capacity`, 합격 인원 `acceptedCount` 필드 매핑 |
| 3 | `StudyRecruit.java` | 모집 상태 `RecruitStatus` 매핑 |
| 4 | `StudyRecruit.java` | 작성자 `Member`와 `ManyToOne` 매핑 |
| 5 | `StudyRecruit.java` | 작성일/수정일 자동 설정 |
| 6 | `StudyRecruitRepository.java` | 작성자 fetch join 목록 쿼리 |
| 7 | `StudyRecruitRepository.java` | 키워드 + 모집 상태 검색 쿼리 |
| 8 | `StudyRecruitService.java` | 검색 조건에 따른 Page 조회 |
| 9 | `StudyRecruitController.java` | `keyword`, `status`, `page`, `size` 파라미터 받기 |
| 10 | `StudyRecruitController.java` | `studyPage`, `keyword`, `status`, `currentPage` model 추가 |
| 11 | `list.html` | 카드 목록, 정원, 상태, 페이지 링크 출력 |

## 완료 기준

- `/studies` 접속 시 스터디 모집글이 최신순으로 보입니다.
- 제목/내용/기술스택 기준으로 검색됩니다.
- `모집중`, `마감` 상태 필터가 동작합니다.
- 카드마다 `합격 인원 / 모집 정원`이 표시됩니다.
