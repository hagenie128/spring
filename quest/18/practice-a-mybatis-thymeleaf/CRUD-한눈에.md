# Thymeleaf + MyBatis CRUD 한눈에

> 도메인이 **학생/도서/메뉴** 무엇이든 **이 표의 역할**만 같으면 됩니다.

---

## 계층 구조

```
Browser → BookController → BookService → BookMapper.java → book-mapper.xml → MySQL
                                              ↓
                                    templates/book/*.html
```

---

## Mapper 메서드 5종 (암기)

| CRUD | Mapper 메서드 | XML |
|------|---------------|-----|
| **R** 목록 | `findAll()` | `SELECT * FROM books ORDER BY book_id DESC` |
| **R** 상세 | `findById(id)` | `WHERE book_id = #{bookId}` |
| **C** 등록 | `insert(dto)` | `INSERT INTO books (...)` |
| **U** 수정 | `update(dto)` | `UPDATE books SET ... WHERE book_id = #{bookId}` |
| **D** 삭제 | `deleteById(id)` | `DELETE FROM books WHERE book_id = #{bookId}` |

---

## Controller URL 7개 (암기)

| 기능 | Method | URL | 반환 |
|------|--------|-----|------|
| 목록 | GET | `/books` | `book/list` |
| 상세 | GET | `/books/{id}` | `book/detail` |
| 등록 폼 | GET | `/books/new` | `book/form` |
| 등록 처리 | POST | `/books` | `redirect:/books` |
| 수정 폼 | GET | `/books/{id}/edit` | `book/edit` |
| 수정 처리 | POST | `/books/{id}/edit` | `redirect:/books/{id}` |
| 삭제 처리 | POST | `/books/{id}/delete` | `redirect:/books` |

학생관리: `books` → `students`, `bookId` → `studentId`

---

## Thymeleaf 핵심 문법

| 용도 | 문법 |
|------|------|
| 반복 | `th:each="book : ${books}"` |
| 출력 | `th:text="${book.title}"` |
| 링크 | `th:href="@{/books/{id}(id=${book.bookId})}"` |
| 폼 객체 | `th:object="${book}"` |
| 입력 바인딩 | `th:field="*{title}"` |
| POST URL | `th:action="@{/books/{id}/delete(id=${book.bookId})}"` |

---

## 등록 vs 수정 폼

| | 등록 | 수정 |
|---|------|------|
| 폼 URL | `th:action="@{/books}"` | `th:action="@{/books/{id}/edit(id=${book.bookId})}"` |
| method | `post` | `post` |
| DTO | 빈 객체 `new BookDTO()` | `findById`로 채운 객체 |

---

## 구현 순서 (시험 당일)

```
1. schema.sql / 테이블
2. BookDTO + @Alias
3. BookMapper.java (5메서드)
4. book-mapper.xml (5쿼리)
5. BookService
6. BookController (7URL)
7. list.html → detail.html → form.html → edit.html
```

**90분 목표:** 1~5번 40분 + 6~7번 50분

---

## 자주 하는 실수

| 실수 | 증상 |
|------|------|
| `@MapperScan` 누락 | Mapper bean 없음 |
| redirect에 `.html` | 404 |
| `th:field` 없이 name만 | 수정 시 값 안 넘어감 |
| PK 컬럼명 불일치 | 조회 null |
| 삭제를 GET 링크로 | 위험 + 시험에서 POST form 요구 |

---

## 참고 완성본

`quest/11/car-crud-project`

📋 단계별: [step01-목록](step01-목록/) ~ [step05-삭제](step05-삭제/)
