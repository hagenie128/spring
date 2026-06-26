-- 변형 연습: 학생 관리 테이블 (TODO: sample-data-students.sql 과 함께 사용)
CREATE DATABASE IF NOT EXISTS student_practice_db CHARACTER SET utf8mb4;
USE student_practice_db;

CREATE TABLE IF NOT EXISTS students (
    student_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(50) NOT NULL,
    email        VARCHAR(100),
    major        VARCHAR(50),
    grade        INT,
    enrolled_at  DATE
);
