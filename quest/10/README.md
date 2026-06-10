# Spring Boot MVC + Thymeleaf 문제

> Spring Boot 3.4.6 | Java 17 | Thymeleaf

---

## 문제 1 (★★☆☆) — th:text / th:each 기초

**프로젝트:** `problem01`  
**실행 URL:** http://localhost:8080/menus

**요구사항:**
1. Controller에서 `storeName` = "키오스크 1호점", `menus` = 문자열 리스트를 Model에 추가
2. HTML에서 `th:text`로 storeName 출력
3. `th:text`로 menus 개수 출력
4. `th:each`로 menus를 `<li>` 반복 출력

**기대 출력:**
```
키오스크 1호점
전체 메뉴: 5개

• 불고기버거
• 치킨버거
• 새우버거
• 감자튀김
• 콜라
```

---

## 문제 2 (★★☆☆) — th:if / th:unless / th:switch

**프로젝트:** `problem02`  
**실행 URL:** http://localhost:8080/menus

**요구사항:**
1. `stat.count`로 순번 출력
2. `#numbers.formatInteger`로 가격 포맷 출력 (예: `6,500원`)
3. `th:if` / `th:unless`로 available에 따라 "판매중"(초록) / "품절"(빨강) 뱃지 출력
4. `th:switch` / `th:case`로 grade에 따라 "BEST"(주황) / "NEW"(파랑) / "-" 출력

---

## 문제 3 (★★★☆) — Form 처리 (th:action / th:object / th:field)

**프로젝트:** `problem03`  
**실행 URL:** http://localhost:8080/menus/new

**요구사항:**
1. GET `/menus/new` → 빈 폼 반환 (model에 `form`, `categories` 추가)
2. POST `/menus` → `@Valid` 유효성 검사
   - 오류 시 폼으로 복귀 + 오류 메시지 표시
   - 성공 시 redirect + Flash 메시지
3. HTML form에 `th:action`, `th:object`, `th:field` 설정
4. `th:errors`로 필드별 오류 메시지 출력

**검증 규칙:**
- name: `@NotBlank`
- price: `@NotNull`, `@Min(0)`

---

## 문제 4 (★★★★) — 상품 CRUD 종합

**프로젝트:** `problem04`  
**실행 URL:** http://localhost:8080/products

**요구사항 (TODO 1~34):**

| # | 항목 |
|---|------|
| 1~6 | `ProductRepository` — `@Repository` + `LinkedHashMap` + `AtomicLong`으로 CRUD 구현 |
| 7~13 | `ProductService` — `@Service` + CRUD 메서드 전체 구현 |
| 14~21 | `ProductController` — CRUD 핸들러 전체 구현 |
| 22 | `DataInitializer` 구현 (CommandLineRunner, 샘플 데이터 3건) |
| 23~26 | `list.html` Thymeleaf 표현식 완성 |
| 27~31 | `detail.html` Thymeleaf 표현식 완성 |
| 32~38 | `form.html` Thymeleaf 표현식 완성 |

**기대 동작:**
- 목록 → 상세 → 수정 → 삭제 전체 흐름 동작
- 등록/수정 시 유효성 검사 오류 표시
- 성공 시 Flash 메시지 표시
