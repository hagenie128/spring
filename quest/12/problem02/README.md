# 문제 2 — 검색 필터 @RequestParam (★★★☆)

**실행:** `Problem02Application`  
**URL:** http://localhost:8080/loans

> `entity/` 폴더는 완성됨. **아래 4개 파일만** 수정하세요.

## 수정할 파일 — TODO 순서대로

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `repository/LoanRepository.java` | `search()` — studentId null 조건 |
| TODO 2 | `repository/LoanRepository.java` | `search()` — status null 조건 |
| TODO 3 | `service/LoanService.java` | `search()` → repository 호출 |
| TODO 4 | `controller/LoanController.java` | `@RequestParam(value="studentId", required=false)` |
| TODO 5 | `controller/LoanController.java` | `@RequestParam(value="status", required=false)` |
| TODO 6 | `controller/LoanController.java` | `model.addAttribute("loans", ...)` |
| TODO 7 | `controller/LoanController.java` | students, statuses, selected* model |
| TODO 8 | `templates/loan/list.html` | 학생 select `th:each` + `th:selected` |
| TODO 9 | `templates/loan/list.html` | 상태 select `th:each` |

**완성 기준:** `?studentId=1`, `?status=BORROWED` 필터 + 선택값 유지
