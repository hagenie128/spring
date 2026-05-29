# 4일차 실습 문제

> 실습 예제 코드 참고 경로: `../practice/di-basic/`

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 문제 1 | `problem01/` | ★★☆☆ | 생성자 주입 XML 완성 |
| 문제 2 | `problem02/` | ★★★☆ | Setter 주입 XML 완성 |
| 문제 3 | `problem03/` | ★★★☆ | 어노테이션 DI + `@Qualifier` |
| 문제 4 (도전) | `problem04/` | ★★★★ | 회원관리 서비스 DI 전체 구성 |

---

## 문제 1 — 생성자 주입 XML 완성 (★★☆☆)

**수정 파일**: `problem01/src/main/resources/applicationContext.xml`

`OrderService`, `OrderRepository`, `PointPolicy` 클래스가 이미 작성되어 있다.  
`applicationContext.xml`의 TODO를 채워 생성자 주입을 완성하라.

**요구사항**:
- `orderRepository` Bean 등록
- `pointPolicy` Bean 등록, 생성자 인자 `rate=5`
- `orderService` Bean 등록
- `orderService`에는 `orderRepository`, `pointPolicy`를 생성자 주입

**완성 확인**:
```text
[OrderRepository] 주문 저장: 김치볶음밥
[PointPolicy] 적립 포인트: 450점
[OrderService] 주문 금액: 9000원
```

---

## 문제 2 — Setter 주입 XML 완성 (★★★☆)

**수정 파일**: `problem02/src/main/resources/applicationContext.xml`

`CouponService`는 `CouponRepository`와 `couponPrefix`를 Setter로 주입받아야 한다.  
XML의 TODO를 완성하라.

**요구사항**:
- `couponRepository` Bean 등록
- `couponService` Bean 등록
- `couponPrefix="KIOSK"` 값 주입
- `couponRepository` 참조 주입

**완성 확인**:
```text
[CouponRepository] 쿠폰 저장: KIOSK-WELCOME-1000
[CouponService] 쿠폰 발급 완료
```

---

## 문제 3 — 어노테이션 DI + `@Qualifier` (★★★☆)

**수정 파일 4개**:
1. `KakaoMessageSender.java`
2. `SmsMessageSender.java`
3. `MessageService.java`
4. `applicationContext.xml`

**TODO 내용**:
- `KakaoMessageSender`에 `@Component("kakaoMessageSender")` 추가
- `SmsMessageSender`에 `@Component("smsMessageSender")` 추가
- `MessageService`에 `@Service` 추가
- `MessageService`의 필드에 `@Autowired`, `@Qualifier("kakaoMessageSender")` 추가
- XML에 `component-scan` 추가

**완성 확인**:
```text
[KakaoMessageSender] 픽업 준비가 완료되었습니다.
[MessageService] 메시지 전송 완료
```

---

## 문제 4 — 도전: 회원관리 서비스 DI 전체 구성 (★★★★)

XML과 어노테이션 DI를 함께 사용하여 회원가입 흐름을 완성하라.

**수정 파일**:
1. `MemberRepository.java` — 적절한 어노테이션 추가
2. `SmsNotifier.java` — 적절한 어노테이션과 Bean 이름 추가
3. `EmailNotifier.java` — 적절한 어노테이션과 Bean 이름 추가
4. `MemberService.java` — 생성자 주입 코드 완성
5. `applicationContext.xml` — `component-scan`, `passwordEncoder`, `memberService` Bean 설정 완성

**요구사항**:
- `MemberRepository`는 `@Repository`
- `SmsNotifier`는 `@Component("smsNotifier")`
- `EmailNotifier`는 `@Component("emailNotifier")`
- `MemberService`는 생성자로 `MemberRepository`, `PasswordEncoder`, `Notifier`를 주입받는다.
- `memberService`에는 `smsNotifier`를 주입한다.
- `PasswordEncoder`는 XML Bean으로 등록하고 생성자 값 `"SHA-256"`을 넣는다.

**예상 출력**:
```text
[PasswordEncoder] SHA-256 방식으로 비밀번호 암호화
[MemberRepository] 회원 저장: user01
[SmsNotifier] user01 가입 알림 전송
[MemberService] 회원가입 완료
```
