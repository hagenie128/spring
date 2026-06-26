-- Quest 18 Practice A: 테이블 생성만 (샘플 데이터는 sample-data.sql)
-- MySQL에서 실행: CREATE DATABASE 후 이 파일 → sample-data.sql 순서

CREATE DATABASE IF NOT EXISTS book_practice_db CHARACTER SET utf8mb4;
USE book_practice_db;

CREATE TABLE IF NOT EXISTS books (
    book_id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    title          VARCHAR(200) NOT NULL,
    author         VARCHAR(100) NOT NULL,
    price          INT NOT NULL DEFAULT 0,
    published_date DATE,
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 다음: sample-data.sql 에서 INSERT 직접 작성
-- 가이드: sample-data-가이드.md
