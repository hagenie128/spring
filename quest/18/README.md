# Quest 18 — 다음 주 실습 대비 (프로젝트 직접 구성)

> **실습 1** Spring Boot + MyBatis + Thymeleaf — CRUD (학생관리와 같은 짜임)  
> **실습 2** React — JWT 로그인 + API 데이터 읽기

교수님이 **주제(학생/도서/메뉴 등)와 API 명세를 시험 당일에 주실 수 있으므로**,  
정답 코드 암기보다 **프로젝트 뼈대를 혼자 세우고 CRUD/JWT 패턴을 바꿔 끼우는 연습**이 목표입니다.

> **📖 다음주 시험:** [PRACTICE-GUIDE-EXAM.html](../PRACTICE-GUIDE-EXAM.html) — 학생 CRUD + 관광 API

> **📖 연습 가이드 (HTML):** [PRACTICE-GUIDE.html](PRACTICE-GUIDE.html) — 문제 / 힌트 / 전체코드 클릭해서 열기

> **📖 한 번에 보기:** [PRACTICE-GUIDE.html](PRACTICE-GUIDE.html) — 브라우저에서 열고 스크롤하며 연습

---

## 연습 구성

| 실습 | 폴더 | 연습 주제 (예시) | 참고 완성본 |
|------|------|------------------|-------------|
| **A** | [practice-a-mybatis-thymeleaf](practice-a-mybatis-thymeleaf/) | **도서 관리 CRUD 전체** (R·C·U·D) | `quest/11/car-crud-project` |
| **B** | [practice-b-react-jwt](practice-b-react-jwt/) + [quest/19](../19-react-jwt-practice/) | JWT + API **CRUD** | `step17-board-front` |

---

## 추천 일정

### 시간이 충분할 때 (1주)

| 일 | 할 일 | 시간 |
|----|--------|------|
| 1일차 | [A-00 프로젝트 만들기](practice-a-mybatis-thymeleaf/00-프로젝트-만들기.md) | 1~2h |
| 2~3일차 | [A Step 1~6](practice-a-mybatis-thymeleaf/체크리스트.md) **CRUD 전체** | 4~5h |
| 4~6일차 | [quest/19](../19-react-jwt-practice/) React **CRUD** | 4~5h |

### ⏱ 이틀 × 3시간 → [2일-3시간-플랜.md](2일-3시간-플랜.md)

| 옵션 | 1일차 | 2일차 |
|------|-------|-------|
| **2일 압축** | Thymeleaf **CRUD 전체** | React CRUD ([quest/19](../19-react-jwt-practice/)) |
| **3일 추천** | Thymeleaf R+C | Thymeleaf U+D + React |

### Practice A Step (Thymeleaf CRUD)

| Step | 내용 |
|------|------|
| 1~2 | **R** 목록·상세 |
| 3 | **C** 등록 |
| 4 | **U** 수정 |
| 5 | **D** 삭제 |
| 6 | 90분 종합 |
| 7 | **페이징** ([step07](practice-a-mybatis-thymeleaf/step07-페이징/)) |

**INSERT 후 PK 받기 (오늘 수업 연계):**

```xml
<insert id="insert" useGeneratedKeys="true" keyProperty="bookId">
```

등록 후 `redirect:/books/{bookId}` 에 쓸 때 필요합니다.

📋 [CRUD-한눈에](practice-a-mybatis-thymeleaf/CRUD-한눈에.md) · [체크리스트](practice-a-mybatis-thymeleaf/체크리스트.md)

**샘플 300건:** [sample_data/](../sample_data/) → `python seed_books.py`

### React CRUD → [quest/19-react-jwt-practice](../19-react-jwt-practice/)

### 공공 API 실기 (내일 과제 2) → [quest/20-openapi-practice](../20-openapi-practice/)

---

## 실습 당일 체크리스트 (공통)

### 시작 전 10분

- [ ] JDK / Node / MySQL 버전 확인
- [ ] Spring Initializr 또는 `create-react-app` 북마크
- [ ] Postman 또는 REST Client 준비
- [ ] 빈 폴더에 프로젝트 생성 (복붙 금지 연습했다면 그대로 진행)

### 문제지 받은 후 5분

- [ ] **테이블 컬럼** → Entity/DTO 필드명(camelCase) 매핑表 작성
- [ ] **URL 목록** → Controller `@GetMapping` / `@PostMapping` 표 작성
- [ ] **API Base URL + 인증 여부** 표 작성 (React 실습)

---

## 학생관리가 아니어도 되는 이유

CRUD 실습의 본질은 **도메인 이름**이 아니라 아래 패턴입니다.

```
Controller → Service → Mapper(Interface) → Mapper.xml → DB
Thymeleaf: list / detail / form / edit.html
```

| 시험 주제 예시 | 바꿀 것 | 그대로 쓰는 것 |
|----------------|---------|----------------|
| 학생 관리 | 테이블, 필드, URL `/students` | CRUD URL 패턴, MyBatis XML 구조 |
| 도서 관리 | `books`, `title`, `author` | `findAll`, `findById`, `insert`, `update`, `delete` |
| 메뉴 관리 | `menus`, `price`, `category` | Thymeleaf `th:each`, `th:field` |
| 사원 관리 | `employees`, `dept` | `@Controller` + `redirect:` |

React도 동일합니다.

```
axiosInstance → xxxApi → Page → useEffect → setState → 화면
```

API가 `/api/students`든 `/api/books`든 **폴더 구조와 인증 흐름은 같습니다.**

---

## 시험 당일 시간 배분 (참고)

| 실습 | 권장 시간 | 순서 |
|------|-----------|------|
| A (Thymeleaf CRUD) | 60~90분 | DB → Mapper → Service → Controller → HTML |
| B (React JWT) | 60~90분 | CRA → axios → login → token → 목록 API → 화면 |

**팁:** B는 백엔드가 이미 있으면 Postman으로 API 먼저 확인한 뒤 프론트를 만드세요.
