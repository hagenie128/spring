# seed_board_data.py
# pip install pymysql

import pymysql
import random
from datetime import datetime, timedelta

conn = pymysql.connect(
    host="localhost",
    port=3306,
    user="root",
    password="12345678",
    database="new_board_db",
    charset="utf8mb4",
    autocommit=False
)

cursor = conn.cursor()

try:
    # 기존 데이터 삭제
    cursor.execute("SET FOREIGN_KEY_CHECKS = 0")

    cursor.execute("TRUNCATE TABLE board_comment_reaction")
    cursor.execute("TRUNCATE TABLE board_reaction")
    cursor.execute("TRUNCATE TABLE board_comment")
    cursor.execute("TRUNCATE TABLE refresh_tokens")
    cursor.execute("TRUNCATE TABLE board")
    cursor.execute("TRUNCATE TABLE board_member")

    cursor.execute("SET FOREIGN_KEY_CHECKS = 1")

    # 회원 100명 생성
    member_ids = []

    for i in range(1, 101):
        username = f"user{i}"
        password = "1234"
        nickname = f"회원{i}"
        role = "ROLE_USER"

        cursor.execute("""
            INSERT INTO board_member(username, password, nickname, role)
            VALUES (%s, %s, %s, %s)
        """, (username, password, nickname, role))

        member_ids.append(cursor.lastrowid)

    print("회원 생성 완료")

    # 게시글 1000건 생성
    board_ids = []

    for i in range(1, 1001):
        mid = random.choice(member_ids)
        title = f"샘플 게시글 제목 {i}"
        content = f"""
        이것은 {i}번 샘플 게시글입니다.

        Spring Boot 게시판 테스트용 데이터입니다.
        JPA, MyBatis, JWT, 댓글, 좋아요 기능 테스트에 사용할 수 있습니다.
        """
        bcount = random.randint(0, 500)
        write_date = datetime.now() - timedelta(days=random.randint(0, 365))

        cursor.execute("""
            INSERT INTO board(title, content, write_date, mid, bcount, write_update_date)
            VALUES (%s, %s, %s, %s, %s, %s)
        """, (title, content, write_date, mid, bcount, write_date))

        board_ids.append(cursor.lastrowid)

    print("게시글 생성 완료")

    # 댓글 생성
    comment_ids = []

    for bno in board_ids:
        comment_count = random.randint(0, 20)

        for _ in range(comment_count):
            mid = random.choice(member_ids)
            content = random.choice([
                "좋은 글 감사합니다.",
                "잘 보고 갑니다.",
                "도움이 많이 되었습니다.",
                "이 부분은 조금 더 설명이 필요해 보입니다.",
                "공감합니다.",
                "테스트 댓글입니다.",
                "재미있는 내용이네요.",
                "저도 같은 생각입니다."
            ])
            cdate = datetime.now() - timedelta(days=random.randint(0, 365))

            cursor.execute("""
                INSERT INTO board_comment(content, cdate, mid, bno)
                VALUES (%s, %s, %s, %s)
            """, (content, cdate, mid, bno))

            comment_ids.append(cursor.lastrowid)

    print("댓글 생성 완료")

    # 게시글 좋아요 / 싫어요 생성
    for bno in board_ids:
        reaction_count = random.randint(0, 30)
        reacted_members = random.sample(member_ids, min(reaction_count, len(member_ids)))

        for mid in reacted_members:
            reaction_type = random.choice(["LIKE", "DISLIKE"])

            cursor.execute("""
                INSERT INTO board_reaction(mid, bno, type)
                VALUES (%s, %s, %s)
            """, (mid, bno, reaction_type))

    print("게시글 반응 생성 완료")

    # 댓글 좋아요 / 싫어요 생성
    for cno in comment_ids:
        reaction_count = random.randint(0, 10)
        reacted_members = random.sample(member_ids, min(reaction_count, len(member_ids)))

        for mid in reacted_members:
            reaction_type = random.choice(["LIKE", "DISLIKE"])

            cursor.execute("""
                INSERT INTO board_comment_reaction(mid, cno, type)
                VALUES (%s, %s, %s)
            """, (mid, cno, reaction_type))

    print("댓글 반응 생성 완료")

    conn.commit()
    print("샘플 데이터 세팅 완료")

except Exception as e:
    conn.rollback()
    print("오류 발생, 롤백 처리됨")
    print(e)

finally:
    cursor.close()
    conn.close()