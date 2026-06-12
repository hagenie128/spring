# quest/12 — Spring Boot JPA Join + MVC 연습

> Spring Boot 3.4 | Java 17 | JPA | Thymeleaf | H2  
> **참고 완성본:** `step13-boot-jpa-join` (카페 주문) — 패턴은 같고 도메인만 **도서관 대출**입니다.

---

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 | 수정할 파일 수 |
|------|------|--------|-----------|----------------|
| 01 | `problem01/` | ★★☆☆ | 연관관계 + JOIN FETCH | TODO 1~9 |
| 02 | `problem02/` | ★★★☆ | `@RequestParam` 검색 | TODO 1~9 |
| 03 | `problem03/` | ★★★☆ | 등록 폼 + List 파라미터 | TODO 1~11 |
| 04 | `problem04/` | ★★★★ | 상세 · 상태 · 삭제 | TODO 1~14 |

**실행 (공통)**

```bash
cd quest/12/problem01   # 02, 03, 04 로 바꿔가며
gradlew.bat bootRun
```

| problem | main 클래스 | URL |
|---------|------------|-----|
| 01~04 | `Problem0NApplication` | http://localhost:8080/loans |

---

## 문제 1 — 연관관계 & JOIN FETCH (★★☆☆)

**폴더:** `problem01/`  
**목표:** 대출 목록에서 학생 정보를 JOIN FETCH로 함께 조회하고 화면에 출력한다.

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `src/main/java/com/spring/entity/LoanItem.java` | `loan` 필드에 `@ManyToOne(LAZY)` + `@JoinColumn(name="loan_id")` |
| TODO 2 | `src/main/java/com/spring/entity/Loan.java` | `loanItems`에 `@OneToMany(mappedBy="loan", cascade=ALL, orphanRemoval=true)` |
| TODO 3 | `src/main/java/com/spring/entity/Loan.java` | `addLoanItem()` — `loanItems.add` + `item.setLoan(this)` |
| TODO 4 | `src/main/java/com/spring/entity/Loan.java` | `@PrePersist`로 `loanDate = LocalDateTime.now()` |
| TODO 5 | `src/main/java/com/spring/repository/LoanRepository.java` | `findAllWithStudent()` JPQL에 `join fetch l.student` 추가 |
| TODO 6 | `src/main/java/com/spring/service/LoanService.java` | `findAllWithStudent()` → `loanRepository` 호출 |
| TODO 7 | `src/main/java/com/spring/controller/LoanController.java` | `GET /loans` — `loans`를 model에 담고 `"loan/list"` 반환 |
| TODO 8 | `src/main/resources/templates/loan/list.html` | `<tr>`에 `th:each="loan : ${loans}"` |
| TODO 9 | `src/main/resources/templates/loan/list.html` | `loan.id`, `loan.student.name`, `loan.loanDate`, `loan.loanItems.size()`, `loan.status.label` 출력 |

**완성하지 않아도 되는 파일 (참고용)**  
`Student.java`, `Book.java`, `LoanStatus.java`, `DataInitializer.java` — 이미 작성됨

**완성 기준**
- `GET /loans` → 대출 3건 + 학생명 표시
- SQL 로그에서 student **N+1 없음** (TODO 5 전후 비교)

**step13 대응:** `Order`, `OrderItem`, `OrderRepository.findAllWithMember()`, `order/list.html`

---

## 문제 2 — 검색 필터 @RequestParam (★★★☆)

**폴더:** `problem02/`  
**목표:** 학생·상태로 대출 목록을 필터링하고, 검색 후 select 선택값을 유지한다.

> 엔티티(`entity/`)는 **완성 상태**입니다. 아래 파일만 수정하세요.

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `src/main/java/com/spring/repository/LoanRepository.java` | `search()` JPQL — `(:studentId IS NULL OR s.id = :studentId)` |
| TODO 2 | `src/main/java/com/spring/repository/LoanRepository.java` | `search()` JPQL — `(:status IS NULL OR l.status = :status)` |
| TODO 3 | `src/main/java/com/spring/service/LoanService.java` | `search(studentId, status)` → `loanRepository.search(...)` |
| TODO 4 | `src/main/java/com/spring/controller/LoanController.java` | `list()` 파라미터 — `@RequestParam(value="studentId", required=false) Long studentId` |
| TODO 5 | `src/main/java/com/spring/controller/LoanController.java` | `list()` 파라미터 — `@RequestParam(value="status", required=false) LoanStatus status` |
| TODO 6 | `src/main/java/com/spring/controller/LoanController.java` | `model.addAttribute("loans", loanService.search(...))` |
| TODO 7 | `src/main/java/com/spring/controller/LoanController.java` | `students`, `statuses`, `selectedStudentId`, `selectedStatus` model 추가 |
| TODO 8 | `src/main/resources/templates/loan/list.html` | 학생 `<select>` — `th:each="s : ${students}"`, `th:selected` |
| TODO 9 | `src/main/resources/templates/loan/list.html` | 상태 `<select>` — `th:each="st : ${statuses}"`, `th:text="${st.label}"` |

**완성 기준**
- `GET /loans` / `?studentId=1` / `?status=BORROWED` 각각 동작
- 검색 후 select에 선택값 유지

**step13 대응:** `OrderController.list`, `OrderRepository.search`, `order/list.html` 검색 폼

---

## 문제 3 — 대출 등록 폼 (★★★☆)

**폴더:** `problem03/`  
**목표:** 여러 도서를 한 번에 대출 등록한다. (`@RequestParam` + `List`)

> 목록·검색(`list` 메서드, `list.html` 검색 폼)은 **완성**입니다.

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `src/main/java/com/spring/service/LoanService.java` | `save()` — `studentRepository.findById` 후 `new Loan()` + `setStudent` |
| TODO 2 | `src/main/java/com/spring/service/LoanService.java` | `save()` — 루프에서 `quantities.get(i) == 0` 이면 `continue` |
| TODO 3 | `src/main/java/com/spring/service/LoanService.java` | `save()` — `bookRepository.findById` → `new LoanItem(book, qty)` → `addLoanItem` |
| TODO 4 | `src/main/java/com/spring/service/LoanService.java` | `save()` — `loanItems` 비어 있으면 `IllegalArgumentException` |
| TODO 5 | `src/main/java/com/spring/service/LoanService.java` | `save()` — `loanRepository.save(loan)` 후 반환 |
| TODO 6 | `src/main/java/com/spring/controller/LoanController.java` | `GET /loans/new` — `students`, `books` model → `"loan/form"` |
| TODO 7 | `src/main/java/com/spring/controller/LoanController.java` | `POST /loans` — `@RequestParam("studentId") Long studentId` |
| TODO 8 | `src/main/java/com/spring/controller/LoanController.java` | `POST /loans` — `@RequestParam("bookIds") List<Long> bookIds` |
| TODO 9 | `src/main/java/com/spring/controller/LoanController.java` | `POST /loans` — `@RequestParam("quantities") List<Integer> quantities`, try-catch + redirect |
| TODO 10 | `src/main/resources/templates/loan/form.html` | 학생 `<select>` — `th:each="s : ${students}"`, `name="studentId"` |
| TODO 11 | `src/main/resources/templates/loan/form.html` | 책 행 — `th:each="book : ${books}"`, hidden `bookIds` + number `quantities` |

**완성 기준**
- `GET /loans/new` → 폼 표시
- 등록 성공 → `redirect:/loans/{id}` (상세는 problem04)
- 책 0권 → `redirect:/loans/new` + flash error

**step13 대응:** `OrderService.save`, `OrderController.save`, `order/form.html`

**주의:** 폼 데이터는 `@PathVariable` ❌ → `@RequestParam` ✅

---

## 문제 4 — 대출 CRUD 종합 (★★★★)

**폴더:** `problem04/`  
**목표:** 상세 조회 · 상태 변경 · 삭제까지 전체 흐름을 완성한다.

> 목록·검색·등록(`LoanController`의 `list`, `form`, `save` / `form.html`)은 **완성**입니다.

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `src/main/java/com/spring/repository/LoanRepository.java` | `findByIdWithDetails` — `join fetch l.student` |
| TODO 2 | `src/main/java/com/spring/repository/LoanRepository.java` | `findByIdWithDetails` — `join fetch l.loanItems`, `join fetch li.book` |
| TODO 3 | `src/main/java/com/spring/service/LoanService.java` | `findByIdWithDetails(id)` — `orElseThrow` |
| TODO 4 | `src/main/java/com/spring/service/LoanService.java` | `updateStatus(id, status)` — 조회 후 `setStatus` |
| TODO 5 | `src/main/java/com/spring/service/LoanService.java` | `delete(id)` — `loanRepository.deleteById` (**Book 삭제 금지**) |
| TODO 6 | `src/main/java/com/spring/controller/LoanController.java` | `GET /loans/{id}` — `loan`, `statuses` → `"loan/detail"` |
| TODO 7 | `src/main/java/com/spring/controller/LoanController.java` | `POST /loans/{id}/status` — `@PathVariable("id")` |
| TODO 8 | `src/main/java/com/spring/controller/LoanController.java` | `POST /loans/{id}/status` — `@RequestParam("status")`, flash + redirect |
| TODO 9 | `src/main/java/com/spring/controller/LoanController.java` | `POST /loans/{id}/delete` — 삭제 후 `redirect:/loans` |
| TODO 10 | `src/main/resources/templates/loan/detail.html` | 제목 `th:text="'대출 #' + ${loan.id}"` |
| TODO 11 | `src/main/resources/templates/loan/detail.html` | `loan.student.name`, `loan.loanDate`, `loan.status` 뱃지 |
| TODO 12 | `src/main/resources/templates/loan/detail.html` | `th:each="item : ${loan.loanItems}"` — 책 제목·저자·수량 |
| TODO 13 | `src/main/resources/templates/loan/detail.html` | 상태 변경 `<form>` — `POST` `/loans/{id}/status`, `name="status"` |
| TODO 14 | `src/main/resources/templates/loan/detail.html` | 삭제 `<form>` — `POST` `/loans/{id}/delete` |

### ✅ 완료 체크 (스스로 점검 — TODO 번호 아님)

- [ ] `LoanController`에 같은 URL 메서드가 **2개 없음**
- [ ] `@GetMapping("/new")` 가 `@GetMapping("/{id}")` **위에** 있음
- [ ] 상세에서 `loan.student.name` 출력됨
- [ ] 상태 변경 후 flash 메시지 보임
- [ ] 삭제해도 `book` 테이블 데이터 유지

**step13 대응:** `OrderRepository.findByIdWithDetails`, `OrderController` detail/status/delete, `order/detail.html`

---

## 학습 범위 ↔ step13 매핑

| step13 | quest/12 |
|--------|----------|
| `Member` | `Student` |
| `MenuItem` | `Book` |
| `Order` | `Loan` |
| `OrderItem` | `LoanItem` |
| `OrderStatus` | `LoanStatus` |
| `/orders` | `/loans` |

---

## Cursor / Windows 팁

- `@RequestParam(value = "이름", required = false)` — **이름 직접 명시** (Cursor 필수)
- 같은 URL에 메서드 2개 → `Ambiguous mapping`
- 폼 `name` = `@RequestParam("name")` / URL `{id}` = `@PathVariable("id")`

---

## 추천 진행 순서

```
problem01 (엔티티+FETCH)
    ↓
problem02 (검색)
    ↓
problem03 (등록)
    ↓
problem04 (상세·상태·삭제)
```

각 problem 폴더의 `README.md`에도 동일 내용이 요약되어 있습니다.
