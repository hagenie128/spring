# Step 5 — 삭제 (Delete)

## TODO

1. `deleteById(Long bookId)` + XML
2. `POST /books/{id}/delete` → redirect 목록
3. `detail.html`에 삭제 form

## Mapper XML

```xml
<delete id="deleteById">
  DELETE FROM books WHERE book_id = #{bookId}
</delete>
```

## Controller

```java
@PostMapping("/{id}/delete")
public String delete(@PathVariable Long id) {
  bookService.deleteById(id);
  return "redirect:/books";
}
```

## detail.html — 삭제 form

```html
<form th:action="@{/books/{id}/delete(id=${book.bookId})}" method="post"
      onsubmit="return confirm('삭제하시겠습니까?');">
  <button type="submit">삭제</button>
</form>
```

⚠️ 삭제는 **GET 링크가 아니라 POST form**

## 완료 기준

- [ ] 삭제 후 목록에서 사라짐
- [ ] confirm 취소 시 유지

📄 상세: [문제.md](../문제.md) 문제 5

## 다음

[Step 6 — CRUD 종합](../step06-crud-종합/)
