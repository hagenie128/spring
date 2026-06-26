-- 변형 연습: 학생 샘플 데이터 — 직접 작성
USE student_practice_db;

-- TODO: 학생 3명 이상 INSERT
-- 컬럼: name, email, major, grade, enrolled_at

-- INSERT INTO students (name, email, major, grade, enrolled_at) VALUES
-- ('홍길동', 'hong@school.ac.kr', '컴퓨터공학', 2, '2023-03-01');

INSERT INTO students (name, email, major, grade, enrolled_at) VALUES
('TODO 이름1', 'todo1@email.com', 'TODO 학과', 1, '2024-03-01');

SELECT * FROM students;
SELECT COUNT(*) FROM students;
