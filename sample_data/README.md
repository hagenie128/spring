# sample_data — Python 샘플 데이터 (step17 · quest18 공용)

> **step17** 수업에서 쓰던 방식 그대로입니다.  
> SQL INSERT 대신 Python으로 **대량 데이터**를 넣어 페이징 연습에 사용합니다.

---

## 준비 (최초 1회)

```powershell
cd sample_data
pip install -r requirements.txt
```

MySQL 비밀번호가 다르면:

```powershell
copy config.example.py config.py
# config.py 에서 DB_PASSWORD 수정
```

---

## 스크립트 목록

| 파일 | DB | 건수(기본) | 용도 |
|------|-----|-----------|------|
| [seed_board_data.py](seed_board_data.py) | `new_board_db` | 글 1000 + 회원 100 | step17 **대량** 테스트 |
| [seed_board_pagination.py](seed_board_pagination.py) | `new_board_db` | **글 300** + 회원 20 | **페이징 연습** (React quest19) |
| [seed_books.py](seed_books.py) | `book_practice_db` | **도서 300** | quest18 Thymeleaf **페이징** |

건수 변경: 각 파일 상단 `BOARD_COUNT` / `BOOK_COUNT` 숫자만 수정.

---

## step17 — 게시판 페이징 (React)

### 1. DB 스키마

```powershell
# MySQL에서 step17-board-backend/src/main/resources/board.sql 실행
```

### 2. 샘플 데이터 300건

```powershell
cd c:\work_spring\sample_data
python seed_board_pagination.py
```

### 3. 확인

- 백엔드 실행 후: `GET http://localhost:8888/api/posts?page=1&size=20`
- `list` 20건 + `pagging` 정보
- 300건 ÷ 20 = **15페이지**

### 로그인 계정

`seed_board_pagination.py`의 회원은 **평문 비밀번호**라 JWT 로그인이 안 될 수 있습니다.  
로그인 연습은 **Postman `/auth/signup`** 으로 별도 계정을 만드세요.

---

## quest18 — 도서 Thymeleaf 페이징

### 1. 테이블

```sql
-- quest/18/practice-a-mybatis-thymeleaf/schema.sql 실행
```

### 2. 도서 300권

```powershell
cd c:\work_spring\sample_data
python seed_books.py
```

### 3. 확인

```sql
USE book_practice_db;
SELECT COUNT(*) FROM books;  -- 300
```

이후 Thymeleaf 목록 + 페이징 구현 → [step07-페이징](../quest/18/practice-a-mybatis-thymeleaf/step07-페이징/)

---

## 대량 vs 페이징 연습

| 목적 | 스크립트 | 권장 건수 |
|------|----------|-----------|
| 페이징 UI 연습 | `seed_board_pagination.py` / `seed_books.py` | **300** |
| 스트레스/성능 | `seed_board_data.py` | 1000+ |

---

## 주의

- 스크립트는 해당 테이블을 **TRUNCATE** 합니다. 기존 데이터가 삭제됩니다.
- `board.sql`의 VIEW(`board_view`)가 있어야 step17 API 목록이 정상 동작합니다.
- Python 실행 전 MySQL 서버가 켜져 있어야 합니다.
