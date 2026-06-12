# quest/12 — Spring Boot JPA Join + MVC 연습

> Spring Boot 3.4 | Java 17 | JPA | Thymeleaf | H2  
> **참고 완성본:** [step13-boot-jpa-join](../step13-boot-jpa-join) — 패턴은 같고 도메인만 **도서관 대출**입니다.

---

## 문제 목록

| 문제 | 폴더 | 난이도 | 핵심 주제 | TODO |
|------|------|--------|-----------|------|
| 01 | [problem01/](problem01/) | ★★☆☆ | 연관관계 + JOIN FETCH | 1~9 |
| 02 | [problem02/](problem02/) | ★★★☆ | `@RequestParam` 검색 | 1~9 |
| 03 | [problem03/](problem03/) | ★★★☆ | 등록 폼 + List 파라미터 | 1~11 |
| 04 | [problem04/](problem04/) | ★★★★ | 상세 · 상태 · 삭제 | 1~14 |

**실행:** `cd quest/12/problem0N` → `gradlew.bat bootRun`  
**main:** [Problem01Application](problem01/src/main/java/com/spring/Problem01Application.java) 등  
**URL:** http://localhost:8080/loans

---

## 문제 1 — 연관관계 & JOIN FETCH (★★☆☆)

**폴더:** [problem01/](problem01/)

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | [LoanItem.java](problem01/src/main/java/com/spring/entity/LoanItem.java) | Loan과 연관관계 매핑 |
| TODO 2 | [Loan.java](problem01/src/main/java/com/spring/entity/Loan.java) | LoanItem 목록 연관관계 매핑 |
| TODO 3 | [Loan.java](problem01/src/main/java/com/spring/entity/Loan.java) | addLoanItem 편의 메서드 |
| TODO 4 | [Loan.java](problem01/src/main/java/com/spring/entity/Loan.java) | 대출일 자동 설정 |
| TODO 5 | [LoanRepository.java](problem01/src/main/java/com/spring/repository/LoanRepository.java) | JOIN FETCH 조회 쿼리 |
| TODO 6 | [LoanService.java](problem01/src/main/java/com/spring/service/LoanService.java) | 목록 조회 서비스 |
| TODO 7 | [LoanController.java](problem01/src/main/java/com/spring/controller/LoanController.java) | GET /loans 핸들러 |
| TODO 8 | [list.html](problem01/src/main/resources/templates/loan/list.html) | 목록 반복 출력 |
| TODO 9 | [list.html](problem01/src/main/resources/templates/loan/list.html) | 학생명·권수·대출일 출력 |

**참고만 (수정 X):** [Student.java](problem01/src/main/java/com/spring/entity/Student.java) · [Book.java](problem01/src/main/java/com/spring/entity/Book.java) · [DataInitializer.java](problem01/src/main/java/com/spring/DataInitializer.java)

**step13 참고:** [Order.java](../step13-boot-jpa-join/src/main/java/com/spring/entity/Order.java) · [OrderItem.java](../step13-boot-jpa-join/src/main/java/com/spring/entity/OrderItem.java) · [OrderRepository.java](../step13-boot-jpa-join/src/main/java/com/spring/repository/OrderRepository.java) · [list.html](../step13-boot-jpa-join/src/main/resources/templates/order/list.html)

**완성 기준:** `/loans` 에 대출 3건 + 학생명 표시, N+1 없음

---

## 문제 2 — 검색 필터 @RequestParam (★★★☆)

**폴더:** [problem02/](problem02/)  
**entity 폴더는 완성됨 — 아래만 수정**

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | [LoanRepository.java](problem02/src/main/java/com/spring/repository/LoanRepository.java) | search 쿼리 — 학생 조건 |
| TODO 2 | [LoanRepository.java](problem02/src/main/java/com/spring/repository/LoanRepository.java) | search 쿼리 — 상태 조건 |
| TODO 3 | [LoanService.java](problem02/src/main/java/com/spring/service/LoanService.java) | search 서비스 |
| TODO 4 | [LoanController.java](problem02/src/main/java/com/spring/controller/LoanController.java) | studentId 파라미터 받기 |
| TODO 5 | [LoanController.java](problem02/src/main/java/com/spring/controller/LoanController.java) | status 파라미터 받기 |
| TODO 6 | [LoanController.java](problem02/src/main/java/com/spring/controller/LoanController.java) | 검색 결과 model 담기 |
| TODO 7 | [LoanController.java](problem02/src/main/java/com/spring/controller/LoanController.java) | select용 model 담기 |
| TODO 8 | [list.html](problem02/src/main/resources/templates/loan/list.html) | 학생 select |
| TODO 9 | [list.html](problem02/src/main/resources/templates/loan/list.html) | 상태 select |

**step13 참고:** [OrderController.java](../step13-boot-jpa-join/src/main/java/com/spring/controller/OrderController.java) · [OrderRepository.java](../step13-boot-jpa-join/src/main/java/com/spring/repository/OrderRepository.java) · [list.html](../step13-boot-jpa-join/src/main/resources/templates/order/list.html)

**완성 기준:** 학생/상태 필터 + 검색 후 선택값 유지

---

## 문제 3 — 대출 등록 폼 (★★★☆)

**폴더:** [problem03/](problem03/)  
**목록·검색은 완성 — 아래만 수정**

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | [LoanService.java](problem03/src/main/java/com/spring/service/LoanService.java) | save — 학생 조회·Loan 생성 |
| TODO 2 | [LoanService.java](problem03/src/main/java/com/spring/service/LoanService.java) | save — 수량 0 건너뛰기 |
| TODO 3 | [LoanService.java](problem03/src/main/java/com/spring/service/LoanService.java) | save — 도서 항목 추가 |
| TODO 4 | [LoanService.java](problem03/src/main/java/com/spring/service/LoanService.java) | save — 빈 대출 예외 처리 |
| TODO 5 | [LoanService.java](problem03/src/main/java/com/spring/service/LoanService.java) | save — 저장 후 반환 |
| TODO 6 | [LoanController.java](problem03/src/main/java/com/spring/controller/LoanController.java) | GET /loans/new |
| TODO 7 | [LoanController.java](problem03/src/main/java/com/spring/controller/LoanController.java) | POST — studentId 받기 |
| TODO 8 | [LoanController.java](problem03/src/main/java/com/spring/controller/LoanController.java) | POST — bookIds 받기 |
| TODO 9 | [LoanController.java](problem03/src/main/java/com/spring/controller/LoanController.java) | POST — quantities·redirect |
| TODO 10 | [form.html](problem03/src/main/resources/templates/loan/form.html) | 학생 select |
| TODO 11 | [form.html](problem03/src/main/resources/templates/loan/form.html) | 도서별 수량 입력 |

**step13 참고:** [OrderService.java](../step13-boot-jpa-join/src/main/java/com/spring/service/OrderService.java) · [OrderController.java](../step13-boot-jpa-join/src/main/java/com/spring/controller/OrderController.java) · [form.html](../step13-boot-jpa-join/src/main/resources/templates/order/form.html)

**완성 기준:** 등록 성공/실패 redirect 동작

---

## 문제 4 — 대출 CRUD 종합 (★★★★)

**폴더:** [problem04/](problem04/)  
**목록·등록은 완성 — 아래만 수정**

| TODO | 열어볼 파일 | 할 일 |
|------|------------|-------|
| TODO 1 | [LoanRepository.java](problem04/src/main/java/com/spring/repository/LoanRepository.java) | 상세 조회 — student fetch |
| TODO 2 | [LoanRepository.java](problem04/src/main/java/com/spring/repository/LoanRepository.java) | 상세 조회 — items·book fetch |
| TODO 3 | [LoanService.java](problem04/src/main/java/com/spring/service/LoanService.java) | 상세 조회 서비스 |
| TODO 4 | [LoanService.java](problem04/src/main/java/com/spring/service/LoanService.java) | 상태 변경 |
| TODO 5 | [LoanService.java](problem04/src/main/java/com/spring/service/LoanService.java) | 대출 삭제 |
| TODO 6 | [LoanController.java](problem04/src/main/java/com/spring/controller/LoanController.java) | GET /loans/{id} |
| TODO 7 | [LoanController.java](problem04/src/main/java/com/spring/controller/LoanController.java) | POST 상태변경 — id |
| TODO 8 | [LoanController.java](problem04/src/main/java/com/spring/controller/LoanController.java) | POST 상태변경 — status |
| TODO 9 | [LoanController.java](problem04/src/main/java/com/spring/controller/LoanController.java) | POST 삭제 |
| TODO 10 | [detail.html](problem04/src/main/resources/templates/loan/detail.html) | 대출번호 표시 |
| TODO 11 | [detail.html](problem04/src/main/resources/templates/loan/detail.html) | 기본 정보 표시 |
| TODO 12 | [detail.html](problem04/src/main/resources/templates/loan/detail.html) | 대출 도서 목록 |
| TODO 13 | [detail.html](problem04/src/main/resources/templates/loan/detail.html) | 상태 변경 폼 |
| TODO 14 | [detail.html](problem04/src/main/resources/templates/loan/detail.html) | 삭제 폼 |

**step13 참고:** [OrderRepository.java](../step13-boot-jpa-join/src/main/java/com/spring/repository/OrderRepository.java) · [OrderController.java](../step13-boot-jpa-join/src/main/java/com/spring/controller/OrderController.java) · [detail.html](../step13-boot-jpa-join/src/main/resources/templates/order/detail.html)

### ✅ 완료 체크

- [ ] URL 중복 매핑 없음
- [ ] /new 가 /{id} 위에 있음
- [ ] 삭제해도 Book 데이터 유지

---

## step13 ↔ quest12 도메인

| step13 | quest12 |
|--------|---------|
| [Member](../step13-boot-jpa-join/src/main/java/com/spring/entity/Member.java) | [Student](problem01/src/main/java/com/spring/entity/Student.java) |
| [MenuItem](../step13-boot-jpa-join/src/main/java/com/spring/entity/MenuItem.java) | [Book](problem01/src/main/java/com/spring/entity/Book.java) |
| [Order](../step13-boot-jpa-join/src/main/java/com/spring/entity/Order.java) | [Loan](problem01/src/main/java/com/spring/entity/Loan.java) |
| [OrderItem](../step13-boot-jpa-join/src/main/java/com/spring/entity/OrderItem.java) | [LoanItem](problem01/src/main/java/com/spring/entity/LoanItem.java) |

---

## 팁

- 소스 파일 안에 `TODO N` 주석이 있습니다 — README 번호와 맞춰 풀세요
- Cursor에서는 `@RequestParam` 이름을 직접 적는 습관 권장
- 같은 URL에 메서드 2개 등록 금지

**진행 순서:** [problem01](problem01/) → [problem02](problem02/) → [problem03](problem03/) → [problem04](problem04/)
