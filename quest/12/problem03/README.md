# 문제 3 — 대출 등록 폼 (★★★☆)

**실행:** `Problem03Application`  
**URL:** http://localhost:8080/loans/new

> `list()`, `list.html` 검색부는 완성. **아래 3개 파일** 수정.

## 수정할 파일 — TODO 순서대로

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `service/LoanService.java` | `save()` — Student 조회 + Loan 생성 |
| TODO 2 | `service/LoanService.java` | `save()` — 수량 0 skip |
| TODO 3 | `service/LoanService.java` | `save()` — Book 조회 + LoanItem 추가 |
| TODO 4 | `service/LoanService.java` | `save()` — 빈 대출 예외 |
| TODO 5 | `service/LoanService.java` | `save()` — repository.save 반환 |
| TODO 6 | `controller/LoanController.java` | `GET /loans/new` — students, books |
| TODO 7 | `controller/LoanController.java` | `POST` — `@RequestParam("studentId")` |
| TODO 8 | `controller/LoanController.java` | `POST` — `@RequestParam("bookIds") List<Long>` |
| TODO 9 | `controller/LoanController.java` | `POST` — `@RequestParam("quantities")` + redirect |
| TODO 10 | `templates/loan/form.html` | 학생 select `th:each` |
| TODO 11 | `templates/loan/form.html` | 책별 hidden `bookIds` + `quantities` |

**완성 기준:** 등록 성공 → redirect, 0권 → flash error
