# Step14 - Spring Boot JPA 게시판 학습 가이드

> 오늘(2026-06-17) 수업에서 만든 **JPA 기반 게시판** 프로젝트 복습 가이드입니다.  
> 수업을 못 들었다면 아래 순서대로 파일을 읽으면 전체 흐름을 파악할 수 있습니다.

---

## 파일 읽는 순서

### 1단계: 설정 파일 이해
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 1 | `build.gradle` | 의존성 구성 (JPA, Thymeleaf, Validation, Security-Crypto, MySQL) |
| 2 | `src/main/resources/application.properties` | DB 연결, JPA 설정, 파일 업로드 설정 |

### 2단계: 엔티티(Entity) — DB 테이블과 매핑되는 클래스
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 3 | `entity/Member.java` | @Entity, @Id, @GeneratedValue, @Column, @PrePersist |
| 4 | `entity/Post.java` | @ManyToOne(LAZY), @OneToMany(mappedBy, cascade, orphanRemoval) |
| 5 | `entity/Comment.java` | 다대일(N:1) 양방향 연관관계 (Post ↔ Comment) |
| 6 | `entity/Attachment.java` | 파일 메타데이터 저장, N:1 연관 (Post ↔ Attachment) |

### 3단계: Repository — DB 접근 계층
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 7 | `repository/PostRepository.java` | @Query JPQL, 페치 조인, 페이징(Page), N+1 문제 해결 |
| 8 | `repository/CommentRepository.java` | @Query vs 메서드명 기반 쿼리 두 가지 방법 비교 |

### 4단계: DTO — 화면과 서버 간 데이터 전송 객체
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 9 | `dto/MemberDTO.java` | 회원가입 폼 DTO, @NotBlank, @Size 유효성 검증 |
| 10 | `dto/PostFormDTO.java` | 게시글 작성 폼 DTO |
| 11 | `dto/CommentFormDTO.java` | 댓글 입력 폼 DTO |

### 5단계: Service — 비즈니스 로직
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 12 | `service/MemberService.java` | BCrypt 암호화, 로그인 인증, 중복 체크 |
| 13 | `service/PostService.java` | @Transactional, readOnly, 게시글 CRUD, 조회수 업데이트 |
| 14 | `service/CommentService.java` | 댓글 CRUD, 서비스 계층의 역할 |
| 15 | `service/AttachmentService.java` | UUID 파일명 변환, Files.copy(), 물리 파일 저장 |

### 6단계: Controller — HTTP 요청/응답 처리
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 16 | `controller/AuthController.java` | 회원가입/로그인/로그아웃, 세션(HttpSession), FlashAttribute |
| 17 | `controller/PostController.java` | 게시글 CRUD, ModelAndView, 페이징, 조회수 HashSet 중복 방지 |
| 18 | `controller/CommentController.java` | 댓글 등록/삭제, 앵커(#comments) 리다이렉트 |

### 7단계: 화면 (Thymeleaf 템플릿)
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 19 | `templates/layout/base.html` | 공통 레이아웃 (th:fragment, th:replace) |
| 20 | `templates/auth/register.html` | 회원가입 폼, th:errors 에러 메시지 출력 |
| 21 | `templates/auth/login.html` | 로그인 폼, FlashAttribute 에러 메시지 |
| 22 | `templates/board/list.html` | 게시글 목록, 검색, 페이징 (th:each, #numbers.sequence) |
| 23 | `templates/board/detail.html` | 게시글 상세, 댓글 목록, 댓글 입력 폼 |
| 24 | `templates/board/write.html` | 게시글 작성, 파일 첨부 (enctype="multipart/form-data") |

### 8단계: 초기 데이터
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 25 | `DataInitializer.java` | CommandLineRunner, BCrypt 암호화, 테스트 데이터 생성 패턴 |

---

## 오늘 배운 핵심 기능

### 1. JPA 엔티티 설계와 연관관계

#### 엔티티란?
- 자바 클래스 하나 = DB 테이블 하나
- `@Entity` + `@Table(name = "...")` 로 매핑
- `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)` → AUTO_INCREMENT 기본키

#### 연관관계 매핑
```
Member (1) ──── (N) Post ──── (N) Comment
                 └──── (N) Attachment
```

| 어노테이션 | 의미 | 사용 위치 |
|-----------|------|----------|
| `@ManyToOne` | 다대일 (N:1) | Post, Comment, Attachment에서 Member/Post를 가리킬 때 |
| `@OneToMany(mappedBy=...)` | 일대다 (1:N) | Post에서 comments, attachments 컬렉션 선언 시 |
| `@JoinColumn(name="...")` | FK 컬럼명 지정 | @ManyToOne 필드에 함께 선언 |

#### 지연 로딩(Lazy Loading)
```java
@ManyToOne(fetch = FetchType.LAZY)  // 게시글 조회 시 회원 정보를 즉시 안 가져옴
```
- 실제로 `post.getMember().getNickname()` 을 호출하는 순간 쿼리 실행
- N+1 문제 → **페치 조인(JOIN FETCH)** 으로 해결

#### 영속성 전이(Cascade)와 고아 객체 제거
```java
@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Comment> comments = new ArrayList<>();
```
- `cascade = CascadeType.ALL`: 게시글 저장/삭제 시 댓글도 자동 저장/삭제
- `orphanRemoval = true`: 리스트에서 댓글을 제거하면 DB에서도 자동 DELETE

#### 생명주기 콜백
```java
@PrePersist   // DB INSERT 직전 자동 호출
public void onCreate() {
    this.createdAt = LocalDateTime.now();
}

@PreUpdate    // DB UPDATE 직전 자동 호출
public void onUpdate() {
    this.updatedAt = LocalDateTime.now();
}
```

---

### 2. JPA Repository — 데이터 접근 계층

#### JpaRepository 상속만 하면 CRUD 자동 제공
```java
public interface PostRepository extends JpaRepository<Post, Long> {
    // save(), findById(), findAll(), deleteById(), count() 등 기본 제공
}
```

#### @Query JPQL로 커스텀 쿼리 작성
```java
// 페치 조인으로 N+1 문제 해결 + 페이징 처리
@Query(value = "select p from Post p join fetch p.member order by p.id desc",
       countQuery = "select count(p) from Post p")
Page<Post> findAllWithPost(Pageable pageable);
```

#### 메서드명 기반 쿼리 (자동 JPQL 생성)
```java
// "findBy" + "PostId" + "OrderBy" + "CreatedAt" + "Asc"
// → WHERE post_id = ? ORDER BY created_at ASC
List<Comment> findByPostIdOrderByCreatedAtAsc(Long id);
```

---

### 3. @Transactional — 트랜잭션 처리

```java
@Service
@Transactional(readOnly = true)  // 클래스 전체: 읽기 전용 (성능 최적화)
public class PostService {

    @Transactional  // 이 메서드만 쓰기 트랜잭션으로 오버라이드
    public Post createPost(PostFormDTO form, Member loginMember) {
        // ...INSERT/UPDATE 작업...
    }
}
```

- **readOnly = true**: 변경 감지(Dirty Checking) 생략 → 성능 향상
- 예외 발생 시 자동 롤백 (RuntimeException 계열)
- 예외 없이 종료 시 자동 커밋

---

### 4. 페이징(Pagination)

```java
// 컨트롤러에서
Pageable pageable = PageRequest.of(page, size);  // 페이지 번호(0부터), 크기
Page<Post> list = postService.getPostList(keyword, pageable);

// Page 객체가 제공하는 메서드
list.isFirst()        // 첫 페이지 여부
list.isLast()         // 마지막 페이지 여부
list.getNumber()      // 현재 페이지 번호 (0부터)
list.getTotalPages()  // 전체 페이지 수
list.getSize()        // 한 페이지 크기
```

```html
<!-- Thymeleaf 페이징 버튼 -->
<li th:each="i : ${#numbers.sequence(0, postPage.totalPages - 1)}">
  <a th:href="@{/board(page=${i})}" th:text="${i + 1}"></a>
</li>
```

---

### 5. 세션(Session) 기반 인증

```java
// 로그인 성공 → 세션에 회원 정보 저장
session.setAttribute("loginMember", member);

// 로그아웃 → 세션 전체 무효화
session.invalidate();

// 컨트롤러 파라미터에서 세션 값 바로 꺼내기
@SessionAttribute(value = "loginMember", required = false) Member loginMember
// required = false → 세션 없어도 예외 없이 null 반환
```

```html
<!-- Thymeleaf에서 세션 접근 -->
<div th:if="${session.loginMember != null}">로그인 상태입니다</div>
<span th:text="${session.loginMember.nickname}"></span>
```

---

### 6. 파일 업로드 처리

```java
// HTML: <input type="file" name="files" multiple>
// 서버:
@RequestParam(value = "files", required = false) MultipartFile[] files

// 파일 저장 알고리즘
String originalName = file.getOriginalFilename();       // 원본 파일명
String extension = originalName.substring(originalName.lastIndexOf("."));  // 확장자
String storedName = UUID.randomUUID() + extension;      // 고유 파일명 생성
Files.copy(file.getInputStream(), uploadPath.resolve(storedName));  // 물리 복사
```

**UUID를 사용하는 이유**: 같은 이름의 파일이 업로드돼도 덮어쓰기가 일어나지 않게 하기 위해

---

### 7. 조회수 중복 방지 — HashSet 활용

```java
// 세션에 HashSet<Long>을 저장하여 이미 본 게시글 ID를 기억
HashSet<Long> pageList = (HashSet<Long>) session.getAttribute("pageList");
if (pageList == null) {
    pageList = new HashSet<>();
    session.setAttribute("pageList", pageList);
}
// add()가 true → 처음 보는 게시글 → 조회수 +1
// add()가 false → 이미 본 게시글 → 조회수 유지
if (pageList.add(id)) {
    postService.updateCount(id);
}
```

---

### 8. BCrypt 비밀번호 암호화

```java
PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

// 저장할 때
member.setPassword(passwordEncoder.encode("1234"));  // → "$2a$10$..." 형태

// 로그인 검증할 때
passwordEncoder.matches("입력한비밀번호", member.getPassword())  // true/false 반환
```

- **단방향 해시**: 암호화된 값으로 원래 비밀번호를 되돌릴 수 없음
- DB가 털려도 원본 비밀번호가 노출되지 않음

---

### 9. Thymeleaf 주요 문법

| 문법 | 설명 | 예시 |
|------|------|------|
| `th:text="${...}"` | 텍스트 출력 | `th:text="${post.title}"` |
| `th:utext="${...}"` | HTML 태그 포함 출력 | `th:utext="${post.content}"` |
| `th:href="@{...}"` | URL 링크 | `th:href="@{/board/{id}(id=${post.id})}"` |
| `th:each="item : ${list}"` | 반복문 | `th:each="post : ${postPage}"` |
| `th:if="${...}"` | 조건부 출력 | `th:if="${session.loginMember != null}"` |
| `th:errors="*{field}"` | 검증 에러 출력 | `th:errors="*{content}"` |
| `th:object="${...}"` | 폼 객체 바인딩 | `th:object="${commentForm}"` |
| `th:field="*{field}"` | 폼 필드 바인딩 | `th:field="*{content}"` |
| `th:replace="~{layout/base :: layout(...)"` | 레이아웃 삽입 | 공통 레이아웃 적용 |
| `#temporals.format(날짜, '패턴')` | 날짜 포맷 | `#temporals.format(post.createdAt, 'yyyy-MM-dd')` |
| `#numbers.sequence(시작, 끝)` | 숫자 시퀀스 생성 | `#numbers.sequence(0, totalPages-1)` |

---

### 10. 프로젝트 전체 흐름 요약

```
브라우저 요청
    ↓
Controller (URL 매핑, 세션 체크, DTO 바인딩)
    ↓
Service (비즈니스 로직, @Transactional)
    ↓
Repository (JpaRepository → SQL 자동 생성 또는 @Query)
    ↓
Database (MySQL)
    ↓
Service (엔티티 반환)
    ↓
Controller (Model에 데이터 담기)
    ↓
Thymeleaf Template (HTML 렌더링)
    ↓
브라우저 응답 (HTML 화면 출력)
```

---

## 실행 방법

1. MySQL에 `board_jpa_db` 데이터베이스 생성
   ```sql
   CREATE DATABASE board_jpa_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. `application.properties` 에서 DB 비밀번호 확인 (기본값: `12345678`)

3. 서버 실행 후 브라우저에서 `http://localhost:8888` 접속

4. 테스트 데이터가 필요하면 `DataInitializer.java` 의 `// @Component` 주석을 풀고 한 번 실행 후 다시 주석 처리

5. 테스트 계정: `sample1` / `1234` (총 5개 샘플 계정)

---

## 자주 나오는 에러와 원인

| 에러 | 원인 | 해결 |
|------|------|------|
| `LazyInitializationException` | 트랜잭션 밖에서 LAZY 로딩 시도 | `@Transactional` 범위 내에서 접근하거나 JOIN FETCH 사용 |
| `could not initialize proxy` | 위와 동일 | 위와 동일 |
| `N+1 쿼리` 발생 (콘솔에 SELECT 쿼리가 N번 찍힘) | LAZY 로딩 + 반복문에서 연관 객체 접근 | `JOIN FETCH` 또는 `@EntityGraph` 사용 |
| `BindException` | 폼 제출 값이 DTO 타입과 불일치 | DTO 타입 및 HTML name 속성 확인 |
| `EntityNotFoundException` | `findById().orElseThrow()` 에서 해당 ID 없음 | 존재하는 ID로 요청하는지 확인 |
