# quest/14 problem04 - 상세 화면, 합격자 목록, 관심 반응

> 참고 완성본: [step14](../../../step14)

## 목표

스터디 상세 화면에서 대기 신청자와 합격자를 구분해 보여주고, 관심 있어요 토글 기능을 완성하세요.

## TODO

| TODO | 파일 | 할 일 |
|------|------|-------|
| 1 | `StudyRecruitRepository.java` | 상세 조회 시 작성자 fetch join |
| 2 | `StudyApplicationRepository.java` | `WAITING` 신청자 목록 조회 |
| 3 | `StudyApplicationRepository.java` | `ACCEPTED` 합격자 목록 조회 |
| 4 | `StudyRecruitService.java` | 상세 조회 + 조회수 증가 |
| 5 | `StudyApplicationService.java` | 대기 신청자 목록 서비스 |
| 6 | `StudyApplicationService.java` | 합격자 목록 서비스 |
| 7 | `StudyInterest.java` | 회원, 모집글, 생성일 매핑 |
| 8 | `StudyInterest.java` | 회원+모집글 unique 제약조건 |
| 9 | `StudyInterestRepository.java` | 관심 여부 조회 |
| 10 | `StudyInterestRepository.java` | 모집글별 관심 수 조회 |
| 11 | `StudyInterestService.java` | 관심 등록/취소 토글 |
| 12 | `StudyRecruitController.java` | `GET /studies/{id}` 상세 핸들러 |
| 13 | `StudyRecruitController.java` | model에 study, waitingApplications, acceptedApplications 담기 |
| 14 | `StudyRecruitController.java` | 관심 토글 POST 핸들러 |
| 15 | `detail.html` | 스터디 정보, 정원, 모집 상태, 신청 폼 출력 |
| 16 | `detail.html` | 신청자 목록과 합격자 목록을 따로 출력 |

## 완료 기준

- 상세 화면에서 `대기 신청자`와 `합격 멤버`가 따로 보입니다.
- 합격 멤버 목록에는 닉네임과 지원 메시지가 표시됩니다.
- 합격 인원/정원이 실시간으로 맞게 표시됩니다.
- 관심 있어요 버튼을 누르면 관심 수가 증가/감소합니다.
