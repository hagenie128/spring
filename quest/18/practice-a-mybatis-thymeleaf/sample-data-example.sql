-- 정답 예시 (막힐 때만 참고 — 연습할 때는 sample-data.sql 에 직접 작성)
USE book_practice_db;

INSERT INTO books (title, author, price, published_date) VALUES
('스프링 부트 실전', '김스프', 32000, '2024-03-01'),
('리액트 입문', '이리액', 28000, '2023-11-15'),
('MyBatis 완벽 가이드', '박마바', 35000, '2024-01-20'),
('자바의 정석', '남궁성', 30000, '2022-05-10'),
('객체지향의 사실과 오해', '조영호', 26000, '2021-08-20');

SELECT COUNT(*) FROM books;
