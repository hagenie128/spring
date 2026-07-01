CREATE DATABASE TEST_STUDENT_DB;

CREATE TABLE department (
    dept_id INT NOT NULL AUTO_INCREMENT,
    dept_name VARCHAR(50) NOT NULL,
    office_location VARCHAR(100),
    phone VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (dept_id)
);

CREATE TABLE student (
    student_id BIGINT NOT NULL AUTO_INCREMENT,
    student_no VARCHAR(20) NOT NULL UNIQUE,
    student_name VARCHAR(30) NOT NULL,
    dept_id INT NOT NULL,
    grade INT NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (student_id),
    CONSTRAINT fk_student_department FOREIGN KEY (dept_id) REFERENCES department (dept_id)
);

INSERT INTO
    department (
        dept_name,
        office_location,
        phone
    )
VALUES (
        '컴퓨터공학과',
        '공학관 301호',
        '02-1111-1001'
    ),
    (
        '정보통신학과',
        '공학관 302호',
        '02-1111-1002'
    ),
    (
        '소프트웨어학과',
        '공학관 303호',
        '02-1111-1003'
    ),
    (
        '경영학과',
        '경영관 201호',
        '02-1111-2001'
    ),
    (
        '디자인학과',
        '예술관 101호',
        '02-1111-3001'
    );

INSERT INTO
    student (
        student_no,
        student_name,
        dept_id,
        grade,
        phone,
        email
    )
VALUES (
        '20240001',
        '김민수',
        1,
        1,
        '010-1111-1111',
        'minsu.kim@example.com'
    ),
    (
        '20240002',
        '이서연',
        1,
        2,
        '010-2222-2222',
        'seoyeon.lee@example.com'
    ),
    (
        '20240003',
        '박지훈',
        2,
        3,
        '010-3333-3333',
        'jihoon.park@example.com'
    ),
    (
        '20240004',
        '최유진',
        2,
        1,
        '010-4444-4444',
        'yujin.choi@example.com'
    ),
    (
        '20240005',
        '정현우',
        3,
        4,
        '010-5555-5555',
        'hyunwoo.jung@example.com'
    ),
    (
        '20240006',
        '강하늘',
        3,
        2,
        '010-6666-6666',
        'haneul.kang@example.com'
    ),
    (
        '20240007',
        '조은비',
        4,
        3,
        '010-7777-7777',
        'eunbi.cho@example.com'
    ),
    (
        '20240008',
        '윤도현',
        4,
        1,
        '010-8888-8888',
        'dohyun.yoon@example.com'
    ),
    (
        '20240009',
        '장수아',
        5,
        2,
        '010-9999-9999',
        'sua.jang@example.com'
    ),
    (
        '20240010',
        '한지민',
        5,
        4,
        '010-0000-0000',
        'jimin.han@example.com'
    );