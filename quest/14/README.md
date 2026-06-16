# quest/14 - 스터디 모집 플랫폼

> Spring Boot 3.5 | Java 21 | JPA | Thymeleaf | MySQL  
> **참고 완성본:** [step14](../../step14) - 목록, 검색, 페이징, 회원, 댓글, 반응 도메인 패턴 참고  
> 게시판 그대로가 아니라 **스터디 모집 서비스**로 도메인을 바꿔서 연습합니다.

---

## 도메인 매핑

| step14 게시판 | quest14 스터디 모집 |
|---------------|--------------------|
| Post | StudyRecruit |
| Member | Member |
| Comment | StudyApplication 또는 QuestionComment |
| Attachment | StudyMaterial |
| PostReaction | StudyInterest |
| ReactionType | InterestType |
| `/board/list` | `/studies` |

---

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 | TODO |
|------|------|--------|-----------|------|
| 01 | [problem01/](problem01/) | ★★☆☆ | 스터디 모집 목록 + 검색 + 페이징 | 1~11 |
| 02 | [problem02/](problem02/) | ★★★☆ | 모집글 등록 + 정원 + 마감 상태 | 1~13 |
| 03 | [problem03/](problem03/) | ★★★★ | 스터디 신청 + 합격/대기 관리 | 1~15 |
| 04 | [problem04/](problem04/) | ★★★★★ | 상세 + 조회수 + 수정 + 파일 다운로드 + 좋아요/싫어요 | 1~26 |

**실행:** `cd quest/14/problem0N` -> `gradlew.bat bootRun`  
**URL:** http://localhost:8080/studies  
**참고 시작점:** [DataInitializer.java](../../step14/src/main/java/com/spring/DataInitializer.java)

---

## 문제 1 - 스터디 모집 목록, 검색, 페이징 (★★☆☆)

**폴더:** [problem01/](problem01/)

스터디 모집글을 목록으로 보여주는 첫 문제입니다.  
제목/내용/기술스택 검색, 모집 상태 필터, 페이징을 완성하세요.

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | `StudyRecruit.java` | 제목, 소개글, 기술스택, 진행방식, 조회수 필드 매핑 |
| TODO 2 | `StudyRecruit.java` | 모집 정원 `capacity`, 현재 합격 인원 `acceptedCount` 필드 매핑 |
| TODO 3 | `StudyRecruit.java` | 모집 상태 `RecruitStatus` 매핑 |
| TODO 4 | `StudyRecruit.java` | 작성자 `Member`와 `ManyToOne` 연관관계 매핑 |
| TODO 5 | `StudyRecruit.java` | 작성일/수정일 자동 설정 |
| TODO 6 | `StudyRecruitRepository.java` | 작성자 fetch join 목록 쿼리 |
| TODO 7 | `StudyRecruitRepository.java` | 키워드 + 모집 상태 검색 쿼리 |
| TODO 8 | `StudyRecruitService.java` | 검색 조건에 따라 Page 조회 |
| TODO 9 | `StudyRecruitController.java` | `keyword`, `status`, `page`, `size` 파라미터 받기 |
| TODO 10 | `StudyRecruitController.java` | model에 `studyPage`, `keyword`, `status`, `currentPage` 담기 |
| TODO 11 | `list.html` | 모집글 카드 목록, 정원, 상태, 페이지 링크 출력 |

**step14 참고:** [Post.java](../../step14/src/main/java/com/spring/entity/Post.java) · [PostRepository.java](../../step14/src/main/java/com/spring/repository/PostRepository.java) · [PostService.java](../../step14/src/main/java/com/spring/service/PostService.java) · [PostController.java](../../step14/src/main/java/com/spring/controller/PostController.java) · [list.html](../../step14/src/main/resources/templates/board/list.html)

**완성 기준**

- `/studies`에 샘플 스터디 모집글이 최신순으로 표시됨
- 제목/내용/기술스택으로 검색 가능
- `모집중`, `마감` 상태 필터 가능
- 카드마다 `합격 인원 / 모집 정원`이 표시됨

---

## 문제 2 - 모집글 등록, 정원, 마감 상태 (★★★☆)

**폴더:** [problem02/](problem02/)  
**문제 1 목록 기능은 완성되어 있다고 가정**

스터디장이 모집글을 등록하고, 모집 정원을 설정하는 문제입니다.  
합격 인원이 정원에 도달하면 자동으로 `CLOSED` 상태가 되도록 설계하세요.

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | `RecruitStatus.java` | `OPEN`, `CLOSED` enum 작성 |
| TODO 2 | `StudyRecruit.java` | 모집 정원은 1명 이상으로 검증 |
| TODO 3 | `StudyRecruit.java` | 기본 상태를 `OPEN`으로 설정 |
| TODO 4 | `StudyRecruit.java` | `isFull()` 메서드 작성 |
| TODO 5 | `StudyRecruit.java` | `closeIfFull()` 메서드 작성 |
| TODO 6 | `StudyRecruitService.java` | 등록 요청으로 StudyRecruit 생성 |
| TODO 7 | `StudyRecruitService.java` | 작성자 Member 조회 후 연결 |
| TODO 8 | `StudyRecruitService.java` | 정원이 잘못된 경우 예외 처리 |
| TODO 9 | `StudyRecruitController.java` | `GET /studies/new` 핸들러 |
| TODO 10 | `StudyRecruitController.java` | `POST /studies` 핸들러 |
| TODO 11 | `form.html` | 제목, 소개글, 기술스택, 진행방식 입력 |
| TODO 12 | `form.html` | 모집 정원 입력 |
| TODO 13 | `list.html` | 모집 마감 글은 신청 버튼 대신 `마감` 표시 |

**step14 참고:** [Member.java](../../step14/src/main/java/com/spring/entity/Member.java) · [Post.java](../../step14/src/main/java/com/spring/entity/Post.java)

**완성 기준**

- 모집글 등록 시 모집 정원을 반드시 입력함
- 새 모집글은 기본적으로 `모집중` 상태임
- 정원이 0 이하이면 등록되지 않음
- 마감된 모집글은 목록에서 명확히 구분됨

---

## 문제 3 - 스터디 신청, 합격/대기 관리 (★★★★)

**폴더:** [problem03/](problem03/)  
**모집글 등록과 정원 기능은 완성되어 있다고 가정**

사용자가 스터디에 신청하고, 스터디장이 신청자를 합격 처리하는 문제입니다.  
합격한 사람은 따로 모아서 목록에 표시할 수 있어야 합니다.

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | `ApplicationStatus.java` | `WAITING`, `ACCEPTED`, `REJECTED` enum 작성 |
| TODO 2 | `StudyApplication.java` | 신청자 Member, 모집글 StudyRecruit 연관관계 매핑 |
| TODO 3 | `StudyApplication.java` | 지원 메시지, 신청 상태, 신청일 필드 매핑 |
| TODO 4 | `StudyApplication.java` | 같은 회원이 같은 스터디에 중복 신청하지 못하게 unique 제약조건 추가 |
| TODO 5 | `StudyApplicationRepository.java` | 모집글별 신청 목록 조회 |
| TODO 6 | `StudyApplicationRepository.java` | 모집글별 합격자 목록 조회 |
| TODO 7 | `StudyApplicationRepository.java` | 회원+모집글 신청 여부 조회 |
| TODO 8 | `StudyApplicationService.java` | 스터디 신청 생성 |
| TODO 9 | `StudyApplicationService.java` | 마감된 스터디에는 신청 불가 처리 |
| TODO 10 | `StudyApplicationService.java` | 신청자 합격 처리 |
| TODO 11 | `StudyApplicationService.java` | 합격 처리 시 `acceptedCount` 증가 |
| TODO 12 | `StudyApplicationService.java` | 정원 도달 시 모집글 자동 마감 |
| TODO 13 | `StudyApplicationService.java` | 정원이 찼으면 추가 합격 불가 처리 |
| TODO 14 | `StudyRecruitController.java` | 신청 POST 핸들러 |
| TODO 15 | `StudyRecruitController.java` | 합격/거절 POST 핸들러 |

**step14 참고:** [Comment.java](../../step14/src/main/java/com/spring/entity/Comment.java) · [CommentRepository.java](../../step14/src/main/java/com/spring/repository/CommentRepository.java)

**완성 기준**

- 로그인한 회원은 모집중인 스터디에 신청 가능
- 같은 스터디에 중복 신청 불가
- 스터디장은 신청자를 `합격` 또는 `거절` 처리 가능
- 합격자가 정원에 도달하면 모집글이 자동 마감됨
- 정원이 찬 뒤에는 추가 합격 처리가 불가능함

---

## 문제 4 - 상세, 조회수, 수정, 파일, 좋아요/싫어요 (★★★★★)

**폴더:** [problem04/](problem04/)  
**신청/합격/마감 기능까지 완성되어 있다고 가정**

스터디 상세 화면에서 신청자와 합격자를 구분해 보여주고, 오늘 배운 조회수 관리, 모집글 수정, 파일 업로드/다운로드, 좋아요/싫어요 반응까지 추가합니다.

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | `StudyRecruitRepository.java` | 상세 조회 시 작성자 fetch join |
| TODO 2 | `StudyApplicationRepository.java` | `WAITING` 신청자 목록 조회 |
| TODO 3 | `StudyApplicationRepository.java` | `ACCEPTED` 합격자 목록 조회 |
| TODO 4 | `StudyRecruitService.java` | 상세 조회 + 조회수 증가 |
| TODO 5 | `StudyApplicationService.java` | 대기 신청자 목록 서비스 |
| TODO 6 | `StudyApplicationService.java` | 합격자 목록 서비스 |
| TODO 7 | `StudyInterest.java` | 회원, 모집글, 생성일 매핑 |
| TODO 8 | `InterestType.java` | `LIKE`, `DISLIKE` enum 작성 |
| TODO 9 | `StudyInterest.java` | 회원+모집글 unique 제약조건, 반응 타입 매핑 |
| TODO 10 | `StudyInterestRepository.java` | 관심 여부 조회, LIKE/DISLIKE 개수 조회 |
| TODO 11 | `StudyInterestService.java` | 좋아요/싫어요 등록·변경·취소 토글 |
| TODO 12 | `StudyRecruitController.java` | `GET /studies/{id}` 상세 핸들러 |
| TODO 13 | `StudyRecruitController.java` | 상세 model에 study, waitingApplications, acceptedApplications 담기 |
| TODO 14 | `StudyRecruitController.java` | 좋아요/싫어요 토글 POST 핸들러 |
| TODO 15 | `detail.html` | 스터디 정보, 정원, 모집 상태, 신청 폼 출력 |
| TODO 16 | `detail.html` | 신청자 목록과 합격자 목록을 따로 출력 |
| TODO 17 | `StudyRecruitController.java` | `GET /studies/{id}/edit` 수정 폼 |
| TODO 18 | `StudyRecruitService.java` | 모집글 수정 서비스 |
| TODO 19 | `StudyRecruitController.java` | `POST /studies/{id}/edit` 수정 처리 |
| TODO 20 | `form.html` | 등록/수정 공용 폼으로 변경 |
| TODO 21 | `StudyMaterial.java` | 원본 파일명, 저장 파일명, 파일 크기 필드 매핑 |
| TODO 22 | `StudyMaterial.java` | 모집글과 `ManyToOne` 매핑 |
| TODO 23 | `StudyMaterialRepository.java` | 모집글별 파일 목록 조회 |
| TODO 24 | `StudyMaterialService.java` | 파일 업로드 저장 |
| TODO 25 | `StudyMaterialService.java` | 다운로드 Resource 조회 |
| TODO 26 | `detail.html` | 파일 업로드 폼과 다운로드 링크 출력 |

**step14 참고:** [PostReaction.java](../../step14/src/main/java/com/spring/entity/PostReaction.java) · [Attachment.java](../../step14/src/main/java/com/spring/entity/Attachment.java) · [AttachmentService.java](../../step14/src/main/java/com/spring/service/AttachmentService.java) · [PostController.java](../../step14/src/main/java/com/spring/controller/PostController.java)

**완성 기준**

- 상세 화면에서 `대기 신청자`와 `합격 멤버`가 따로 보임
- 합격 멤버 목록에는 닉네임과 지원 메시지가 표시됨
- 합격 인원/정원이 실시간으로 맞게 표시됨
- 상세 조회할 때마다 조회수가 1 증가함
- 모집글 수정 화면에서 제목/소개글/기술스택/진행방식/정원을 수정할 수 있음
- 파일 업로드 후 상세 화면에서 다운로드 가능
- 좋아요/싫어요를 누르면 개수가 증가/감소하고, 같은 반응을 다시 누르면 취소됨

---

## 주요 엔티티 설계

| 엔티티 | 주요 필드 |
|--------|-----------|
| `Member` | username, password, nickname, role, createdAt |
| `StudyRecruit` | title, description, techStack, method, capacity, acceptedCount, status, leader, viewCount, createdAt, updatedAt |
| `StudyApplication` | studyRecruit, applicant, message, status, createdAt |
| `StudyInterest` | studyRecruit, member, type, createdAt |
| `StudyMaterial` | studyRecruit, originalName, storedName, fileSize, createdAt |

---

## 팁

- 정원은 `capacity`, 합격 인원은 `acceptedCount`처럼 분리하면 화면 출력과 마감 판단이 쉬워집니다.
- 합격 처리 로직은 반드시 `@Transactional` 안에서 처리하세요.
- 합격 처리 순서: 신청 조회 -> 이미 합격인지 확인 -> 정원 확인 -> 상태 변경 -> 합격 인원 증가 -> 정원 도달 시 마감.
- 화면에서는 신청자 전체 목록보다 `WAITING`과 `ACCEPTED`를 나눠서 보여주는 편이 요구사항이 선명합니다.
- 검색 후 페이지 이동 링크에는 `keyword`, `status`를 계속 같이 넘겨야 검색 상태가 유지됩니다.
- 좋아요/싫어요는 회원+모집글 unique 제약조건을 유지하고, type만 바꾸는 방식으로 처리하면 중복 반응을 막기 쉽습니다.
- 파일 다운로드 응답에는 `Content-Disposition: attachment` 헤더를 넣어야 브라우저가 다운로드로 처리합니다.

**진행 순서:** [problem01](problem01/) -> [problem02](problem02/) -> [problem03](problem03/) -> [problem04](problem04/)
