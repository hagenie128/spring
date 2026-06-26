# Step 11 — 페이징 (React)

> **선행:** 게시글 **300건** → [sample_data/seed_board_pagination.py](../../../sample_data/seed_board_pagination.py)

```powershell
cd c:\work_spring\sample_data
python seed_board_pagination.py
```

---

## API

```
GET /api/posts?page=1&size=20

응답:
{
  "list": [ ... 20건 ],
  "pagging": {
    "currentPage": 1,
    "startPageOfPageGroup": 1,
    "endPageOfPageGroup": 5,
    ...
  }
}
```

300 ÷ 20 = **15페이지**

---

## TODO

### 1. `postApi.getPage(page, keyword, size)` — 이미 Step 1

### 2. `PaggingBar.jsx` (step17-board-front 참고)

```javascript
// pagging.startPageOfPageGroup ~ endPageOfPageGroup 버튼
// onPageChange(pageNo) → postApi.getPage(pageNo, '', 20)
```

### 3. `PostListPage.jsx`

```javascript
const [pagging, setPagging] = useState({});

postApi.getPage(pageNo, '', 20).then(res => {
  setPosts(res.data.list);
  setPagging(res.data.pagging);
});
```

---

## 완료 기준

- [ ] 1페이지 20건
- [ ] 페이지 버튼 클릭 시 다른 글
- [ ] ◀▶ 그룹 이동

📋 [sample_data/README.md](../../../sample_data/README.md)
