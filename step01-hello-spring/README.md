# 1일차 실습 문제

> 실습 예제 코드 참고 경로: `../practice/hello-spring/`

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 문제 1 | `problem01/` | ★☆☆☆ | pom.xml 의존성 추가 |
| 문제 2 | `problem02/` | ★★☆☆ | applicationContext.xml Bean 등록 |
| 문제 3 | `problem03/` | ★★★☆ | ApplicationContext로 Bean 가져오기 |
| 문제 4 (도전) | `problem04/` | ★★★★ | 클래스 설계 → Bean 등록 → 사용 전체 |

---

## 문제 1 — pom.xml 의존성 추가 (★☆☆☆)

**목표**: `spring-context` 의존성이 빠진 `pom.xml`을 완성한다.

**수정 파일**: `problem01/pom.xml`

**TODO 위치**: 파일 내 `TODO` 주석

**추가해야 할 의존성 정보**:
```
groupId   : org.springframework
artifactId: spring-context
version   : 6.2.7
```

**완성 확인**: `ProblemMain.java`를 실행하면 다음이 출력되어야 한다.
```
[GreeterBean] 안녕하세요, Spring입니다!
```

---

## 문제 2 — Bean 등록 (★★☆☆)

**목표**: 비어 있는 `applicationContext.xml`에 두 개의 Bean을 등록한다.

**수정 파일**: `problem02/src/main/resources/applicationContext.xml`

**TODO 위치**: XML 파일 내 주석

**등록해야 할 Bean**:

| id | class | 프로퍼티 |
|----|-------|---------|
| `productBean` | `com.spring.problem02.ProductBean` | `name="아메리카노"`, `price=4500` |
| `cartBean` | `com.spring.problem02.CartBean` | (없음) |

**힌트**: `<property name="..." value="..."/>` 태그로 프로퍼티를 주입한다.

**완성 확인**:
```
[ProductBean] 상품명: 아메리카노, 가격: 4500원
[CartBean] 장바구니가 준비되었습니다.
```

---

## 문제 3 — Bean 사용하기 (★★★☆)

**목표**: `applicationContext.xml`은 완성되어 있다.  
`ProblemMain.java`에 `ApplicationContext`를 생성하고 Bean을 꺼내 사용하는 코드를 작성한다.

**수정 파일**: `problem03/src/main/java/com/spring/problem03/ProblemMain.java`

**TODO 항목** (총 4개):
1. `ClassPathXmlApplicationContext`로 컨테이너 생성
2. `context.getBean()`으로 `"coffeeBean"` 꺼내기
3. `coffee.introduce()` 메서드 호출
4. 컨테이너 종료

**완성 확인**:
```
[CoffeeBean] 이름: 카페라떼 | 가격: 5000원 | 원산지: 에티오피아
```

---

## 문제 4 — 도전: 학생 관리 시스템 (★★★★)

**목표**: 아래 3개 파일을 모두 완성한다.

**수정 파일**:
1. `problem04/src/main/java/com/spring/problem04/Student.java`
2. `problem04/src/main/resources/applicationContext.xml`
3. `problem04/src/main/java/com/spring/problem04/ProblemMain.java`

**요구사항**은 각 파일의 TODO 주석을 참조한다.

**완성 확인**:
```
안녕하세요! 저는 홍길동이고, 25살이며 컴퓨터공학을 공부하고 있습니다.
안녕하세요! 저는 김춘향이고, 23살이며 소프트웨어공학을 공부하고 있습니다.
```
