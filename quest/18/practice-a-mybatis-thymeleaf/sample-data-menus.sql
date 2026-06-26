-- 변형 연습: 메뉴 샘플 데이터 — 직접 작성
USE menu_practice_db;

-- TODO: 메뉴 3개 이상 INSERT
-- 컬럼: name, category, price, is_available (1=판매중, 0=품절)

-- INSERT INTO menus (name, category, price, is_available) VALUES
-- ('아메리카노', '커피', 4500, 1);

INSERT INTO menus (name, category, price, is_available) VALUES
('TODO 메뉴명', 'TODO 카테고리', 0, 1);

SELECT * FROM menus;
