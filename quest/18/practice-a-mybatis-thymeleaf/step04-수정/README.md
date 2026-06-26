# Step 4 — 수정 (Update)

## TODO

1. `update(BookDTO)` + XML
2. `GET /books/{id}/edit` — 기존값 로드
3. `POST /books/{id}/edit` — redirect 상세

## Mapper XML

```xml
<update id="update">
  UPDATE books
  SET title = #{title},
      author = #{author},
      price = #{price},
      published_date = #{publishedDate}
  WHERE book_id = #{bookId}
</update>
```

## Controller

```java
@GetMapping("/{id}/edit")
public String editForm(@PathVariable Long id, Model model) {
  model.addAttribute("book", bookService.findById(id));
  return "book/edit";
}

@PostMapping("/{id}/edit")
public String edit(@PathVariable Long id, @ModelAttribute BookDTO book) {
  book.setBookId(id);
  bookService.update(book);
  return "redirect:/books/" + id;
}
```

## edit.html

`form.html` 복사 후 `th:action`만 변경:

```html
<form th:action="@{/books/{id}/edit(id=${book.bookId})}" th:object="${book}" method="post">
```

## 완료 기준

- [ ] 상세 → 수정 → 저장 → 상세에 반영

📄 상세: [문제.md](../문제.md) 문제 4
