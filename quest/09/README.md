# Day08 실습 문제 — Spring MVC + Thymeleaf

**기술 스택**: Spring 6.2.7 / Java 17 / Thymeleaf 3.1.2 (thymeleaf-spring6) / Tomcat 10.1+

---

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 | 핵심 TODO |
|------|------|--------|-----------|-----------|
| 문제 1 | problem01/ | ★★☆☆ | Thymeleaf 설정 완성하기 | TODO 1~3 (XML Bean 3개) |
| 문제 2 | problem02/ | ★★☆☆ | th:each와 th:href 활용 | TODO 1~7 |
| 문제 3 | problem03/ | ★★★☆ | th:object/th:field 폼 처리 | TODO 1~14 |
| 문제 4 (도전) | problem04/ | ★★★★ | 키오스크 전체 구현 (Thymeleaf 버전) | TODO 1~5 |

---

## 문제 1 — Thymeleaf 설정 완성하기 (★★☆☆)

**폴더**: `problem01/`

**목표**: `dispatcher-context.xml`의 주석 처리된 TODO 3개를 채워서 Thymeleaf ViewResolver가 동작하도록 설정한다.

| TODO | 파일 | 내용 |
|------|------|------|
| TODO 1 | dispatcher-context.xml | `SpringResourceTemplateResolver` Bean 등록 (prefix/suffix/encoding 설정) |
| TODO 2 | dispatcher-context.xml | `SpringTemplateEngine` Bean 등록 (templateResolver 주입) |
| TODO 3 | dispatcher-context.xml | `ThymeleafViewResolver` Bean 등록 (templateEngine 주입) |

**완성 기준**:
- `GET /welcome` 접속 시 welcome.html이 렌더링되며 환영 메시지와 현재 시각이 출력된다.

**힌트**:
```xml
<!-- templateResolver -->
<bean id="templateResolver"
      class="org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver">
    <property name="prefix" value="classpath:/templates/"/>
    <property name="suffix" value=".html"/>
    <property name="characterEncoding" value="UTF-8"/>
</bean>
```

---

## 문제 2 — th:each와 th:href 활용 (★★☆☆)

**폴더**: `problem02/`

**목표**: 메뉴 목록을 `th:each`로 출력하고, 메뉴명 클릭 시 상세 페이지로 이동하는 링크를 완성한다.

| TODO | 파일 | 내용 |
|------|------|------|
| TODO 1 | MenuController.java | `menuList`를 `List.of(...)`로 초기화 (메뉴 4개) |
| TODO 2 | MenuController.java | `GET /menu` — model에 "menus" 추가 |
| TODO 3 | MenuController.java | `GET /menu/{id}` — stream으로 메뉴 검색, model에 "menu" 추가 |
| TODO 4 | menu/list.html | `<tr>`에 `th:each="menu : ${menus}"` 추가 및 각 셀 `th:text` 작성 |
| TODO 5 | menu/list.html | `<a>`에 `th:href="@{/menu/{id}(id=${menu.id})}"` 추가 |
| TODO 6 | menu/detail.html | `th:text`로 menu.name / menu.category / menu.price 출력 |
| TODO 7 | menu/detail.html | 목록 링크에 `th:href="@{/menu}"` 추가 |

**완성 기준**:
- `GET /menu` → 메뉴 4개가 테이블로 출력된다.
- 메뉴명 클릭 → `GET /menu/1` 등 상세 페이지로 이동하여 해당 메뉴 정보가 출력된다.

---

## 문제 3 — th:object/th:field로 폼 처리 (★★★☆)

**폴더**: `problem03/`

**목표**: `th:object`/`th:field`를 사용하는 주문 폼을 완성하고, POST 처리 결과 화면을 구현한다.

| TODO | 파일 | 내용 |
|------|------|------|
| TODO 1 | OrderForm.java | 필드 4개 선언 (customerName, menuName, quantity, requestMessage) |
| TODO 2 | OrderForm.java | 기본 생성자 선언 |
| TODO 3 | OrderForm.java | getter/setter 구현 |
| TODO 4 | OrderController.java | `GET /orders/new` — model에 "menus"(메뉴명 Set) 추가 |
| TODO 5 | OrderController.java | `GET /orders/new` — model에 "orderForm"(new OrderForm()) 추가 |
| TODO 6 | OrderController.java | `POST /orders` — priceMap에서 단가 조회 |
| TODO 7 | OrderController.java | `POST /orders` — totalPrice 계산 |
| TODO 8 | OrderController.java | `POST /orders` — model에 orderForm, unitPrice, totalPrice 추가 |
| TODO 9 | order/form.html | `<form>`에 `th:action="@{/orders}"`, `th:object="${orderForm}"` 추가 |
| TODO 10 | order/form.html | 고객명 `<input>`에 `th:field="*{customerName}"` 추가 |
| TODO 11 | order/form.html | 메뉴 `<select>`에 `th:field="*{menuName}"` 추가, `th:each`로 option 생성 |
| TODO 12 | order/form.html | 수량 `<input>`에 `th:field="*{quantity}"` 추가 |
| TODO 13 | order/form.html | 요청사항 `<input>`에 `th:field="*{requestMessage}"` 추가 |
| TODO 14 | order/result.html | `th:text`로 주문 정보 6개(고객명/메뉴명/수량/요청사항/단가/총금액) 출력 |

**완성 기준**:
- `GET /orders/new` → 주문 폼이 출력되고, 메뉴 select에 3개 옵션이 표시된다.
- 폼 제출 → `POST /orders` 처리 후 result 화면에 단가와 총 금액이 정확히 출력된다.

---

## 문제 4 (도전) — 키오스크 전체 구현 Thymeleaf 버전 (★★★★)

**폴더**: `problem04/`

**목표**: `KioskController.java`의 TODO 5개를 완성하여 메뉴 목록 조회 → 상세 → 검색 → 주문 → 결과의 전체 흐름을 구현한다. 템플릿 파일은 모두 완성되어 있으므로 컨트롤러만 작성하면 된다.

| TODO | 매핑 | 내용 |
|------|------|------|
| TODO 1 | `GET /kiosk/menus` | model에 "menus" 전체 목록 추가, "kiosk/menus" 반환 |
| TODO 2 | `GET /kiosk/menus/{menuId}` | id로 메뉴 검색, model에 "menu" 추가, "kiosk/detail" 반환 |
| TODO 3 | `GET /kiosk/menus/search` | keyword 필터링 후 "menus"·"keyword" 추가, "kiosk/menus" 반환 |
| TODO 4 | `GET /kiosk/orders/new` | "menus"·"selectedMenuId"·"orderForm" 추가, "kiosk/form" 반환 |
| TODO 5 | `POST /kiosk/orders` | 메뉴 검색, totalPrice 계산, "orderForm"·"menu"·"totalPrice" 추가, "kiosk/result" 반환 |

**완성 기준**:
- `GET /kiosk/menus` → 메뉴 4개 목록 출력
- `GET /kiosk/menus/search?keyword=버거` → 버거 3개만 필터링 출력
- `GET /kiosk/menus/1` → 불고기버거 상세 정보 출력
- 주문 폼에서 메뉴 상세 페이지의 "주문하기" 링크 클릭 시 해당 메뉴가 기본 선택됨
- 주문 완료 화면에서 총 금액이 정확히 계산되어 출력됨

**주의 사항**:
- `GET /kiosk/menus/search`와 `GET /kiosk/menus/{menuId}`의 경로 충돌에 주의한다.
  `search` 매핑을 `{menuId}` 매핑보다 먼저 선언하거나, 별도 경로(`/kiosk/search`)로 설계한다.
- TODO 4에서 `orderForm.menuId`에 `selectedMenuId`를 미리 세팅해야 폼의 select 기본값이 동작한다.

---

## Thymeleaf 핵심 문법 요약

| 표현식 | 설명 | 예시 |
|--------|------|------|
| `th:text` | 텍스트 출력 | `th:text="${name}"` |
| `th:each` | 반복 | `th:each="item : ${list}"` |
| `th:href` | URL 링크 | `th:href="@{/menu/{id}(id=${menu.id})}"` |
| `th:action` | 폼 action URL | `th:action="@{/orders}"` |
| `th:object` | 폼 바인딩 객체 | `th:object="${orderForm}"` |
| `th:field` | 폼 필드 바인딩 | `th:field="*{customerName}"` |
| `th:if` | 조건 표시 | `th:if="${#lists.isEmpty(list)}"` |
| `th:unless` | 조건 비표시 | `th:unless="${#lists.isEmpty(list)}"` |
