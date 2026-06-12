# 문제 4 — 대출 CRUD 종합 (★★★★)

**실행:** `Problem04Application`

> `list`, `form`, `save` 는 완성. **아래 4개 파일** 수정.

## 수정할 파일 — TODO 순서대로

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `repository/LoanRepository.java` | `findByIdWithDetails` — fetch student |
| TODO 2 | `repository/LoanRepository.java` | `findByIdWithDetails` — fetch loanItems, book |
| TODO 3 | `service/LoanService.java` | `findByIdWithDetails()` |
| TODO 4 | `service/LoanService.java` | `updateStatus()` |
| TODO 5 | `service/LoanService.java` | `delete()` — 대출만 삭제 |
| TODO 6 | `controller/LoanController.java` | `GET /loans/{id}` 상세 |
| TODO 7 | `controller/LoanController.java` | `POST /{id}/status` — PathVariable id |
| TODO 8 | `controller/LoanController.java` | `POST /{id}/status` — RequestParam status |
| TODO 9 | `controller/LoanController.java` | `POST /{id}/delete` |
| TODO 10 | `templates/loan/detail.html` | 대출번호 제목 |
| TODO 11 | `templates/loan/detail.html` | 학생·일시·상태 |
| TODO 12 | `templates/loan/detail.html` | loanItems 테이블 |
| TODO 13 | `templates/loan/detail.html` | 상태 변경 폼 |
| TODO 14 | `templates/loan/detail.html` | 삭제 폼 |

## ✅ 완료 체크

- [ ] URL 중복 매핑 없음
- [ ] `/new` 가 `/{id}` 위
- [ ] 삭제 시 Book 유지
