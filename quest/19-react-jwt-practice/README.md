# Quest 19 — React JWT + API + **CRUD** 단계별 연습

> **목표:** React를 **처음부터 직접 타이핑**하면서 JWT 로그인 + **게시글 CRUD 전체**를 익힌다.  
> **백엔드:** `step17-board-backend` (포트 8888) — 직접 만들 필요 없음  
> **참고 완성본:** `step17-board-front` (막힐 때만 열기)

> **📖 통합 가이드:** [../PRACTICE-GUIDE.html](../PRACTICE-GUIDE.html) — 문제·힌트·전체코드 (클릭해서 열기)

> **📖 Quest 18·19 통합 HTML:** [../PRACTICE-GUIDE.html](../PRACTICE-GUIDE.html)

---

## 시작 전 준비 (10분)

```powershell
# 터미널 1 — 백엔드
cd step17-board-backend
.\gradlew.bat bootRun

# 터미널 2 — 연습용 React (새 프로젝트)
npx create-react-app react-jwt-lab
cd react-jwt-lab
npm install axios react-router-dom
```

프로젝트 루트에 `.env` 파일:

```
REACT_APP_API_URL=http://localhost:8888
```

Postman으로 회원가입/로그인 한 번 해 두기 → [requests.http](requests.http)

**페이징 연습 데이터 300건:**

```powershell
cd c:\work_spring\sample_data
pip install -r requirements.txt
python seed_board_pagination.py
```

📄 [sample_data/README.md](../../sample_data/README.md)

---

## 단계별 연습 (순서대로!)

| Step | 폴더 | 시간 | 배우는 것 | CRUD |
|------|------|------|-----------|------|
| 1 | [step01-axios-목록](step01-axios-목록/) | 40분 | Axios, useEffect | **R** 목록 |
| 2 | [step02-router](step02-router/) | 30분 | Router, Link | |
| 3 | [step03-login](step03-login/) | 50분 | JWT, localStorage | |
| 4 | [step04-auth-context](step04-auth-context/) | 50분 | Context, useAuth | |
| 5 | [step05-상세-useParams](step05-상세-useParams/) | 40분 | useParams | **R** 상세 |
| 6 | [step06-미니과제](step06-미니과제/) | 40분 | NavBar | |
| 7 | [step07-post-create](step07-post-create/) | 40분 | POST | **C** 등록 |
| 8 | [step08-post-update](step08-post-update/) | 40분 | PATCH | **U** 수정 |
| 9 | [step09-post-delete](step09-post-delete/) | 30분 | DELETE | **D** 삭제 |
| 10 | [step10-crud-종합](step10-crud-종합/) | 60분 | 댓글 + 90분 재시험 | **C** 댓글 |
| 11 | [step11-페이징](step11-페이징/) | 40분 | PaggingBar | **R** 페이징 |

📋 CRUD 요약: [CRUD-한눈에.md](CRUD-한눈에.md)

**이틀 × 3시간:** [2일-플랜.md](2일-플랜.md) (CRUD 포함 압축版)  
**삼일 있으면:** Step 1~4 / 5~7 / 8~10 권장

---

## 폴더 구조 (Step 10 완료 시)

```
src/
├── api/
│   ├── axiosInstance.js
│   ├── authApi.js
│   ├── postApi.js      ← getPage, getPost, create, update, remove
│   └── commentApi.js   ← (Step 10)
├── context/
│   └── AuthContext.jsx
├── pages/
│   ├── PostListPage.jsx
│   ├── PostDetailPage.jsx
│   ├── PostWritePage.jsx   ← 등록 + 수정 겸용
│   └── LoginPage.jsx
├── components/
│   └── NavBar.jsx
├── App.js
└── index.js
```

---

## template/ 사용법

각 Step의 `TODO`를 채울 때 [template/](template/) 폴더의 **빈 껍데기 파일**을 복사해 시작할 수 있습니다.

```
template/step01/App.js          → Step 1
template/step03/LoginPage.jsx   → Step 3
...
```

**규칙:** `// TODO` 주석이 있는 부분만 직접 작성. 나머지는 참고.

---

## 시험 당일 React만 나올 때

1. Postman으로 API 3개 확인 (login, list, detail)
2. `axiosInstance` → `xxxApi` → `Page` → `useEffect` 순서로 생성
3. `response.data` **키 이름** 문제지에서 확인 (`list`? `data`? `items`?)
4. JWT → `localStorage` + `Bearer` 헤더
5. `useParams` 이름 = 라우트 `:이름` 일치

---

## 자주 하는 실수 TOP 5

| # | 실수 | 해결 |
|---|------|------|
| 1 | `useAuth` Provider 밖에서 호출 | `index.js`에 AuthProvider |
| 2 | useRef 값 안 꺼냄 | `.current.value` |
| 3 | 백엔드 URL로 navigate | `/login` not `/auth/login` |
| 4 | `.env` 수정 후 재시작 안 함 | `npm start` 다시 |
| 5 | 상세 403 | `useParams` 키 ≠ 라우트 파라미터 |
