"""
Quest 18 — 도서(books) 샘플 데이터 대량 생성
사용: schema.sql 실행 후 이 스크립트 실행

  pip install pymysql
  python seed_books.py

건수 변경: 아래 BOOK_COUNT 수정 (기본 300)
"""

import random
from datetime import date, timedelta

import pymysql

# ========== 설정 (여기만 바꾸면 됨) ==========
BOOK_COUNT = 300          # 생성할 도서 권수
DB_HOST = "localhost"
DB_PORT = 3306
DB_USER = "root"
DB_PASSWORD = "12345678"  # 본인 MySQL 비밀번호
DB_NAME = "book_practice_db"
TRUNCATE_BEFORE_INSERT = True  # True면 books 테이블 비우고 다시 넣음
PAGE_SIZE = 10            # 페이징 연습 시 한 페이지당 개수 (참고용 출력)
# =============================================

AUTHORS = [
    "김스프", "이리액", "박마바", "최자바", "정스프링",
    "한타임", "오데이터", "윤알고", "장네트", "임클린",
]

TITLE_PREFIX = [
    "입문", "실전", "완벽 가이드", "핵심 정리", "프로젝트",
    "패턴", "아키텍처", "테스트", "배포", "성능 튜닝",
]

SUBJECTS = [
    "Spring Boot", "React", "MyBatis", "JPA", "JWT",
    "Thymeleaf", "MySQL", "Docker", "Kubernetes", "Java",
]


def random_published_date():
    start = date(2018, 1, 1)
    days = random.randint(0, 2500)
    return start + timedelta(days=days)


def main():
    conn = pymysql.connect(
        host=DB_HOST,
        port=DB_PORT,
        user=DB_USER,
        password=DB_PASSWORD,
        database=DB_NAME,
        charset="utf8mb4",
        autocommit=False,
    )
    cursor = conn.cursor()

    try:
        if TRUNCATE_BEFORE_INSERT:
            cursor.execute("TRUNCATE TABLE books")
            print("books 테이블 초기화 완료")

        rows = []
        for i in range(1, BOOK_COUNT + 1):
            subject = random.choice(SUBJECTS)
            prefix = random.choice(TITLE_PREFIX)
            title = f"{subject} {prefix} #{i:04d}"
            author = random.choice(AUTHORS)
            price = random.randint(12000, 45000)
            price = (price // 1000) * 1000
            published = random_published_date()
            rows.append((title, author, price, published))

        cursor.executemany(
            """
            INSERT INTO books (title, author, price, published_date)
            VALUES (%s, %s, %s, %s)
            """,
            rows,
        )

        conn.commit()

        cursor.execute("SELECT COUNT(*) FROM books")
        total = cursor.fetchone()[0]
        pages = (total + PAGE_SIZE - 1) // PAGE_SIZE

        print(f"도서 {BOOK_COUNT}건 INSERT 완료")
        print(f"DB 확인: SELECT COUNT(*) → {total}건")
        print(f"페이징 참고: size={PAGE_SIZE} 이면 약 {pages}페이지")
        print("다음: step07-페이징 연습")

    except Exception as e:
        conn.rollback()
        print("오류 — 롤백됨")
        raise e
    finally:
        cursor.close()
        conn.close()


if __name__ == "__main__":
    main()
