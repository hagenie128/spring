"""
step17-board-backend / React 페이징 연습용 샘플 데이터

- 게시글 300건 (기본) → size=20 이면 15페이지, size=30 이면 10페이지
- 회원 20명 (로그인 테스트: user1 ~ user20 / 비밀번호 1234 는 BCrypt 필요 → Postman signup 권장)
- 댓글·반응은 가볍게

사용법:
  pip install -r requirements.txt
  python seed_board_pagination.py

선행: step17-board-backend 의 board.sql 실행 (new_board_db)
"""

import random
from datetime import datetime, timedelta

from db import connect

try:
    from config import BOARD_DB
except ImportError:
    BOARD_DB = "new_board_db"

# ========== 여기 숫자만 바꿔도 됨 ==========
BOARD_COUNT = 300
MEMBER_COUNT = 20
MAX_COMMENTS_PER_POST = 3
# ==========================================

AUTHORS = ["김스프", "이리액", "박자바", "최빅데이터", "정클라우드"]
COMMENT_SAMPLES = [
    "좋은 글 감사합니다.",
    "잘 보고 갑니다.",
    "도움이 되었습니다.",
    "페이징 테스트 댓글입니다.",
]


def main():
    conn = connect(BOARD_DB)
    cursor = conn.cursor()

    try:
        cursor.execute("SET FOREIGN_KEY_CHECKS = 0")
        for table in [
            "board_comment_reaction",
            "board_reaction",
            "board_comment",
            "refresh_tokens",
            "board",
            "board_member",
        ]:
            cursor.execute(f"TRUNCATE TABLE {table}")
        cursor.execute("SET FOREIGN_KEY_CHECKS = 1")

        member_ids = []
        for i in range(1, MEMBER_COUNT + 1):
            cursor.execute(
                """
                INSERT INTO board_member(username, password, nickname, role)
                VALUES (%s, %s, %s, %s)
                """,
                (f"user{i}", "1234", f"회원{i}", "ROLE_USER"),
            )
            member_ids.append(cursor.lastrowid)
        print(f"회원 {MEMBER_COUNT}명 생성")

        board_ids = []
        for i in range(1, BOARD_COUNT + 1):
            mid = random.choice(member_ids)
            title = f"[페이징연습] 게시글 {i:04d} — {random.choice(AUTHORS)}"
            content = f"{i}번 샘플 게시글입니다. React/Thymeleaf 페이징 연습용 데이터."
            bcount = random.randint(0, 200)
            write_date = datetime.now() - timedelta(days=random.randint(0, 180))

            cursor.execute(
                """
                INSERT INTO board(title, content, write_date, mid, bcount, write_update_date)
                VALUES (%s, %s, %s, %s, %s, %s)
                """,
                (title, content, write_date, mid, bcount, write_date),
            )
            board_ids.append(cursor.lastrowid)

        print(f"게시글 {BOARD_COUNT}건 생성")

        comment_count = 0
        for bno in board_ids:
            for _ in range(random.randint(0, MAX_COMMENTS_PER_POST)):
                mid = random.choice(member_ids)
                content = random.choice(COMMENT_SAMPLES)
                cdate = datetime.now() - timedelta(days=random.randint(0, 90))
                cursor.execute(
                    """
                    INSERT INTO board_comment(content, cdate, mid, bno)
                    VALUES (%s, %s, %s, %s)
                    """,
                    (content, cdate, mid, bno),
                )
                comment_count += 1

        print(f"댓글 {comment_count}건 생성")

        conn.commit()
        print("완료 — GET /api/posts?page=1&size=20 로 페이징 확인")

    except Exception as e:
        conn.rollback()
        print("오류 발생, 롤백:", e)
        raise
    finally:
        cursor.close()
        conn.close()


if __name__ == "__main__":
    main()
