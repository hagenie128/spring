# quest/14 problem03 - 스터디 신청, 합격/대기 관리

> 참고 완성본: [step14](../../../step14)

## 목표

사용자가 스터디에 신청하고, 스터디장이 신청자를 합격/거절 처리할 수 있게 만드세요. 합격자는 따로 목록에 표시할 수 있어야 합니다.

## TODO

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | `ApplicationStatus.java` | `WAITING`, `ACCEPTED`, `REJECTED` enum 작성 |
| 2 | `StudyApplication.java` | 신청자 Member, 모집글 StudyRecruit 연관관계 매핑 |
| 3 | `StudyApplication.java` | 지원 메시지, 신청 상태, 신청일 필드 매핑 |
| 4 | `StudyApplication.java` | 회원+스터디 unique 제약조건 |
| 5 | `StudyApplicationRepository.java` | 모집글별 신청 목록 조회 |
| 6 | `StudyApplicationRepository.java` | 모집글별 합격자 목록 조회 |
| 7 | `StudyApplicationRepository.java` | 회원+모집글 신청 여부 조회 |
| 8 | `StudyApplicationService.java` | 스터디 신청 생성 |
| 9 | `StudyApplicationService.java` | 마감된 스터디 신청 불가 처리 |
| 10 | `StudyApplicationService.java` | 신청자 합격 처리 |
| 11 | `StudyApplicationService.java` | 합격 처리 시 `acceptedCount` 증가 |
| 12 | `StudyApplicationService.java` | 정원 도달 시 모집글 자동 마감 |
| 13 | `StudyApplicationService.java` | 정원이 찼으면 추가 합격 불가 처리 |
| 14 | `StudyRecruitController.java` | 신청 POST 핸들러 |
| 15 | `StudyRecruitController.java` | 합격/거절 POST 핸들러 |

## 완료 기준

- 로그인한 회원은 모집중인 스터디에 신청할 수 있습니다.
- 같은 스터디에 중복 신청할 수 없습니다.
- 스터디장은 신청자를 `합격` 또는 `거절` 처리할 수 있습니다.
- 합격자가 정원에 도달하면 모집글이 자동 마감됩니다.
- 정원이 찬 뒤에는 추가 합격 처리가 불가능합니다.
