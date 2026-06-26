"""공통 DB 연결 — 각 seed 스크립트에서 import"""

import pymysql

try:
    from config import DB_HOST, DB_PORT, DB_USER, DB_PASSWORD
except ImportError:
  # config.py 없으면 기본값 (step17 application.properties 와 동일)
    DB_HOST = "localhost"
    DB_PORT = 3306
    DB_USER = "root"
    DB_PASSWORD = "12345678"


def connect(database: str):
    return pymysql.connect(
        host=DB_HOST,
        port=DB_PORT,
        user=DB_USER,
        password=DB_PASSWORD,
        database=database,
        charset="utf8mb4",
        autocommit=False,
    )
