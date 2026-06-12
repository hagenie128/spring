# 문제 1 — 연관관계 & JOIN FETCH (★★☆☆)

**실행:** `gradlew.bat bootRun` → `Problem01Application`  
**URL:** http://localhost:8080/loans

## 수정할 파일 — TODO 순서대로

| TODO | 수정 파일 | 내용 |
|------|-----------|------|
| TODO 1 | `entity/LoanItem.java` | `loan` 필드 `@ManyToOne(LAZY)` + `@JoinColumn(name="loan_id")` |
| TODO 2 | `entity/Loan.java` | `loanItems` `@OneToMany(mappedBy, cascade, orphanRemoval)` |
| TODO 3 | `entity/Loan.java` | `addLoanItem()` 양방향 편의 메서드 |
| TODO 4 | `entity/Loan.java` | `@PrePersist` → `loanDate` 자동 설정 |
| TODO 5 | `repository/LoanRepository.java` | `join fetch l.student` JPQL |
| TODO 6 | `service/LoanService.java` | `findAllWithStudent()` 구현 |
| TODO 7 | `controller/LoanController.java` | `GET /loans` — model + view |
| TODO 8 | `templates/loan/list.html` | `th:each` 목록 반복 |
| TODO 9 | `templates/loan/list.html` | 학생명·권수·대출일 출력 |

> 경로 기준: `src/main/java/com/spring/` / `src/main/resources/`

**건드리지 않아도 되는 파일:** `Student.java`, `Book.java`, `LoanStatus.java`, `DataInitializer.java`

**완성 기준:** `GET /loans` 대출 3건 + N+1 없음
