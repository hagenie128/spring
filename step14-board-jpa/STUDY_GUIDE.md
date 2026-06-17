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
| 6-1 | `entity/ReactionType.java` | 좋아요/싫어요 값을 Enum으로 제한 |
| 6-2 | `entity/PostReaction.java` | 게시글 좋아요/싫어요 테이블 설계 |
| 6-3 | `entity/CommentReaction.java` | 댓글 좋아요/싫어요 테이블 설계 |

### 3단계: Repository — DB 접근 계층
| 순서 | 파일 | 핵심 내용 |
|------|------|-----------|
| 7 | `repository/PostRepository.java` | @Query JPQL, 페치 조인, 페이징(Page), N+1 문제 해결 |
| 8 | `repository/CommentRepository.java` | @Query vs 메서드명 기반 쿼리 두 가지 방법 비교 |
| 8-1 | `repository/PostReactionRepository.java` | 게시글 반응 CRUD 준비 |
| 8-2 | `repository/CommentReactionRepository.java` | 댓글 반응 CRUD 준비 |

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
| 15-1 | `service/PostReactionService.java` | 게시글 반응 로직을 넣기 위한 서비스 뼈대 |
| 15-2 | `service/CommentReactionService.java` | 댓글 반응 로직을 넣기 위한 서비스 뼈대 |

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

### 0. 오늘 추가된 코드와 역할 한눈에 보기

오늘 프로젝트는 단순 게시판에서 한 단계 확장해서 **회원, 게시글, 댓글, 첨부파일, 반응 기능의 테이블 설계**까지 이어지는 구조입니다. 아직 화면에서 좋아요/싫어요 버튼을 누르는 전체 로직은 완성 전이고, 반응 관련 코드는 DB 설계와 확장 준비 단계로 보면 됩니다.

| 영역 | 추가/사용 파일 | 역할 |
|------|----------------|------|
| 실행 진입점 | `Step14BoardJpaApplication.java` | Spring Boot 애플리케이션 시작 클래스 |
| 의존성 | `build.gradle` | JPA, Thymeleaf, Validation, Web, BCrypt, MySQL, Lombok 사용 설정 |
| 환경 설정 | `application.properties` | DB 연결, JPA 테이블 생성 전략, 서버 포트, 파일 업로드 크기/경로 설정 |
| 회원 | `Member`, `MemberDTO`, `MemberRepository`, `MemberService`, `AuthController` | 회원가입, 로그인, 로그아웃, 비밀번호 암호화, 세션 저장 |
| 게시글 | `Post`, `PostFormDTO`, `PostRepository`, `PostService`, `PostController` | 글 목록/검색/상세/작성/삭제, 조회수 증가, 페이징 |
| 댓글 | `Comment`, `CommentFormDTO`, `CommentRepository`, `CommentService`, `CommentController` | 게시글별 댓글 조회, 댓글 등록, 댓글 삭제 |
| 첨부파일 | `Attachment`, `AttachmentRepository`, `AttachmentService` | 업로드 파일을 서버 디스크에 저장하고 DB에는 파일 정보만 저장 |
| 반응 | `ReactionType`, `PostReaction`, `CommentReaction`, 각 ReactionRepository/Service | 좋아요/싫어요 기능을 위한 테이블과 서비스 구조 준비 |
| 화면 | `templates/layout`, `templates/auth`, `templates/board` | Thymeleaf로 서버에서 HTML 생성 |
| 스타일 | `static/css/board.css` | 게시판 화면 스타일 |
| 테스트 데이터 | `DataInitializer.java` | 샘플 회원/게시글/댓글/첨부파일 데이터 생성 |

#### 전체 요청 흐름

```text
브라우저
  -> Controller: URL 요청을 받음
  -> DTO: 폼 데이터를 담고 검증함
  -> Service: 실제 업무 규칙을 처리함
  -> Repository: DB에 접근함
  -> Entity: DB 테이블과 매핑되는 객체
  -> Thymeleaf: 서버 데이터를 HTML로 렌더링
  -> 브라우저
```

#### 왜 이렇게 계층을 나누는가?

| 계층 | 맡는 일 | 여기에 두면 좋은 코드 |
|------|---------|----------------------|
| Controller | 요청/응답 처리 | URL 매핑, 로그인 체크, Model에 데이터 담기, redirect |
| DTO | 화면 입력값 운반 | 제목/내용/댓글/회원가입 입력값, `@NotBlank`, `@Size` |
| Service | 핵심 로직 처리 | 회원 중복 확인, 비밀번호 암호화, 글 저장, 조회수 증가, 파일 저장 |
| Repository | DB 접근 | `findById`, `save`, `deleteById`, 검색 쿼리 |
| Entity | DB 테이블 구조 | 컬럼, PK, FK, 연관관계, 생성/수정 시간 자동 세팅 |

---

### 0-1. 설정 파일에 추가된 코드 의미

#### `build.gradle`

| 코드 | 의미 |
|------|------|
| `spring-boot-starter-web` | Controller, URL 매핑, 내장 톰캣, MVC 기능 사용 |
| `spring-boot-starter-thymeleaf` | 서버에서 HTML 템플릿을 렌더링 |
| `spring-boot-starter-data-jpa` | Entity, Repository, JpaRepository, JPQL 사용 |
| `spring-boot-starter-validation` | DTO의 `@NotBlank`, `@Size` 같은 검증 어노테이션 사용 |
| `spring-security-crypto` | 전체 Spring Security 로그인 기능은 쓰지 않고, BCrypt 비밀번호 암호화 기능만 사용 |
| `mysql-connector-j` | Spring Boot가 MySQL DB에 접속할 수 있게 해주는 드라이버 |
| `lombok` | `@Getter`, `@Setter`, `@NoArgsConstructor` 등 반복 코드를 자동 생성 |
| `options.compilerArgs << '-parameters'` | 컨트롤러 파라미터 이름 인식을 안정적으로 하도록 컴파일 옵션 추가 |

#### `application.properties`

| 설정 | 의미 |
|------|------|
| `server.port=8888` | 애플리케이션 실행 포트를 8888로 변경 |
| `spring.datasource.url` | 접속할 MySQL DB 주소와 DB 이름 지정 |
| `spring.datasource.username/password` | DB 접속 계정 |
| `spring.jpa.hibernate.ddl-auto=create` | 실행할 때마다 기존 테이블을 지우고 새로 생성. 학습용으로는 편하지만 데이터가 매번 삭제됨 |
| `spring.jpa.show-sql=true` | JPA가 실행하는 SQL을 콘솔에 출력 |
| `spring.jpa.properties.hibernate.format_sql=true` | SQL을 줄바꿈해서 보기 좋게 출력 |
| `spring.thymeleaf.cache=false` | HTML 수정 후 서버 재시작 없이 변경 확인하기 쉽게 캐시 끔 |
| `spring.servlet.multipart.max-file-size=10MB` | 파일 1개 최대 업로드 크기 |
| `spring.servlet.multipart.max-request-size=100MB` | 요청 1번에 포함될 수 있는 전체 업로드 용량 |
| `app.upload.dir=uploads` | 실제 파일이 저장될 서버 폴더 |

> 복습 포인트: `ddl-auto=create`는 실행할 때마다 테이블을 새로 만들기 때문에, 저장된 게시글/회원이 사라지는 것이 정상입니다. 데이터를 유지하려면 `update`로 바꿉니다.

---

### 0-2. 사용된 코드 상세 해설

#### 1. Spring Boot 시작 코드

```java
@SpringBootApplication
public class Step14BoardJpaApplication {
  public static void main(String[] args) {
    SpringApplication.run(Step14BoardJpaApplication.class, args);
  }
}
```

| 코드 | 의미 |
|------|------|
| `@SpringBootApplication` | Spring Boot 시작 설정. 내부적으로 `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan` 역할을 함께 함 |
| `SpringApplication.run(...)` | 내장 톰캣을 띄우고, Controller/Service/Repository 같은 Bean을 찾아 등록한 뒤 애플리케이션 실행 |
| `ComponentScan` | `com.spring` 패키지 아래의 `@Controller`, `@Service`, `@Repository`, `@Component`를 자동으로 찾음 |

---

#### 2. DTO 검증 코드

DTO는 HTML 폼에서 넘어온 값을 Entity에 바로 넣지 않고, 중간에서 검증하고 운반하는 객체입니다.

```java
@NotBlank(message = "제목을 입력해주세요.")
@Size(max = 200, message = "제목은 200자 이하로 입력해 주세요.")
private String title;
```

| 코드 | 의미 |
|------|------|
| `@NotBlank` | `null`, 빈 문자열, 공백 문자열을 허용하지 않음 |
| `@Size(max = 200)` | 글자 수 제한. DB 컬럼 길이와 맞춰주면 안전함 |
| `message` | 검증 실패 시 Thymeleaf의 `th:errors`로 출력할 메시지 |

컨트롤러에서는 이렇게 검증합니다.

```java
public String postNew(@Valid @ModelAttribute("form") PostFormDTO form,
    BindingResult bindingResult) {
  if (bindingResult.hasErrors()) {
    return "board/write";
  }
}
```

| 코드 | 의미 |
|------|------|
| `@Valid` | DTO의 검증 어노테이션을 실행 |
| `@ModelAttribute("form")` | HTML form 데이터를 `PostFormDTO` 객체에 바인딩하고, 뷰에서도 `form` 이름으로 사용 |
| `BindingResult` | 검증 실패 정보를 담는 객체 |
| `bindingResult.hasErrors()` | 입력값 오류가 있으면 DB 저장하지 않고 다시 폼 화면으로 이동 |

> 중요: `BindingResult`는 반드시 `@Valid`가 붙은 파라미터 바로 뒤에 와야 합니다. 사이에 다른 파라미터가 끼면 검증 에러를 제대로 받지 못할 수 있습니다.

---

#### 3. Entity 기본 코드

```java
@Entity
@Table(name = "post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
```

| 코드 | 의미 |
|------|------|
| `@Entity` | 이 클래스가 DB 테이블과 매핑되는 JPA 엔티티임을 표시 |
| `@Table(name = "post")` | 실제 DB 테이블명을 `post`로 지정 |
| `@Id` | 기본키(PK) 컬럼 |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | MySQL의 AUTO_INCREMENT 방식으로 PK 자동 증가 |

```java
@Column(nullable = false, columnDefinition = "TEXT")
private String content;
```

| 코드 | 의미 |
|------|------|
| `nullable = false` | DB 컬럼에 `NOT NULL` 제약 추가 |
| `columnDefinition = "TEXT"` | 긴 게시글 본문을 저장하기 위해 DB 컬럼 타입을 TEXT로 지정 |

---

#### 4. 연관관계 코드

게시글 여러 개는 회원 한 명이 작성할 수 있으므로 `Post -> Member`는 N:1입니다.

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "member_id", nullable = false)
private Member member;
```

| 코드 | 의미 |
|------|------|
| `@ManyToOne` | 여러 게시글이 한 회원을 참조하는 다대일 관계 |
| `fetch = FetchType.LAZY` | 연관된 회원을 당장 조회하지 않고, 실제 사용할 때 조회 |
| `@JoinColumn(name = "member_id")` | `post` 테이블에 생길 FK 컬럼명 지정 |

게시글 하나에는 댓글 여러 개가 달릴 수 있으므로 `Post -> Comment`는 1:N입니다.

```java
@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Comment> comments = new ArrayList<>();
```

| 코드 | 의미 |
|------|------|
| `@OneToMany` | 게시글 하나가 댓글 여러 개를 가짐 |
| `mappedBy = "post"` | FK 관리는 `Comment` 엔티티의 `post` 필드가 담당한다는 뜻 |
| `cascade = CascadeType.ALL` | 게시글 삭제 시 댓글도 함께 삭제 |
| `orphanRemoval = true` | 부모와 연결이 끊긴 댓글을 DB에서도 삭제 |
| `new ArrayList<>()` | NullPointerException 방지를 위해 컬렉션을 미리 초기화 |

---

#### 5. 생성/수정 시간 자동 입력

```java
@PrePersist
public void onCreate() {
  this.createdAt = LocalDateTime.now();
  this.updatedAt = LocalDateTime.now();
}

@PreUpdate
public void onUpdate() {
  this.updatedAt = LocalDateTime.now();
}
```

| 코드 | 실행 시점 | 역할 |
|------|-----------|------|
| `@PrePersist` | INSERT 직전 | 최초 생성일, 수정일 자동 입력 |
| `@PreUpdate` | UPDATE 직전 | 수정일 자동 갱신 |

> 그래서 컨트롤러나 서비스에서 `createdAt`을 직접 세팅하지 않아도 DB 저장 직전에 자동으로 값이 들어갑니다.

---

#### 6. Repository 코드

```java
public interface PostRepository extends JpaRepository<Post, Long> {
}
```

| 코드 | 의미 |
|------|------|
| `JpaRepository<Post, Long>` | `Post` 엔티티를 다루고, PK 타입은 `Long`이라는 뜻 |
| `save()` | INSERT 또는 UPDATE |
| `findById()` | PK로 단건 조회 |
| `findAll()` | 전체 조회 |
| `deleteById()` | PK로 삭제 |
| `count()` | 전체 개수 조회 |

커스텀 쿼리는 `@Query`로 직접 작성합니다.

```java
@Query(value = "select p from Post p join fetch p.member order by p.id desc",
       countQuery = "select count(p) from Post p")
Page<Post> findAllWithPost(Pageable pageable);
```

| 코드 | 의미 |
|------|------|
| `select p from Post p` | SQL 테이블명이 아니라 Entity 이름 기준으로 조회 |
| `join fetch p.member` | 게시글과 작성자 회원을 한 번에 가져와 N+1 문제 방지 |
| `order by p.id desc` | 최신 글이 먼저 보이도록 정렬 |
| `Page<Post>` | 목록 데이터 + 전체 페이지 수 + 현재 페이지 정보까지 담는 객체 |
| `Pageable` | 몇 페이지, 몇 개씩 가져올지 담는 객체 |
| `countQuery` | 페이징에 필요한 전체 데이터 개수를 따로 조회 |

검색 쿼리입니다.

```java
@Query(value = "select p from Post p join fetch p.member m "
    + "where p.title like %:keyword% or m.nickname like %:keyword% "
    + "order by p.id desc")
Page<Post> searchWithPost(String keyword, Pageable pageable);
```

| 코드 | 의미 |
|------|------|
| `:keyword` | 메서드 파라미터 `keyword` 값이 들어갈 자리 |
| `like %:keyword%` | 검색어가 일부만 포함돼도 조회 |
| `m.nickname` | 작성자 닉네임으로도 검색 |

---

#### 7. Service 코드

서비스는 컨트롤러가 직접 DB를 만지지 않도록 중간에서 업무 로직을 처리합니다.

```java
@Service
@Transactional(readOnly = true)
public class PostService {
}
```

| 코드 | 의미 |
|------|------|
| `@Service` | 스프링 Bean으로 등록. 비즈니스 로직 계층임을 표시 |
| `@Transactional(readOnly = true)` | 기본은 조회 전용 트랜잭션으로 실행 |

쓰기 작업은 메서드에 따로 `@Transactional`을 붙입니다.

```java
@Transactional
public Post createPost(PostFormDTO form, Member loginMember) {
  Post post = new Post();
  post.setTitle(form.getTitle());
  post.setContent(form.getContent());
  post.setMember(loginMember);
  postRepository.save(post);
  return post;
}
```

| 코드 | 의미 |
|------|------|
| `new Post()` | DB에 저장할 엔티티 생성 |
| `form.getTitle()` | 화면에서 입력한 제목을 DTO에서 꺼냄 |
| `post.setMember(loginMember)` | 현재 로그인 회원을 작성자로 연결 |
| `postRepository.save(post)` | INSERT 실행 |
| `return post` | 저장 후 생성된 게시글 ID를 사용할 수 있게 반환 |

조회수 증가는 JPA 변경 감지를 이용합니다.

```java
@Transactional
public void updateCount(Long id) {
  Post post = findById(id);
  post.setViewCount(post.getViewCount() + 1);
}
```

| 코드 | 의미 |
|------|------|
| `findById(id)` | 영속 상태의 Post 엔티티 조회 |
| `setViewCount(...)` | 엔티티 값 변경 |
| 별도 `save()` 없음 | 트랜잭션 종료 시 JPA Dirty Checking으로 UPDATE 자동 실행 |

---

#### 8. 회원가입/로그인 코드

```java
private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
```

| 코드 | 의미 |
|------|------|
| `PasswordEncoder` | 비밀번호 암호화/검증 인터페이스 |
| `BCryptPasswordEncoder` | BCrypt 해시 알고리즘 구현체 |
| `(10)` | 암호화 강도. 높을수록 안전하지만 느려짐 |

```java
if (memberRepository.existsByUsername(form.getUsername())) {
  throw new IllegalArgumentException("중복된 아이디 입니다.");
}
```

| 코드 | 의미 |
|------|------|
| `existsByUsername` | 해당 아이디가 이미 있는지 true/false 조회 |
| `throw new IllegalArgumentException` | 가입 불가 상황을 예외로 알림 |
| 컨트롤러의 `catch` | 예외 메시지를 화면 에러로 보여줌 |

```java
member.setPassword(passwordEncoder.encode(form.getPassword()));
```

- 사용자가 입력한 평문 비밀번호를 DB에 그대로 저장하지 않습니다.
- BCrypt로 해시한 문자열만 저장합니다.

```java
passwordEncoder.matches(password, member.getPassword())
```

- 첫 번째 값: 사용자가 로그인 폼에 입력한 평문 비밀번호
- 두 번째 값: DB에 저장된 BCrypt 해시 문자열
- 둘이 같은 비밀번호인지 검증해서 true/false를 반환합니다.

---

#### 9. Controller 코드

```java
@Controller
@RequestMapping("/board")
public class PostController {
}
```

| 코드 | 의미 |
|------|------|
| `@Controller` | HTML 화면을 반환하는 MVC 컨트롤러 |
| `@RequestMapping("/board")` | 이 클래스의 모든 URL 앞에 `/board`가 붙음 |

```java
@GetMapping
public ModelAndView list(ModelAndView view,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
}
```

| 코드 | 의미 |
|------|------|
| `@GetMapping` | GET `/board` 요청 처리 |
| `@RequestParam` | URL 쿼리스트링 값을 받음. 예: `/board?page=1&keyword=java` |
| `defaultValue` | 값이 없을 때 기본값 사용 |
| `ModelAndView` | 화면 이름과 화면에 넘길 데이터를 함께 담음 |

```java
@SessionAttribute(value = "loginMember", required = false) Member loginMember
```

| 코드 | 의미 |
|------|------|
| `@SessionAttribute` | 세션에 저장된 값을 컨트롤러 파라미터로 바로 받음 |
| `value = "loginMember"` | 로그인 성공 시 저장했던 세션 key |
| `required = false` | 로그인하지 않아도 예외 대신 null 받음 |

```java
return "redirect:/auth/login";
```

- 서버에서 HTML을 렌더링하는 것이 아니라, 브라우저에게 `/auth/login`으로 다시 요청하라고 지시합니다.
- 로그인 필요 화면에서 자주 사용합니다.

---

#### 10. 파일 업로드 코드

```java
@RequestParam(value = "files", required = false) MultipartFile[] files
```

| 코드 | 의미 |
|------|------|
| `MultipartFile` | 업로드된 파일 1개를 표현하는 Spring 객체 |
| `MultipartFile[]` | multiple 업로드라 여러 파일을 배열로 받음 |
| `required = false` | 파일 첨부 없이 글만 작성해도 허용 |

```java
String originalName = file.getOriginalFilename();
String storedName = UUID.randomUUID() + extension;
Files.copy(file.getInputStream(), uploadPath.resolve(storedName));
```

| 코드 | 의미 |
|------|------|
| `getOriginalFilename()` | 사용자가 업로드한 원래 파일명 |
| `UUID.randomUUID()` | 중복 가능성이 거의 없는 저장 파일명 생성 |
| `getInputStream()` | 업로드된 임시 파일의 내용을 읽는 스트림 |
| `Files.copy(...)` | 실제 서버 폴더에 파일 복사 |

DB에는 파일 자체가 아니라 파일 정보만 저장합니다.

```java
Attachment attachment = new Attachment();
attachment.setOriginalName(originalName);
attachment.setStoredName(storedName);
attachment.setFileSize(file.getSize());
attachment.setPost(post);
attachmentRepository.save(attachment);
```

| 필드 | 저장 이유 |
|------|-----------|
| `originalName` | 다운로드/화면 표시용 원본 이름 |
| `storedName` | 서버에 실제 저장된 고유 이름 |
| `fileSize` | 파일 크기 표시/관리 |
| `post` | 어떤 게시글의 첨부파일인지 연결 |

---

#### 11. 조회수 중복 방지 코드

```java
HashSet<Long> pageList = (HashSet<Long>) session.getAttribute("pageList");
if (pageList == null) {
  pageList = new HashSet<Long>();
  session.setAttribute("pageList", pageList);
}
if (pageList.add(id)) {
  postService.updateCount(id);
}
```

| 코드 | 의미 |
|------|------|
| `session.getAttribute("pageList")` | 이 브라우저가 이미 본 게시글 ID 목록을 꺼냄 |
| `HashSet<Long>` | 중복을 허용하지 않는 자료구조 |
| `pageList == null` | 처음 상세 페이지에 들어온 세션이면 새 Set 생성 |
| `pageList.add(id)` | 처음 보는 ID면 true, 이미 있으면 false |
| `updateCount(id)` | true일 때만 조회수 1 증가 |

> 이 방식은 브라우저 세션 기준입니다. 다른 브라우저나 세션이 만료된 뒤에는 다시 조회수가 올라갑니다.

---

#### 12. 삭제 코드

게시글 삭제는 DB 데이터만 지우면 서버 폴더에 실제 파일이 남습니다. 그래서 물리 파일 삭제와 DB 삭제를 같이 처리합니다.

```java
List<Attachment> fileList = attachmentService.getAttachmentByPost(id);
Path rootPath = Paths.get(uploadDir).toAbsolutePath();
for (Attachment att : fileList) {
  rootPath.resolve(att.getStoredName()).toFile().delete();
}
postService.deleteById(id);
```

| 코드 | 의미 |
|------|------|
| `getAttachmentByPost(id)` | 게시글에 연결된 첨부파일 목록 조회 |
| `Paths.get(uploadDir).toAbsolutePath()` | 업로드 폴더의 절대 경로 계산 |
| `resolve(att.getStoredName())` | 업로드 폴더 + 저장 파일명 결합 |
| `.delete()` | 서버 디스크의 실제 파일 삭제 |
| `postService.deleteById(id)` | DB에서 게시글 삭제 |

> `Post` 엔티티의 `cascade = CascadeType.ALL` 때문에 게시글을 삭제하면 댓글과 첨부파일 메타데이터도 함께 삭제됩니다. 단, 실제 파일은 DB가 아니라 디스크에 있으므로 직접 삭제해야 합니다.

---

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

### 9. 좋아요/싫어요 반응 기능 준비 코드

이번 프로젝트에는 반응 기능을 위한 파일이 추가되어 있지만, 현재 서비스 로직은 아직 비어 있습니다. 즉 **테이블 설계와 Repository 준비까지 된 상태**입니다.

#### `ReactionType.java`

```java
public enum ReactionType {
  LIKE, DISLIKE
}
```

- 문자열을 아무거나 저장하지 못하게 `LIKE`, `DISLIKE` 두 값으로 제한합니다.
- `"좋아요"`, `"싫어요"`, `"like"`, `"LIKE"`처럼 제각각 저장되는 문제를 막습니다.

#### `PostReaction.java`

```java
@Table(name = "post_reaction",
       uniqueConstraints = @UniqueConstraint(columnNames = { "member_id", "post_id" }))
```

- 게시글 반응 정보를 저장하는 엔티티입니다.
- `member_id`: 누가 눌렀는지
- `post_id`: 어떤 게시글에 눌렀는지
- `type`: `LIKE` 또는 `DISLIKE`
- `uniqueConstraints`: 한 회원이 같은 게시글에 반응을 여러 번 남기지 못하게 막습니다.

#### `CommentReaction.java`

```java
@Table(name = "comment_reaction",
       uniqueConstraints = @UniqueConstraint(columnNames = { "member_id", "comment_id" }))
```

- 댓글 반응 정보를 저장하는 엔티티입니다.
- 구조는 `PostReaction`과 거의 같고, 대상이 게시글이 아니라 댓글입니다.

#### `@Enumerated(EnumType.STRING)`을 쓰는 이유

```java
@Enumerated(EnumType.STRING)
private ReactionType type;
```

- Enum을 DB에 저장할 때 `LIKE`, `DISLIKE` 문자열로 저장합니다.
- `EnumType.ORDINAL`은 0, 1 같은 숫자로 저장하므로 나중에 Enum 순서가 바뀌면 데이터 의미가 깨질 수 있습니다.

#### 현재 비어 있는 Service의 의미

```java
@Service
public class PostReactionService {
}
```

- 아직 좋아요/싫어요 클릭 로직은 구현되지 않았습니다.
- 나중에 여기에 다음 로직이 들어갈 수 있습니다.

```text
1. 로그인 회원 확인
2. 게시글 또는 댓글 조회
3. 기존 반응이 있는지 조회
4. 기존 반응이 없으면 새로 저장
5. 같은 버튼을 다시 누르면 반응 취소
6. 반대 버튼을 누르면 LIKE <-> DISLIKE 변경
```

---

### 10. 컨트롤러별 로직 의미

#### `AuthController`

| 메서드 | URL | 역할 |
|--------|-----|------|
| `registerForm` | `GET /auth/register` | 회원가입 화면 보여주기 |
| `register` | `POST /auth/register` | 입력값 검증, 중복 체크, 비밀번호 암호화 후 회원 저장 |
| `loginForm` | `GET /auth/login` | 로그인 화면 보여주기 |
| `login` | `POST /auth/login` | 아이디/비밀번호 검증 후 세션에 `loginMember` 저장 |
| `logout` | `GET /auth/logout` | 세션 무효화로 로그아웃 처리 |

#### `PostController`

| 메서드 | URL | 역할 |
|--------|-----|------|
| `list` | `GET /board` | 게시글 목록, 검색, 페이징 처리 |
| `postForm` | `GET /board/new` | 로그인한 사용자에게 글쓰기 화면 제공 |
| `postNew` | `POST /board/new` | 게시글 저장 + 첨부파일 저장 |
| `detail` | `GET /board/{id}` | 게시글 상세, 댓글, 첨부파일 조회 + 조회수 증가 |
| `delete` | `GET /board/{id}/delete` | 작성자 본인 확인 후 물리 파일 삭제 + DB 삭제 |

#### `CommentController`

| 메서드 | URL | 역할 |
|--------|-----|------|
| `addComment` | `POST /comments/post/{id}` | 로그인 확인 후 댓글 저장 |
| `delete` | `GET /comments/{id}/delete` | 댓글 작성자 본인 확인 후 댓글 삭제 |

---

### 11. Thymeleaf 주요 문법

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

### 12. 프로젝트 전체 흐름 요약

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
