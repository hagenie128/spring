# React 환경 설정 · npm 패키지 · 진도 가이드

> 이클립스에서 Cursor(터미널)로 넘어오면서 헷갈리는 부분 + 시험 전 뭐부터 할지 정리  
> 경로: `c:\work_spring\quest\React-환경-및-진도-가이드.md`

---

## 1. 이클립스 vs Cursor — 뭐가 다른가?

| 이클립스 | Cursor (지금) |
|----------|---------------|
| Run 버튼으로 서버 실행 | **터미널**에서 명령어 실행 |
| 프로젝트 Import | 폴더를 그냥 열면 됨 (`c:\work_spring`) |
| Servers 탭 | 터미널 2개 쓰는 게 일반적 |
| Maven/Gradle 자동 빌드 | `gradlew.bat` / `npm` 직접 입력 |
| 콘솔이 아래 패널 | 터미널 탭에서 로그 확인 |

**핵심:** 하는 일은 같고, **실행 버튼 대신 명령어**를 친다고 생각하면 됩니다.

---

## 2. npm / npm install 이 뭔가?

- **npm** = Node.js에 딸려 오는 **패키지 관리자** (라이브러리 설치·실행 도구)
- **node_modules** = `npm install` 하면 생기는 **설치된 라이브러리 폴더** (용량 큼, git에 안 올림)
- **package.json** = 이 프로젝트가 쓰는 라이브러리 목록 + 실행 스크립트

```powershell
npm install          # package.json 보고 전부 설치 (처음 1번)
npm install axios    # axios만 추가 설치 + package.json에 기록
npm i axios          # install 줄임말 (같음)
npm start            # 개발 서버 실행 (React)
```

이클립스 비유:

| npm | 이클립스 |
|-----|----------|
| `npm install` | Maven Update / Gradle Refresh |
| `npm start` | Run on Server (프론트) |
| `package.json` | `pom.xml` / `build.gradle` |

---

## 3. 자주 보는 패키지 — 각각 뭐 하는 건가?

### `axios`

- **역할:** 브라우저(React)에서 **HTTP 요청** (GET, POST, PATCH, DELETE)
- **언제:** 백엔드 API 호출, 공공 API 호출
- **예시:**

```javascript
import axios from "axios";
const res = await axios.get("http://localhost:8888/api/posts");
```

`step17-board-front`에는 **이미 설치됨** (`package.json`에 있음).

---

### `react-router-dom`

- **역할:** 페이지 **URL 이동** (`/`, `/posts/1`, `/login` 등)
- **언제:** 목록 → 상세 → 글쓰기 화면 전환
- **예시:**

```javascript
import { BrowserRouter, Routes, Route, useNavigate, useParams } from "react-router-dom";
```

`step17-board-front`에는 **이미 설치됨**.

---

### `dompurify` (DOMPurify)

- **역할:** HTML 문자열에서 **위험한 태그/스크립트 제거** (XSS 방지)
- **언제:** Quill 등 에디터로 쓴 HTML을 화면에 넣을 때
- **왜 필요?** `dangerouslySetInnerHTML`은 HTML을 그대로 넣기 때문에, 악성 스크립트가 들어갈 수 있음

```javascript
import DOMPurify from "dompurify";

<div dangerouslySetInnerHTML={{ __html: DOMPurify.sanitize(post.content) }} />
```

**현재 `step17-board-front` 상태:**

| 항목 | 상태 |
|------|------|
| `package.json`에 dompurify | ❌ 없음 |
| 코드에서 DOMPurify 사용 | ❌ 없음 |
| 상세 페이지 | `dangerouslySetInnerHTML`만 사용 중 |
| 교수님 git (`teacher/main`) | dompurify 없음 |

→ **설치 안 해도 지금 게시판은 돌아갑니다.**  
→ 공유자료에 있었다면 **보안 권장 사항**으로 넣은 것일 가능성이 큼.  
→ 시험에서 HTML 출력 요구가 있으면 설치 + `sanitize` 쓰는 게 좋음.

**설치 방법 (필요할 때만):**

```powershell
cd c:\work_spring\step17-board-front
npm install dompurify
```

설치 후 `PostDetailPage.jsx`에서 위처럼 `DOMPurify.sanitize()`로 감싸면 됩니다.

---

### `quill`

- **역할:** 리치 텍스트 **에디터** (굵게, 목록, 이미지 등)
- `step17-board-front`에 **이미 설치됨**

---

## 4. React 폴더를 받았을 때 — 셋팅 순서

Git/압축/USB로 `step17-board-front` 같은 폴더만 받은 경우:

### ① Node.js 설치 확인

```powershell
node -v
npm -v
```

숫자가 나오면 OK. 안 나오면 [nodejs.org](https://nodejs.org) LTS 설치.

### ② 프로젝트 폴더로 이동

```powershell
cd c:\work_spring\step17-board-front
```

### ③ 의존성 설치 (가장 중요!)

```powershell
npm install
```

- `node_modules`가 없거나 비어 있으면 **반드시** 실행
- `package.json`에 적힌 axios, react, quill 등 전부 다운로드

### ④ 환경 변수 파일 (`.env`)

프로젝트 루트에 `.env` 파일 생성 (없으면):

```
REACT_APP_API_URL=http://localhost:8888
```

- `REACT_APP_` 로 시작해야 React가 읽음
- **수정 후에는 `npm start` 다시** 해야 반영됨
- API 키·비밀번호는 git에 올리지 않기

### ⑤ 실행

```powershell
npm start
```

브라우저 `http://localhost:3000` 자동 열림.

### ⑥ 백엔드가 필요한 프로젝트면 (step17)

**터미널 2** — 백엔드:

```powershell
cd c:\work_spring\step17-board-backend
.\gradlew.bat bootRun
```

MySQL `new_board_db` 실행 중이어야 함.

---

## 5. 자주 하는 실수

| 증상 | 원인 | 해결 |
|------|------|------|
| `Module not found: axios` | `npm install` 안 함 | 폴더에서 `npm install` |
| API 연결 안 됨 | 백엔드 안 켬 / `.env` 없음 | `bootRun` + `.env` 확인 |
| `.env` 바꿨는데 반영 안 됨 | 서버 재시작 안 함 | Ctrl+C 후 `npm start` |
| `EADDRINUSE 3000` | 포트 이미 사용 중 | 기존 터미널 종료 |
| `node_modules` 용량 큼 | 정상 | 삭제 후 `npm install`로 복구 가능 |

---

## 6. 시험 전 — 뭐부터 할지 (우선순위)

안 푼 quest가 많아도 **전부 할 필요 없음**. 내일 시험 기준:

### 1순위 — 과제 2: 공공 API

- 폴더: `quest/20-openapi-practice/exam-tourism-gw`
- JWT·백엔드 **없음**
- React + Axios + 검색 + 목록 + loading/error

```powershell
npx create-react-app tourism-search
cd tourism-search
npm install axios
# .env에 REACT_APP_TOUR_API_KEY=...
npm start
```

### 2순위 — 과제 1: Spring Thymeleaf CRUD

- 폴더: `quest/18/practice-a-mybatis-thymeleaf`
- step01~05 (목록·상세·등록·수정·삭제)

### 3순위 — quest 19 (React JWT)

- `step17-board-front` / `step17-board-backend`가 **완성본**
- 처음부터 다시 안 해도 됨. 막힌 step만 참고

### 지금은 건너뛰어도 됨

- quest 02~10, 12~17 (기초·조각 연습)
- OAuth (quest 16 problem03)

---

## 7. 실행 패턴 치트시트

### 게시판 (step17)

```powershell
# 터미널 1
cd c:\work_spring\step17-board-backend
.\gradlew.bat bootRun

# 터미널 2
cd c:\work_spring\step17-board-front
npm install    # 처음만
npm start
```

### 공공 API 연습 (시험 과제 2)

```powershell
cd tourism-search
npm install axios
npm start
# 백엔드 없음
```

### Thymeleaf CRUD (시험 과제 1)

```powershell
cd 만든-spring-프로젝트
.\gradlew.bat bootRun
# 보통 http://localhost:8080
```

---

## 8. 오늘 수업 핵심 (step17) 요약

| 주제 | 기억할 것 |
|------|-----------|
| 등록 후 이동 | `navigate(\`/posts/${res.data.board.bno}\`)` |
| 수정 후 이동 | URL의 `bno` 사용 (PATCH는 204) |
| MyBatis INSERT | `useGeneratedKeys="true" keyProperty="bno"` |
| Quill state | `setForm(prev => ({...prev, content}))` |
| HTML 출력 | `dangerouslySetInnerHTML` (+ 선택: DOMPurify) |
| 삭제 확인 | `window.confirm` |

---

## 9. 관련 문서 링크

| 문서 | 내용 |
|------|------|
| [PRACTICE-GUIDE-EXAM.html](PRACTICE-GUIDE-EXAM.html) | 내일 시험 2과제 HTML 가이드 |
| [quest/20-openapi-practice/README.md](20-openapi-practice/README.md) | 공공 API 연습 |
| [quest/18/README.md](18/README.md) | Thymeleaf CRUD |
| [quest/19-react-jwt-practice/README.md](19-react-jwt-practice/README.md) | React JWT 단계별 |

---

*마지막 업데이트: 2026-06-29*
