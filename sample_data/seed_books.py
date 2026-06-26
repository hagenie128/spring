"""
quest/18 도서 CRUD + Thymeleaf 페이징 연습용

- 도서 300건 (기본) → 페이지당 10권이면 30페이지
- book_practice_db / books 테이블

사용법:
  pip install -r requirements.txt
  python seed_books.py

선행: quest/18/.../schema.sql 실행
"""

import random
from datetime import date, timedelta

from db import connect

try:
    from config import BOOK_DB
except ImportError:
    BOOK_DB = "book_practice_db"

# ========== 건수 조절 ==========
BOOK_COUNT = 300
# ================================

TITLES = [
    "스프링 부트", "리액트", "MyBatis", "JPA", "JWT",
    "Thymeleaf", "REST API", "클린 코드", "객체지향", "알고리즘",
    "네트워크", "운영체제", "데이터베이스", "자바", "파이썬",
]
AUTHORS = [
    "김스프", "이리액", "박마바", "최자바", "정웹",
    "한백엔", "오프론트", "윤DB", "서클라우드", "강테스트",
]


def main():
    conn = connect(BOOK_DB)
    cursor = conn.cursor()

    try:
        cursor.execute("TRUNCATE TABLE books")

        base_date = date(2020, 1, 1)
        for i in range(1, BOOK_COUNT + 1):
            title = f"{random.choice(TITLES)} 실전 가이드 vol.{i}"
            author = random.choice(AUTHORS)
            price = random.randint(15000, 45000)
            published = base_date + timedelta(days=random.randint(0, 1800))

            cursor.execute(
                """
                INSERT INTO books (title, author, price, published_date)
                VALUES (%s, %s, %s, %s)
                """,
                (title, author, price, published),
            )

        conn.commit()
        print(f"도서 {BOOK_COUNT}건 삽입 완료 — SELECT COUNT(*) FROM books; 로 확인")

    except Exception as e:
        conn.rollback()
        print("오류:", e)
        raise
    finally:
        cursor.close()
        conn.close()


if __name__ == "__main__":
    main()
