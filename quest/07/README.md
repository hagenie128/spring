# 7일차 실습 문제

> 실습 예제 코드 참고 경로: `../practice/spring-mvc-form/`

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 문제 1 | `problem01/` | ★★☆☆ | `@GetMapping`, `@PathVariable`, `@RequestParam` |
| 문제 2 | `problem02/` | ★★☆☆ | `@PostMapping`, `@ModelAttribute` 폼 처리 |
| 문제 3 | `problem03/` | ★★★☆ | DTO 바인딩 + JSP/JSTL 주문 결과 출력 |
| 문제 4 (도전) | `problem04/` | ★★★★ | 키오스크 주문 화면 MVC 전체 구현 |

---

## 문제 1 — 메뉴 조회 컨트롤러 매핑 완성 (★★☆☆)

**수정 파일**: `problem01/src/main/java/com/spring/problem01/controller/MenuController.java`

`web.xml`, `dispatcher-context.xml`, JSP는 완성된 상태다.  
`MenuController.java`의 TODO를 채워 메뉴 목록, 상세, 검색 요청을 처리하라.

**요구사항**:
1. 클래스에 `@Controller`, `@RequestMapping("/menus")`를 추가하라.
2. `GET /menus` 요청을 처리하고 `menus` model attribute에 메뉴 목록을 담은 뒤 `"menu/list"`를 반환하라.
3. `GET /menus/{menuId}` 요청을 처리하고 `menu` model attribute에 선택 메뉴를 담은 뒤 `"menu/detail"`을 반환하라.
4. `GET /menus/search?keyword=버거` 요청을 처리하고 검색 결과를 `menus`, 검색어를 `keyword`에 담은 뒤 `"menu/list"`를 반환하라.

**예상 결과**:
- `http://localhost:8080/problem01/menus` → 메뉴 목록 출력
- `http://localhost:8080/problem01/menus/1` → 불고기버거 상세 출력
- `http://localhost:8080/problem01/menus/search?keyword=치킨` → 치킨버거 검색 결과 출력

---

## 문제 2 — 주문 폼 POST 처리 완성 (★★☆☆)

**수정 파일**: `problem02/src/main/java/com/spring/problem02/controller/OrderController.java`

주문 입력 폼 JSP와 설정 파일은 완성된 상태다.  
`OrderController.java`의 TODO를 채워 주문 폼 출력과 제출 처리를 완성하라.

**요구사항**:
1. 클래스에 `@Controller`, `@RequestMapping("/orders")`를 추가하라.
2. `GET /orders/new` 요청에서 `"order/form"` 뷰를 반환하라.
3. `POST /orders` 요청에서 `@RequestParam`으로 `customerName`, `menuName`, `quantity`를 받으라.
4. 메뉴 가격을 계산해 `customerName`, `menuName`, `quantity`, `totalPrice`를 model에 담고 `"order/result"`를 반환하라.

**예상 결과**:
- `http://localhost:8080/problem02/orders/new` → 주문 입력 폼 출력
- 고객명 `홍길동`, 메뉴 `불고기버거`, 수량 `2` 제출 → 합계 `17000원` 출력

---

## 문제 3 — `@ModelAttribute`와 DTO 바인딩 완성 (★★★☆)

**수정 파일 2개**:
1. `problem03/src/main/java/com/spring/problem03/dto/OrderForm.java`
2. `problem03/src/main/java/com/spring/problem03/controller/OrderController.java`

Form 데이터를 DTO로 자동 바인딩하도록 TODO를 완성하라.

**요구사항**:
- `OrderForm`
  - 필드: `customerName`, `menuName`, `quantity`, `requestMessage`
  - 기본 생성자, getter, setter 작성
- `OrderController`
  - `GET /orders/new` → `order/form`
  - `POST /orders` → `@ModelAttribute OrderForm form`으로 수신
  - `orderForm`, `unitPrice`, `totalPrice`를 model에 담고 `order/result` 반환

**예상 결과**:
- 요청사항까지 입력한 주문 폼 제출 시 결과 JSP에 고객명, 메뉴명, 수량, 요청사항, 합계가 출력된다.

---

## 문제 4 — 도전: 키오스크 주문 화면 MVC 전체 구현 (★★★★)

**수정 파일 4개**:
1. `problem04/src/main/java/com/spring/problem04/dto/MenuItem.java`
2. `problem04/src/main/java/com/spring/problem04/dto/OrderForm.java`
3. `problem04/src/main/java/com/spring/problem04/controller/KioskController.java`
4. `problem04/src/main/webapp/WEB-INF/views/kiosk/result.jsp`

키오스크 메뉴 조회, 검색, 주문 폼, 주문 결과 화면을 완성하라.

**요구사항**:
- `MenuItem`: `id`, `name`, `category`, `price`
- `OrderForm`: `customerName`, `menuId`, `quantity`, `requestMessage`
- `KioskController`
  - `GET /kiosk/menus` → 전체 메뉴 목록 출력
  - `GET /kiosk/menus/{menuId}` → 메뉴 상세 출력
  - `GET /kiosk/menus/search?keyword=...` → 이름 또는 카테고리에 keyword가 포함된 메뉴만 출력
  - `GET /kiosk/orders/new?menuId=1` → 주문 폼 출력
  - `POST /kiosk/orders` → `@ModelAttribute OrderForm`으로 수신 후 주문 결과 출력
- `result.jsp`
  - 고객명, 메뉴명, 카테고리, 수량, 요청사항, 총 금액 출력

**예상 결과**:
- `http://localhost:8080/problem04/kiosk/menus` → 메뉴 4개 출력
- `http://localhost:8080/problem04/kiosk/menus/search?keyword=버거` → 버거 메뉴만 출력
- `http://localhost:8080/problem04/kiosk/orders/new?menuId=1` → 불고기버거 주문 폼
- 주문자 `홍길동`, 수량 `2` 제출 → 합계 `17000원` 출력

