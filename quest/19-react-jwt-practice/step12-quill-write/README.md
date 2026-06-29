# Step 12 — Quill 에디터 + 글쓰기/수정 (보너스)

> 오늘 수업에서 다룬 **Quill + PostWritePage** 연습  
> 백엔드: `step17-board-backend` 그대로 사용

---

## 배울 개념

- `quill` 패키지 설치
- `QuillEditor` 컴포넌트 분리
- 글쓰기/수정 페이지 **겸용** (`/posts/create`, `/posts/:bno/edit`)
- `useState` + `prevForm` 패턴
- 등록 후 `res.data.board.bno`로 상세 이동
- 수정 후 URL의 `bno`로 상세 이동 (PATCH는 204)

---

## 준비

```powershell
cd step17-board-front   # 또는 연습용 React 프로젝트
npm install quill
```

백엔드 `board-mapper.xml`에 아래가 있어야 등록 후 글번호를 받을 수 있습니다.

```xml
<insert id="insertBoard" useGeneratedKeys="true" keyProperty="bno">
```

---

## TODO

### 1. `QuillEditor.jsx`

- Quill 인스턴스는 **최초 1회만** 생성 (`useEffect([])`)
- `text-change` → `onChange(HTML)` 호출
- 수정 모드 초기 본문은 **최초 1회만** `innerHTML` 반영
- ❌ 타이핑할 때마다 `defaultValue`를 다시 넣으면 **커서가 끝으로 튐**

### 2. `PostWritePage.jsx`

```javascript
const { bno } = useParams();
const isEditorMode = !!bno;

// 제목/내용 state
const [form, setForm] = useState({ title: "", content: "" });

// 내용 변경 — prevForm 사용!
const onChangePostDetail = (html) => {
  setForm(prev => ({ ...prev, content: html }));
};

// 수정 모드: 기존 글 1회 로드
useEffect(() => {
  if (!isEditorMode) return;
  postApi.getPost(bno).then(res => {
    setForm({
      title: res.data.board.title,
      content: res.data.board.content,
    });
  });
}, [bno, isEditorMode]);

// 저장
if (isEditorMode) {
  await postApi.update(bno, form);
  navigate(`/posts/${bno}`);          // PATCH → 204, bno는 URL에서
} else {
  const res = await postApi.create(form);
  navigate(`/posts/${res.data.board.bno}`);  // POST → board.bno
}
```

### 3. 라우트

```jsx
<Route path="/posts/create" element={<PostWritePage />} />
<Route path="/posts/:bno/edit" element={<PostWritePage />} />
```

### 4. 상세 HTML 출력

```jsx
<div dangerouslySetInnerHTML={{ __html: post.content }} />
```

---

## 완료 기준

- [ ] Quill로 굵게/목록 등 서식 입력 가능
- [ ] 타이핑해도 커서가 도망가지 않음
- [ ] 글 등록 후 해당 글 상세로 이동
- [ ] 수정 후 같은 글 상세로 이동
- [ ] 상세에서 HTML 서식이 보임

---

## 흔한 실수

| 실수 | 증상 |
|------|------|
| `setForm({...form, content})` + stale closure | 제목이 사라짐 |
| `useEffect([defaultValue])`로 Quill 재설정 | 커서가 맨 끝으로 튐 |
| `res.data.bno` 사용 | `undefined` → `/posts/0` |
| `useGeneratedKeys` 없음 | `board.bno`가 0 |

---

## 다음

[Step 13 — 반응·삭제·작성자 버튼](../step13-reaction-owner/) · [quest/20 공공 API 연습](../../20-openapi-practice/)
