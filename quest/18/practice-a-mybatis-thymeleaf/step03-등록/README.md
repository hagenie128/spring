# Step 3 — 등록 (Create)

## TODO

1. `insert(BookDTO)` + XML
2. `GET /books/new` → 빈 DTO → `book/form`
3. `POST /books` → redirect 목록
4. `th:object`, `th:field`

## form.html 최소

```html
<form th:action="@{/books}" th:object="${book}" method="post">
  <input th:field="*{title}" placeholder="제목" />
  <input th:field="*{author}" placeholder="저자" />
  <input th:field="*{price}" type="number" />
  <input th:field="*{publishedDate}" type="date" />
  <button type="submit">등록</button>
</form>
```

## 완료 기준

- [ ] 새 도서 등록 후 목록에 표시

📄 상세: [문제.md](../문제.md) 문제 3
