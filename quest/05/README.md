# 5일차 실습 문제

> 실습 예제 코드 참고 경로: `../practice/di-advanced/`

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 문제 1 | `problem01/` | ★★☆☆ | @Primary로 기본 구현체 지정 |
| 문제 2 | `problem02/` | ★★☆☆ | @Value + @PropertySource |
| 문제 3 | `problem03/` | ★★★☆ | Java Config (@Configuration + @Bean) |
| 문제 4 (도전) | `problem04/` | ★★★★ | Interface 교체 + Java Config 전체 구현 |

---

## 문제 1 — @Primary로 기본 구현체 지정 (★★☆☆)

**수정 파일**: `problem01/src/main/java/com/spring/problem01/EmailSender.java`

`NotificationSender` 인터페이스를 구현하는 `EmailSender`와 `SmsSender`가 이미 작성되어 있다.  
`AlertService`는 `@Qualifier` 없이 `NotificationSender`를 `@Autowired`로 주입받는다.  
`EmailSender.java`의 TODO를 채워 `@Primary`를 추가하라.

**요구사항**:
- `EmailSender`에 `@Primary` 어노테이션 추가
- `AlertService`는 수정 없이 `EmailSender`가 주입되어야 함

**예상 출력**:
```text
[EmailSender] 이메일 발송: 주문 완료
[AlertService] 알림 전송 완료
```

---

## 문제 2 — @Value + @PropertySource (★★☆☆)

**수정 파일**: `problem02/src/main/java/com/spring/problem02/KioskConfig.java`

`application.properties` 파일이 이미 작성되어 있다.  
`KioskConfig.java`의 TODO 3개를 채워 프로퍼티 값을 Bean 필드에 주입하라.

**요구사항**:
1. `@Component` 추가
2. `@PropertySource("classpath:application.properties")` 추가
3. `@Value`로 `kioskName`, `taxRate`, `currency` 필드 주입

**예상 출력**:
```text
[KioskConfig] 가게: 맛있는 키오스크 | 세율: 10% | 통화: KRW
```

---

## 문제 3 — Java Config (@Configuration + @Bean) (★★★☆)

**수정 파일**: `problem03/src/main/java/com/spring/problem03/AppConfig.java`

`CartRepository`, `DiscountCalculator`, `CartService` 클래스가 이미 작성되어 있다.  
`AppConfig.java`의 TODO를 채워 Java Config로 DI를 구성하라.

**요구사항**:
1. `AppConfig`에 `@Configuration` 추가
2. `@Bean cartRepository()` 메서드 작성
3. `@Bean discountCalculator()` 메서드 작성
4. `@Bean cartService()` 메서드 작성 — `cartRepository()`와 `discountCalculator()` 주입

**예상 출력**:
```text
[DiscountCalculator] 10% 할인 적용
[CartRepository] 저장: 불고기버거 x2
[CartService] 장바구니 합계: 21600원
```

---

## 문제 4 — 도전: Interface 교체 + Java Config (★★★★)

**수정 파일 2개**:
1. `problem04/src/main/java/com/spring/problem04/CashPayment.java`
2. `problem04/src/main/java/com/spring/problem04/CardPayment.java`
3. `problem04/src/main/java/com/spring/problem04/PaymentAppConfig.java`
4. `problem04/src/main/java/com/spring/problem04/ProblemMain.java`

`PaymentProcessor` 인터페이스와 `ReceiptPrinter`, `CheckoutService`가 이미 작성되어 있다.  
TODO를 채워 Java Config로 두 가지 결제 수단을 각각 주입한 `CheckoutService`를 구성하라.

**요구사항**:
- `CashPayment`와 `CardPayment`에 `@Component` 추가
- `PaymentAppConfig`에 `@Configuration` + `@ComponentScan` 추가
- `@Bean receiptPrinter()` 작성
- `@Bean checkoutServiceWithCard(CardPayment, ReceiptPrinter)` 작성
- `@Bean checkoutServiceWithCash(CashPayment, ReceiptPrinter)` 작성
- `ProblemMain`에서 두 가지 `CheckoutService`를 각각 실행

**예상 출력**:
```text
[CardPayment] 카드 결제: 25000원
[ReceiptPrinter] 영수증 발행: 카드결제완료
[CashPayment] 현금 결제: 10000원
[ReceiptPrinter] 영수증 발행: 현금결제완료
```
