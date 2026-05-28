# 2일차 실습 문제

> 실습 예제 코드 참고 경로: `../practice/ioc-basic/`

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 문제 1 | `problem01/` | ★★☆☆ | XML — ref·컬렉션 주입 완성 |
| 문제 2 | `problem02/` | ★★★☆ | 어노테이션 추가 + component-scan 설정 |
| 문제 3 | `problem03/` | ★★★☆ | 싱글톤·프로토타입 비교 코드 작성 |
| 문제 4 (도전) | `problem04/` | ★★★★ | 카페 관리 시스템 전체 구성 |

---

## 문제 1 — XML ref·컬렉션 주입 (★★☆☆)

**수정 파일**: `problem01/src/main/resources/applicationContext.xml`

`MenuBean`(메뉴 정보)과 `RestaurantBean`(식당 정보)이 이미 작성되어 있다.  
`applicationContext.xml`의 TODO를 채워 두 Bean을 완성하라.

**요구사항**:
- `menuBean`: `category="한식"`, `items`=[비빔밥, 불고기, 된장찌개], `prices`={비빔밥=9000, 불고기=12000, 된장찌개=8000}
- `restaurantBean`: `name="한국의 맛"`, `menu` 프로퍼티에 `menuBean` **참조**

**완성 확인**:
```
[RestaurantBean] 식당명: 한국의 맛
[MenuBean] 카테고리: 한식
  메뉴 목록: [비빔밥, 불고기, 된장찌개]
  가격표: {비빔밥=9000, 불고기=12000, 된장찌개=8000}
```

---

## 문제 2 — 어노테이션 Bean 등록 (★★★☆)

**수정 파일 2개**:
1. `problem02/src/main/java/com/spring/problem02/*.java` — 각 클래스에 올바른 어노테이션 추가
2. `problem02/src/main/resources/applicationContext.xml` — component-scan 추가

**TODO 내용**:
- `PaymentRepository.java` → `@Repository` 추가
- `PaymentService.java` → `@Service` 추가
- `NotificationSender.java` → `@Component` 추가
- `applicationContext.xml` → `<context:component-scan base-package="com.spring.problem02"/>` 추가

**완성 확인**:
```
[PaymentRepository] 결제 내역 저장 완료 (id=1001)
[PaymentService] 결제 처리 시작: 50000원
[NotificationSender] 알림 전송: 결제가 완료되었습니다.
```

---

## 문제 3 — 싱글톤 vs 프로토타입 비교 (★★★☆)

**수정 파일**: `problem03/src/main/java/com/spring/problem03/ProblemMain.java`

`CounterBean`(singleton)과 `TemporaryBean`(prototype)은 이미 `applicationContext.xml`에 등록되어 있다.  
`ProblemMain.java`의 TODO 6개를 완성하라.

**작성 포인트**:
1. `counterBean` 두 번 꺼내 `c1`, `c2`에 저장
2. `c1.increment()` 3회 호출
3. `c2.getCount()` 출력 → `3` 이어야 함 (같은 인스턴스)
4. `c1 == c2` 비교 출력 → `true`
5. `temporaryBean` 두 번 꺼내 `t1`, `t2`에 저장
6. `t1.getId()`, `t2.getId()` 출력 → 서로 달라야 함

**완성 확인**:
```
[Singleton] c1 == c2 : true
[Singleton] c1.count() 3회 후 c2.getCount() = 3
[Prototype] t1.id = T-XXXX
[Prototype] t2.id = T-YYYY  (XXXX != YYYY)
[Prototype] t1 == t2 : false
```

---

## 문제 4 — 도전: 카페 관리 시스템 (★★★★)

아래 4개의 파일을 모두 완성하라.

**수정 파일**:
1. `problem04/src/main/java/com/spring/problem04/CoffeeItem.java` — POJO 클래스 직접 작성
2. `problem04/src/main/java/com/spring/problem04/CoffeeMenu.java` — `@Component` 추가
3. `problem04/src/main/java/com/spring/problem04/CafeService.java` — `@Service` 추가
4. `problem04/src/main/resources/applicationContext.xml` — CoffeeItem Bean 3개 등록 + component-scan 추가
5. `problem04/src/main/java/com/spring/problem04/ProblemMain.java` — Bean 사용 코드 작성

**CoffeeItem 요구사항**: 필드 `name(String)`, `price(int)`, `size(String)` + getter/setter + `toString()`

**예상 출력**:
```
[CafeService] ☕ Spring Cafe에 오신 것을 환영합니다!
[CoffeeMenu] === 오늘의 메뉴 ===
  아메리카노 | 4,500원 | Large
  카페라떼   | 5,000원 | Medium
  바닐라라떼 | 5,500원 | Medium
```
