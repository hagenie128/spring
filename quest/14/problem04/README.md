# quest/14 problem04 - 상세, 조회수, 수정, 파일, 좋아요/싫어요

> 참고 완성본: [step14](../../../step14)

## 목표

스터디 상세 화면에서 대기 신청자와 합격자를 구분해 보여주고, 조회수 증가, 모집글 수정, 파일 업로드/다운로드, 좋아요/싫어요 토글 기능을 완성하세요.

## TODO

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | `StudyRecruitRepository.java` | 상세 조회 쿼리 |
| 2 | `StudyApplicationRepository.java` | `WAITING` 신청자 목록 조회 |
| 3 | `StudyApplicationRepository.java` | `ACCEPTED` 합격자 목록 조회 |
| 4 | `StudyRecruitService.java` | 상세 조회 + 조회수 증가 |
| 5 | `StudyApplicationService.java` | 대기 신청자 목록 서비스 |
| 6 | `StudyApplicationService.java` | 합격자 목록 서비스 |
| 7 | `StudyInterest.java` | 회원, 모집글, 생성일 매핑 |
| 8 | `InterestType.java` | `LIKE`, `DISLIKE` enum 작성 |
| 9 | `StudyInterest.java` | 회원+모집글 unique 제약조건, 반응 타입 매핑 |
| 10 | `StudyInterestRepository.java` | 반응 조회와 개수 조회 |
| 11 | `StudyInterestService.java` | 좋아요/싫어요 처리 |
| 12 | `StudyRecruitController.java` | `GET /studies/{id}` 상세 핸들러 |
| 13 | `StudyRecruitController.java` | 상세 화면 데이터 전달 |
| 14 | `StudyRecruitController.java` | 좋아요/싫어요 토글 POST 핸들러 |
| 15 | `detail.html` | 스터디 정보, 정원, 모집 상태, 신청 폼 출력 |
| 16 | `detail.html` | 신청자 목록과 합격자 목록을 따로 출력 |
| 17 | `StudyRecruitController.java` | `GET /studies/{id}/edit` 수정 폼 |
| 18 | `StudyRecruitService.java` | 모집글 수정 서비스 |
| 19 | `StudyRecruitController.java` | `POST /studies/{id}/edit` 수정 처리 |
| 20 | `form.html` | 등록/수정 공용 폼 |
| 21 | `StudyMaterial.java` | 원본 파일명, 저장 파일명, 파일 크기 필드 매핑 |
| 22 | `StudyMaterial.java` | 모집글과 `ManyToOne` 매핑 |
| 23 | `StudyMaterialRepository.java` | 모집글별 파일 목록 조회 |
| 24 | `StudyMaterialService.java` | 파일 업로드 저장 |
| 25 | `StudyMaterialService.java` | 파일 다운로드 처리 |
| 26 | `detail.html` | 파일 업로드 폼과 다운로드 링크 출력 |

## 완료 기준

- 상세 화면에서 `대기 신청자`와 `합격 멤버`가 따로 보입니다.
- 합격 멤버 목록에는 닉네임과 지원 메시지가 표시됩니다.
- 합격 인원/정원이 실시간으로 맞게 표시됩니다.
- 상세 조회 시 조회수가 1 증가합니다.
- 모집글 수정 화면에서 제목/소개글/기술스택/진행방식/정원을 수정할 수 있습니다.
- 파일 업로드 후 상세 화면에서 다운로드할 수 있습니다.
- 좋아요/싫어요는 각각 개수가 표시되고, 같은 반응을 다시 누르면 취소됩니다.
