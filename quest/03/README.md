# Day 03 실습 문제

## 주제: Bean 생명주기, Java Config, ApplicationContext

| 문제 | 주제 | 난이도 |
|------|------|--------|
| problem01 | XML init-method / destroy-method | ★★☆☆ |
| problem02 | @PostConstruct / @PreDestroy | ★★☆☆ |
| problem03 | @Configuration + @Bean + AnnotationConfigApplicationContext | ★★★☆ |
| problem04 | InitializingBean / DisposableBean — 도전 과제 | ★★★★ |

---

## problem01 — XML init-method / destroy-method ★★☆☆

### 시나리오
파일 처리 서비스(`FileHandler`)가 있습니다.  
컨테이너 시작 시 작업 디렉토리를 생성하고, 종료 시 임시 파일을 정리해야 합니다.

### TODO
`applicationContext.xml` 에서 `fileHandler` Bean에  
- `init-method="openDirectory"` 속성 추가  
- `destroy-method="cleanUp"` 속성 추가  

### 기대 출력
```
[FileHandler] 생성자 호출
[FileHandler] openDirectory() — /tmp/work 디렉토리 준비 완료
[FileHandler] 파일 처리: report.txt
▶ 컨테이너 종료
[FileHandler] cleanUp() — 임시 파일 삭제 완료
```

---

## problem02 — @PostConstruct / @PreDestroy ★★☆☆

### 시나리오
이메일 발송 서비스(`EmailConnectionPool`)가 커넥션 풀을 관리합니다.  
Bean 초기화 시 커넥션 3개를 미리 생성하고, 소멸 시 모두 닫아야 합니다.

### TODO
`EmailConnectionPool` 클래스에  
- `@PostConstruct` 메서드: `initPool()` — 커넥션 3개 생성 로그 출력  
- `@PreDestroy` 메서드: `closePool()` — 커넥션 3개 닫기 로그 출력  
- `@Component` 어노테이션 추가  

### 기대 출력
```
[EmailConnectionPool] 생성자 호출
[EmailConnectionPool] initPool() — 커넥션 3개 생성 완료
[EmailConnectionPool] 이메일 발송: to=admin@example.com
▶ 컨테이너 종료
[EmailConnectionPool] closePool() — 커넥션 3개 닫기 완료
```

---

## problem03 — @Configuration + @Bean ★★★☆

### 시나리오
`MemberService`와 `MemberRepository`를 Java Config 방식으로 등록합니다.  
`MemberService` 는 `MemberRepository`를 생성자로 주입받습니다.  
`AnnotationConfigApplicationContext`로 컨테이너를 생성하세요.

### TODO
1. `AppConfig` 클래스에 `@Configuration` 추가  
2. `memberRepository()` 메서드에 `@Bean` 추가  
3. `memberService()` 메서드에 `@Bean` 추가 (memberRepository를 생성자 인자로 전달)  
4. `ProblemMain` — `AnnotationConfigApplicationContext(AppConfig.class)` 로 컨테이너 생성  
5. `ProblemMain` — `memberService` Bean 획득 후 `register("홍길동")` 호출  

### 기대 출력
```
[MemberRepository] 생성
[MemberService] 생성 — MemberRepository 주입됨
[MemberService] register("홍길동") → [MemberRepository] save("홍길동") 완료
```

---

## problem04 — InitializingBean / DisposableBean 도전 ★★★★

### 시나리오
데이터베이스 커넥션 풀(`ConnectionPool`)을 구현합니다.  
- `InitializingBean.afterPropertiesSet()`: 풀 크기만큼 커넥션 생성  
- `DisposableBean.destroy()`: 모든 커넥션 닫기  
- XML에서 `poolSize` 프로퍼티 주입 (setter injection)  
- `getConnection()` / `releaseConnection()` 메서드 구현  

### 조건
- `poolSize` 기본값 3, XML에서 5로 오버라이드  
- `afterPropertiesSet()`에서 poolSize 개수만큼 커넥션 ID 생성 (`conn-1` ~ `conn-N`)  
- `getConnection()`은 사용 가능한 커넥션이 없으면 "풀 고갈" 메시지 출력  

### 기대 출력
```
[ConnectionPool] 생성자 — poolSize=3(기본값)
[ConnectionPool] setPoolSize(5) — XML 주입
[ConnectionPool] afterPropertiesSet() — 커넥션 5개 생성: [conn-1, conn-2, conn-3, conn-4, conn-5]
[ConnectionPool] getConnection() → conn-1
[ConnectionPool] getConnection() → conn-2
[ConnectionPool] releaseConnection(conn-1) — 반납 완료
▶ 컨테이너 종료
[ConnectionPool] destroy() — 커넥션 5개 닫기 완료
```
