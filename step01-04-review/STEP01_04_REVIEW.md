# Spring Step01~04 복습 정리

이 문서는 `step01-hello-spring`부터 `step04-di`까지 배운 내용을 수업 후 다시 따라가기 좋게 정리한 복습 노트입니다. 마지막에는 전체 개념을 한 번에 확인할 수 있는 총정리 예제 `step01-04-review`를 추가했습니다.

## 1. Step01: Spring 시작과 Bean 등록

### 핵심 개념

- Spring은 객체를 직접 `new`로 만들 수도 있지만, 설정 파일에 등록하면 컨테이너가 객체를 생성하고 관리한다.
- Spring 컨테이너는 보통 `ApplicationContext`로 사용한다.
- XML 설정 파일에서 `<bean>` 태그로 객체를 등록할 수 있다.
- `id`는 bean의 이름이고, `class`는 생성할 클래스의 전체 경로다.

### 기억할 코드 흐름

```java
ApplicationContext context =
        new ClassPathXmlApplicationContext("applicationContext.xml");

MessageBean messageBean = context.getBean("messageBean", MessageBean.class);
messageBean.sayHello();
```

### 복습 질문

- `ApplicationContext`는 어떤 역할을 하나?
- XML의 `<bean id="..." class="...">`에서 `id`와 `class`는 각각 무엇을 의미하나?
- 직접 `new MessageBean()` 하는 방식과 Spring에서 `getBean()` 하는 방식의 차이는 무엇인가?

## 2. Step02: IoC, 의존 객체 주입, Scope

### 핵심 개념

- IoC(Inversion of Control)는 객체 생성과 연결 책임을 개발자 코드가 아니라 Spring 컨테이너가 맡는다는 뜻이다.
- XML에서 `<property>`를 사용하면 setter로 값을 주입할 수 있다.
- `<constructor-arg>`를 사용하면 생성자로 값을 주입할 수 있다.
- `singleton` scope는 컨테이너 안에서 객체를 하나만 만든다.
- `prototype` scope는 `getBean()`을 호출할 때마다 새 객체를 만든다.

### XML 주입 방식 예시

```xml
<bean id="bookBean" class="xml.BookBean">
    <property name="title" value="Spring 입문"/>
</bean>
```

### Scope 비교

```java
CounterBean counter1 = context.getBean(CounterBean.class);
CounterBean counter2 = context.getBean(CounterBean.class);

System.out.println(counter1 == counter2);
```

- `singleton`: `true`
- `prototype`: `false`

### 복습 질문

- IoC를 사용하면 객체 생성 코드가 어디로 이동하나?
- `singleton`과 `prototype`은 언제 객체가 만들어지는가?
- setter 주입과 생성자 주입은 어떤 차이가 있는가?

## 3. Step03: Bean 생명주기와 Java Config

### 핵심 개념

Spring bean은 보통 다음 순서로 동작한다.

1. 객체 생성
2. 의존성 주입
3. 초기화 callback 실행
4. 사용
5. 컨테이너 종료 시 소멸 callback 실행

### 생명주기 callback 방식

| 방식 | 예시 | 특징 |
| --- | --- | --- |
| XML 지정 | `init-method`, `destroy-method` | 클래스가 Spring 인터페이스를 몰라도 된다 |
| 인터페이스 구현 | `InitializingBean`, `DisposableBean` | Spring 전용 인터페이스에 의존한다 |
| 어노테이션 | `@PostConstruct`, `@PreDestroy` | 가장 자주 쓰는 간단한 방식 |

### Java Config

XML 대신 Java 클래스로 bean을 등록할 수 있다.

```java
@Configuration
public class AppConfig {

    @Bean
    public ReportService reportService() {
        return new ReportService("월간");
    }
}
```

실행할 때는 `AnnotationConfigApplicationContext`를 사용한다.

```java
AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);
```

### 복습 질문

- `@Configuration`과 `@Bean`은 각각 어떤 역할을 하나?
- `@ComponentScan`은 무엇을 찾아서 등록하나?
- `prototype` bean은 왜 `@PreDestroy`가 자동으로 호출되지 않을까?

## 4. Step04: DI 방식 3가지

### 생성자 주입

```java
public OrderService(OrderRepository orderRepository, DiscountPolicy discountPolicy) {
    this.orderRepository = orderRepository;
    this.discountPolicy = discountPolicy;
}
```

- 필요한 의존성을 객체 생성 시점에 반드시 받는다.
- `final` 필드와 잘 어울린다.
- 실무에서 가장 권장되는 방식이다.

### Setter 주입

```java
public void setMenuRepository(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
}
```

- bean 생성 후 setter 메서드로 주입한다.
- 선택적으로 바꿀 수 있는 값에 어울린다.

### 필드 주입

```java
@Autowired
@Qualifier("smsSender")
private MessageSender messageSender;
```

- 코드가 짧지만 테스트와 변경에 불리할 수 있다.
- 같은 타입 bean이 여러 개면 `@Qualifier`로 어떤 bean을 쓸지 지정한다.

### 복습 질문

- 생성자 주입이 setter/field 주입보다 권장되는 이유는 무엇인가?
- 같은 타입의 bean이 2개 이상이면 Spring은 어떤 문제가 생기나?
- `@Qualifier("smsSender")`는 어떤 bean을 선택하라는 뜻인가?

## 5. 오늘 내용 한 장 요약

| 주제 | 한 줄 정리 |
| --- | --- |
| Bean | Spring 컨테이너가 생성하고 관리하는 객체 |
| IoC | 객체 생성과 연결의 제어권이 Spring으로 넘어가는 것 |
| DI | 필요한 객체를 외부에서 주입받는 것 |
| XML 설정 | `<bean>`, `<property>`, `<constructor-arg>`로 객체를 등록하고 연결 |
| Java Config | `@Configuration`, `@Bean`으로 Java 코드에서 bean 등록 |
| Component Scan | `@Component`, `@Service`, `@Repository` 등을 자동 탐색 |
| Lifecycle | 생성, 주입, 초기화, 사용, 소멸 흐름 |
| Scope | `singleton`은 하나, `prototype`은 요청마다 새 객체 |

## 6. 총정리 예제 실행 안내

새로 추가한 예제 위치:

```text
step01-04-review/
```

주요 파일:

```text
step01-04-review/src/main/java/com/spring/review/ReviewMainApp.java
step01-04-review/src/main/resources/applicationContext-review.xml
```

예제에서 확인할 수 있는 내용:

- XML bean 등록
- 생성자 주입
- setter 주입
- component-scan
- `@Autowired` + `@Qualifier`
- `@PostConstruct` / `@PreDestroy`
- `init-method` / `destroy-method`
- `singleton` / `prototype` scope 차이

실행 흐름:

1. `ClassPathXmlApplicationContext`가 XML 설정을 읽는다.
2. XML에 등록된 bean과 component-scan으로 찾은 bean이 생성된다.
3. 생성자 주입과 setter 주입이 실행된다.
4. 주문 처리, 메뉴 조회, 알림 발송을 실행한다.
5. prototype bean을 두 번 꺼내 서로 다른 객체인지 비교한다.
6. 컨테이너가 닫히며 소멸 callback이 실행된다.

## 7. 스스로 점검하기

- XML만 보고 어떤 객체가 생성되는지 설명할 수 있는가?
- `constructor-arg ref`와 `constructor-arg value`의 차이를 말할 수 있는가?
- `property ref`와 `property value`의 차이를 말할 수 있는가?
- `@ComponentScan`이 없으면 `@Component`가 붙은 클래스가 자동 등록될까?
- `@Qualifier`가 필요한 상황을 예로 들 수 있는가?
- `singleton`과 `prototype`을 코드 출력으로 구분할 수 있는가?
