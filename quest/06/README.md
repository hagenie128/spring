# 6일차 실습 문제

> 실습 예제 코드 참고 경로: `../practice/spring-mvc-basic/`

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 |
|------|------|--------|-----------|
| 문제 1 | `problem01/` | ★★☆☆ | web.xml — DispatcherServlet 설정 완성 |
| 문제 2 | `problem02/` | ★★☆☆ | @Controller + @RequestMapping 메서드 추가 |
| 문제 3 | `problem03/` | ★★★☆ | dispatcher-context.xml ViewResolver 설정 + 컨트롤러 완성 |
| 문제 4 (도전) | `problem04/` | ★★★★ | 키오스크 미니 사이트 Spring MVC 전체 구성 |

---

## 문제 1 — web.xml: DispatcherServlet 설정 완성 (★★☆☆)

**수정 파일**: `problem01/src/main/webapp/WEB-INF/web.xml`

`WelcomeController`가 이미 작성되어 있고, `dispatcher-context.xml`과 `welcome.jsp`도 완성된 상태다.  
`web.xml`의 TODO 3개를 채워 DispatcherServlet과 CharacterEncodingFilter를 등록하라.

**요구사항**:
1. `dispatcher`라는 이름으로 DispatcherServlet을 등록하라.
   - `contextConfigLocation`: `/WEB-INF/spring/dispatcher-context.xml`
   - `load-on-startup`: 1
2. 위에서 등록한 `dispatcher`가 모든 요청(`/`)을 처리하도록 매핑하라.
3. `CharacterEncodingFilter`를 등록하여 UTF-8 인코딩을 적용하라.
   - `encoding=UTF-8`, `forceEncoding=true`
   - `url-pattern`: `/*`

**예상 결과**:
- `http://localhost:8080/problem01/welcome` 접속 시 "환영합니다! Spring MVC 첫 번째 실습" 화면 출력

---

## 문제 2 — @Controller + @RequestMapping 메서드 추가 (★★☆☆)

**수정 파일**: `problem02/src/main/java/com/spring/problem02/controller/MenuController.java`

`web.xml`과 `dispatcher-context.xml`은 완성된 상태다.  
`MenuController.java`의 TODO 4개를 채워 메뉴 조회와 주문 처리 메서드를 작성하라.

**요구사항**:
1. 클래스에 `@Controller` 어노테이션을 추가하라.
2. 클래스에 `@RequestMapping("/menu")`를 추가하라.
3. `GET /menu` 요청을 처리하는 메서드를 작성하라.
   - model에 `menuName="불고기버거"`, `price=8500`, `category="버거"` 추가
   - `"menu"` 뷰 이름 반환
4. `GET /menu/order?item=불고기버거&qty=2` 요청을 처리하는 메서드를 작성하라.
   - `@RequestParam`으로 `item`(String), `qty`(int, defaultValue="1") 수신
   - model에 `itemName`, `quantity`, `totalPrice`(qty * 8500) 추가
   - `"order"` 뷰 이름 반환

**예상 결과**:
- `http://localhost:8080/problem02/menu` → 메뉴 정보 출력 (불고기버거, 8500원, 버거)
- `http://localhost:8080/problem02/menu/order?item=불고기버거&qty=2` → 주문 정보 출력 (합계: 17000원)

---

## 문제 3 — dispatcher-context.xml ViewResolver 설정 (★★★☆)

**수정 파일**: `problem03/src/main/webapp/WEB-INF/spring/dispatcher-context.xml`

`web.xml`, `ProductController.java`, `list.jsp`, `detail.jsp`는 완성된 상태다.  
`dispatcher-context.xml`의 TODO 3개를 채워 Spring MVC 설정을 완성하라.

**요구사항**:
1. 어노테이션 기반 MVC를 활성화하는 태그를 추가하라. (`<mvc:annotation-driven/>`)
2. `com.spring.problem03.controller` 패키지를 컴포넌트 스캔하라.
3. `InternalResourceViewResolver` Bean을 등록하라.
   - `prefix`: `/WEB-INF/views/`
   - `suffix`: `.jsp`

**예상 결과**:
- `http://localhost:8080/problem03/product/list` → 상품 목록 출력 (아메리카노, 카페라떼, 녹차라떼)
- `http://localhost:8080/problem03/product/detail?name=아메리카노` → 상품 상세 출력 (아메리카노, 4500원)

---

## 문제 4 — 도전: 키오스크 미니 사이트 Spring MVC 전체 구성 (★★★★)

**수정 파일 3개**:
1. `problem04/src/main/webapp/WEB-INF/web.xml` — DispatcherServlet + CharacterEncodingFilter 전체 작성
2. `problem04/src/main/webapp/WEB-INF/spring/dispatcher-context.xml` — MVC 설정 전체 작성
3. `problem04/src/main/java/com/spring/problem04/controller/KioskController.java` — 컨트롤러 전체 구현

`home.jsp`, `menu.jsp`, `order.jsp`, `MenuItem.java`(DTO)는 완성된 상태다.

**요구사항**:
- `web.xml`: DispatcherServlet(`/WEB-INF/spring/dispatcher-context.xml`) + CharacterEncodingFilter(UTF-8)
- `dispatcher-context.xml`: `<mvc:annotation-driven/>`, component-scan(`com.spring.problem04.controller`), InternalResourceViewResolver(prefix=`/WEB-INF/views/`, suffix=`.jsp`)
- `KioskController` (`@Controller`, `@RequestMapping("/kiosk")`):
  - `GET /kiosk/home` → `"kiosk/home"` 반환
  - `GET /kiosk/menu` → `menuList`(MenuItem 3개: 불고기버거/8500, 치킨버거/9000, 새우버거/8000) model 추가 후 `"kiosk/menu"` 반환
  - `GET /kiosk/order?menu=...&qty=...` → `orderMenu`, `orderQty`, `totalPrice` model 추가 후 `"kiosk/order"` 반환
    - 가격: 불고기버거=8500, 치킨버거=9000, 새우버거=8000 (Map 또는 switch 활용)

**예상 결과**:
- `http://localhost:8080/problem04/kiosk/home` → 키오스크 홈 화면 (메뉴 보기, 주문하기 링크)
- `http://localhost:8080/problem04/kiosk/menu` → 메뉴 목록 (표: 메뉴명, 가격)
- `http://localhost:8080/problem04/kiosk/order?menu=불고기버거&qty=2` → "합계: 17000원"
- `http://localhost:8080/problem04/kiosk/order?menu=치킨버거&qty=3` → "합계: 27000원"
