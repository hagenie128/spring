-- new_board_db FK 정리 (기존 DB용 — Workbench에서 USE new_board_db 후 실행)
-- board.sql 276줄 이하 중복 구문은 삭제됨. 이 파일만 실행하세요.

USE new_board_db;

-- 현재 FK 확인
SELECT TABLE_NAME, CONSTRAINT_NAME, REFERENCED_TABLE_NAME, DELETE_RULE
FROM information_schema.REFERENTIAL_CONSTRAINTS
WHERE CONSTRAINT_SCHEMA = 'new_board_db'
  AND TABLE_NAME IN ('board', 'board_comment', 'board_reaction', 'board_comment_reaction')
ORDER BY TABLE_NAME, CONSTRAINT_NAME;

-- 1) board_comment → board : CASCADE 로 변경
--    (기존 이름이 fk_comment_board 인 경우)
ALTER TABLE board_comment DROP FOREIGN KEY fk_comment_board;

ALTER TABLE board_comment
  ADD CONSTRAINT FK_board_TO_board_comment
    FOREIGN KEY (bno)
    REFERENCES board (bno)
    ON DELETE CASCADE;

-- 2) board_reaction → board : CASCADE FK 추가 (없을 때만)
--    이미 FK_board_TO_board_reaction 이 있으면 이 블록은 에러 → 건너뛰기
ALTER TABLE board_reaction
  ADD CONSTRAINT FK_board_TO_board_reaction
    FOREIGN KEY (bno)
    REFERENCES board (bno)
    ON DELETE CASCADE;

-- 3) board_comment_reaction → board_comment : 이미 CASCADE면 생략
--    DELETE_RULE 이 NO ACTION 이면 아래 실행
-- ALTER TABLE board_comment_reaction DROP FOREIGN KEY FK_board_comment_TO_board_comment_reaction;
-- ALTER TABLE board_comment_reaction
--   ADD CONSTRAINT FK_board_comment_TO_board_comment_reaction
--     FOREIGN KEY (cno) REFERENCES board_comment (cno) ON DELETE CASCADE;

-- 완료 후 확인
SELECT TABLE_NAME, CONSTRAINT_NAME, REFERENCED_TABLE_NAME, DELETE_RULE
FROM information_schema.REFERENTIAL_CONSTRAINTS
WHERE CONSTRAINT_SCHEMA = 'new_board_db'
  AND TABLE_NAME IN ('board', 'board_comment', 'board_reaction', 'board_comment_reaction')
ORDER BY TABLE_NAME, CONSTRAINT_NAME;
