# Step 7 — 페이징 (MyBatis + Thymeleaf)

> **선행:** 도서 **300건** 이상 → [sample_data/seed_books.py](../../../sample_data/seed_books.py)

```powershell
cd c:\work_spring\sample_data
pip install -r requirements.txt
python seed_books.py
```

300건 ÷ 페이지당 10권 = **30페이지** 연습 가능

---

## 배울 개념

- `ROW_NUMBER()` + `ceil(rw / size)` (step17 board-mapper 와 동일 패턴)
- `@RequestParam page`, `size`
- `PaggingVO` — 페이지 그룹 버튼
- Thymeleaf 페이징 링크

---

## TODO

### 1. `BookMapper.java`

```java
List<BookDTO> findPage(@Param("page") int page, @Param("size") int size);
int countAll();
```

### 2. `book-mapper.xml`

```xml
<select id="findPage" resultType="book">
  SELECT * FROM (
    SELECT ROW_NUMBER() OVER (ORDER BY book_id DESC) AS rw, b.*
    FROM books b
  ) bv
  WHERE CEIL(bv.rw / #{size}) = #{page}
</select>

<select id="countAll" resultType="int">
  SELECT COUNT(*) FROM books
</select>
```

### 3. `PaggingVO.java` (step17 복사 또는 간단 버전)

- `PAGE_CONTENT_COUNT` = **10** (한 페이지 10권)
- `getTotalPage()`, `getStartPageOfPageGroup()` 등

### 4. `BookController`

```java
@GetMapping
public String list(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size,
    Model model
) {
  model.addAttribute("books", bookService.findPage(page, size));
  model.addAttribute("pagging", new PaggingVO(bookService.countAll(), page));
  return "book/list";
}
```

### 5. `list.html` 페이징

```html
<!-- page 1 ~ endPageOfPageGroup 링크 -->
<a th:each="p : ${#numbers.sequence(pagging.startPageOfPageGroup, pagging.endPageOfPageGroup)}"
   th:href="@{/books(page=${p}, size=10)}"
   th:text="${p}"></a>
```

또는 step17 `PaggingBar` 와 같은 버튼 패턴

---

## 완료 기준

- [ ] 1페이지에 10권만 표시
- [ ] 2페이지 클릭 시 다른 10권
- [ ] 마지막 페이지(30)까지 이동 가능
- [ ] `SELECT COUNT(*)` = 300

---

## React 페이징 (step17)

같은 DB `seed_board_pagination.py` → [quest/19 step11](../../../19-react-jwt-practice/step11-페이징/)

📋 [sample_data/README.md](../../../sample_data/README.md)
