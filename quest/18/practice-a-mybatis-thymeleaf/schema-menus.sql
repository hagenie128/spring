-- 변형 연습: 메뉴 관리 테이블
CREATE DATABASE IF NOT EXISTS menu_practice_db CHARACTER SET utf8mb4;
USE menu_practice_db;

CREATE TABLE IF NOT EXISTS menus (
    menu_id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(100) NOT NULL,
    category      VARCHAR(50),
    price         INT NOT NULL DEFAULT 0,
    is_available  TINYINT(1) DEFAULT 1
);
