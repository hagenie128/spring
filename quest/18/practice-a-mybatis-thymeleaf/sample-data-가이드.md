# 샘플 데이터 넣기 가이드 (Practice A — Thymeleaf CRUD)

> 목록·상세 연습을 하려면 DB에 **데이터가 3건 이상** 있어야 합니다.  
> 아래 **방법 1**을 추천하고, 시험/실습에서 Java로 넣으라고 하면 **방법 2**를 사용하세요.

---

## 방법 1 — SQL 파일 (추천, 가장 빠름)

### 순서

1. `schema.sql` 실행 → 테이블 생성
2. **`sample-data.sql`** 열기 → `TODO` 부분에 INSERT **직접 작성**
3. MySQL에서 `sample-data.sql` 실행
4. 확인:

```sql
USE book_practice_db;
SELECT * FROM books;
SELECT COUNT(*) FROM books;  -- 3 이상
```

### INSERT 작성 패턴

```sql
INSERT INTO books (title, author, price, published_date) VALUES
('제목', '저자', 32000, '2024-03-01');
```

여러 건 한 번에:

```sql
INSERT INTO books (title, author, price, published_date) VALUES
('책1', '저자A', 10000, '2024-01-01'),
('책2', '저자B', 20000, '2024-02-01'),
('책3', '저자C', 30000, '2024-03-01');
```

### 파일 위치

| 파일 | 용도 |
|------|------|
| [schema.sql](schema.sql) | 테이블만 CREATE |
| [sample-data.sql](sample-data.sql) | **직접 작성** TODO |
| [sample-data-example.sql](sample-data-example.sql) | 막힐 때만 참고 |

---

## 방법 2 — DataInitializer (Java로 앱 시작 시 자동 삽입)

시험에서 "앱 실행 시 샘플 데이터" 요구 시 사용.

### 순서

1. [template/DataInitializer.java](template/DataInitializer.java) 를  
   `src/main/java/com/spring/DataInitializer.java` 로 복사
2. `TODO` 부분에 `bookMapper.insert(...)` **직접 작성**
3. `bootRun` → 브라우저 `/books` 에 데이터 표시 확인

### 핵심 패턴

```java
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
  private final BookMapper bookMapper;

  @Override
  public void run(String... args) {
    if (!bookMapper.findAll().isEmpty()) return; // 중복 방지

    BookDTO b1 = new BookDTO();
    b1.setTitle("TODO 제목");
    b1.setAuthor("TODO 저자");
    // ...
    bookMapper.insert(b1);
  }
}
```

⚠️ `BookMapper.insert`와 XML `insert` 쿼리가 **먼저** 있어야 함 (Step 3 등록 연습과 연결)

---

## 방법 3 — MySQL Workbench / DBeaver GUI

1. 테이블 `books` 우클릭 → **Table Data Import** 또는 그리드에서 직접 입력
2. 연습용으로는 방법 1(SQL 파일)이 시험과 더 비슷함

---

## 변형 연습용 템플릿 (도메인 바꿀 때)

| 도메인 | 스키마 | 샘플 데이터 TODO |
|--------|--------|------------------|
| 도서 | schema.sql | sample-data.sql |
| 학생 | [schema-students.sql](schema-students.sql) | [sample-data-students.sql](sample-data-students.sql) |
| 메뉴 | [schema-menus.sql](schema-menus.sql) | [sample-data-menus.sql](sample-data-menus.sql) |

테이블 컬럼에 맞게 INSERT 컬럼명만 바꿔서 **직접** 작성하세요.

---

## 방법 4 — Python (`sample_data/` 폴더) ⭐ 페이징 연습

step17 수업과 **같은 폴더**입니다.

```powershell
cd c:\work_spring\sample_data
pip install -r requirements.txt
python seed_books.py      # 도서 300건 → book_practice_db
```

| 스크립트 | 건수 | 용도 |
|----------|------|------|
| [sample_data/seed_books.py](../../../sample_data/seed_books.py) | 300 | quest18 도서 + **페이징** |
| [sample_data/seed_board_pagination.py](../../../sample_data/seed_board_pagination.py) | 300 | step17 React 페이징 |
| [sample_data/seed_board_data.py](../../../sample_data/seed_board_data.py) | 1000 | 대량 테스트 |

건수 변경: 파일 상단 `BOOK_COUNT` / `BOARD_COUNT` 수정.

📄 전체 설명: [sample_data/README.md](../../../sample_data/README.md)

---

## 체크리스트

- [ ] `schema.sql` 실행됨
- [ ] `sample-data.sql` INSERT를 **본인이** 작성함 (복붙만 X)
- [ ] (페이징) `python seed_books.py` → COUNT ≥ 300
- [ ] `/books` 목록에 데이터 표시
- [ ] (선택) DataInitializer로도 넣어 봄

---

## 흔한 실수

| 실수 | 증상 |
|------|------|
| DB/테이블 안 만듦 | Table doesn't exist |
| 컬럼명 오타 | Unknown column |
| NOT NULL 컬럼 누락 | Insert fails |
| schema 없이 INSERT만 | 에러 |
| book_id에 값 넣음 | AUTO_INCREMENT 충돌 (보통 생략) |
