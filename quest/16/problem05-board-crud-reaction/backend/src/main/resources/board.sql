-- Active: 1782277767994@@127.0.0.1@3306
create DATABASE new_board_db;
USE new_board_db;

desc board;
CREATE TABLE board
(
  bno               BIGINT      NOT NULL AUTO_INCREMENT,
  title             VARCHAR(50) NULL    ,
  content           LONGTEXT    NULL    ,
  write_date        DATETIME    NULL     DEFAULT now(),
  mid               int         NOT NULL,
  bcount            int         NULL     DEFAULT 0,
  write_update_date DATETIME    NULL     DEFAULT now(),
  PRIMARY KEY (bno)
);

CREATE TABLE board_comment
(
  cno     BIGINT        NOT NULL AUTO_INCREMENT,
  content VARCHAR(1000) NULL    ,
  cdate   DATETIME      NULL     DEFAULT now(),
  mid     int           NOT NULL,
  bno     BIGINT        NOT NULL,
  PRIMARY KEY (cno)
);

CREATE TABLE board_member
(
  id       int         NOT NULL AUTO_INCREMENT,
  username       VARCHAR(20) NOT NULL UNIQUE,
  password   CHAR(255)   NULL    ,
  nickname VARCHAR(10) NULL    ,
  role varchar(20) NULL,
  PRIMARY KEY (id)
);

create table board_reaction(
  id int not null AUTO_INCREMENT,
  mid int not null,
  bno int not null,
  type varchar(10),
  PRIMARY KEY (id)
);
create table board_comment_reaction(
  id int not null AUTO_INCREMENT,
  mid int not null,
  cno int not null,
  type varchar(10),
  PRIMARY KEY (id)
);

CREATE TABLE refresh_tokens (
  expires_at datetime(6) NOT NULL,
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL unique,
  token varchar(600) NOT NULL unique,
  PRIMARY KEY (id)
);

ALTER TABLE refresh_tokens
  ADD CONSTRAINT FK_board_member_TO_refresh
    FOREIGN KEY (user_id)
    REFERENCES board_member (id);



ALTER TABLE board
  ADD CONSTRAINT FK_board_member_TO_board
    FOREIGN KEY (mid)
    REFERENCES board_member (id);

ALTER TABLE board_comment
  ADD CONSTRAINT FK_board_member_TO_board_comment
    FOREIGN KEY (mid)
    REFERENCES board_member (id);

ALTER TABLE board_comment
  ADD CONSTRAINT FK_board_TO_board_comment
    FOREIGN KEY (bno)
    REFERENCES board (bno);

-- 댓글 좋아요, 싫어요, 게시글 좋아요, 싫어요는 중복 방지 위해 unique 제약조건 추가
alter table board_reaction add constraint unique (mid, bno);
alter table board_comment_reaction add constraint unique (mid, cno);

--- -------------------------
select count(*) from board;
select count(*) from board_comment; 
select count(*) from board_comment_reaction;
select count(*) from board_member;

-- 게시글 확인
select * from board LIMIT 100;

-- 전체 게시글 조회
--  글번호, 제목, 회원번호, 닉네임, 작성일, 수정일, 글내용, 조회수
SELECT
    b.bno, b.title, b.write_date, b.write_update_date,
    b.content, b.bcount, bm.id as mid, bm.nickname
from board b join board_member bm 
on b.mid = bm.id order by b.bno desc;
-- 글번호 별 좋아요 개수를 조회
select br.bno, count(*) as blike
from board_reaction br
where br.type = 'LIKE'
group by br.bno;

-- 글번호 별 싫어요 개수를 조회
select br.bno, count(*) as bhate
from board_reaction br
where br.type = 'DISLIKE'
group by br.bno;

-- 전체 게시글 조회
--  글번호, 제목, 회원번호, 닉네임, 작성일, 수정일, 글내용, 조회수, 게시글 좋아요 개수, 게시글 싫어요 개수
SELECT
    b.bno, b.title, b.write_date, b.write_update_date,
    b.content, b.bcount, bm.id as mid, bm.nickname,
    ifnull(bl.blike, 0) as blike, ifnull(bh.bhate, 0) as bhate
from board b join board_member bm 
on b.mid = bm.id
left outer join (
    select br.bno, count(*) as blike
    from board_reaction br
    where br.type = 'like'
    group by br.bno
) bl on bl.bno = b.bno
left outer join (
    select br.bno, count(*) as bhate
    from board_reaction br
    where br.type = 'dislike'
    group by br.bno
) bh on bh.bno = b.bno
order by b.bno desc;

-- WITH 절을 이용한 전체 게시글 조회 : MYSQL 8.0 이상에서 지원
with board_content_like as 
(select br.bno, count(*) as blike
from board_reaction br
where br.type = 'like'
group by br.bno),
board_content_hate as 
(select br.bno, count(*) as bhate
from board_reaction br
where br.type = 'dislike'
group by br.bno)
SELECT
    b.bno, b.title, b.write_date, b.write_update_date,
    b.content, b.bcount, bm.id as mid, bm.nickname, 
    ifnull(bcl.blike, 0) as blike, ifnull(bch.bhate, 0) as bhate
from board b left outer join board_member bm on b.mid = bm.id
LEFT outer join board_content_like bcl on bcl.bno = b.bno
LEFT outer join board_content_hate bch on bch.bno = b.bno
order by b.bno desc
;

CREATE OR REPLACE VIEW board_view
as
with board_content_like as 
(select br.bno, count(*) as blike
from board_reaction br
where br.type = 'like'
group by br.bno),
board_content_hate as 
(select br.bno, count(*) as bhate
from board_reaction br
where br.type = 'dislike'
group by br.bno)
SELECT
    b.bno, b.title, b.write_date, b.write_update_date,
    b.content, b.bcount, bm.id as mid, bm.nickname, 
    ifnull(bcl.blike, 0) as blike, ifnull(bch.bhate, 0) as bhate
from board b left outer join board_member bm on b.mid = bm.id
LEFT outer join board_content_like bcl on bcl.bno = b.bno
LEFT outer join board_content_hate bch on bch.bno = b.bno
;

select * from board_view order by bno desc;

-- 페이징 - 방법1 
--  board_view에서 최근 글 30건만 조회
select * from board_view order by bno desc limit 30;
-- limit 조회할게시글개수 offset (페이지번호-1)*조회할게시글개수
select * from board_view order by bno desc limit 30 offset 30;
select * from board_view order by bno desc limit 30 offset 120;

-- 페이징 - 방법2
-- board_view 조회시 row_number 적용
select ROW_NUMBER() OVER(ORDER BY b.bno desc) as rw, b.*
from board_view b;

-- 1페이지 읽기
select * from (select ROW_NUMBER() OVER(ORDER BY b.bno desc) as rw, b.*
from board_view b) bv
where ceil(bv.rw/30) = 1;
-- 2페이지 읽기
select * from (select ROW_NUMBER() OVER(ORDER BY b.bno desc) as rw, b.*
from board_view b) bv
where ceil(bv.rw/30) = 2;

select * from (select ROW_NUMBER() OVER(ORDER BY b.bno desc) as rw, b.*
from board_view b where b.title like '%84%') bv
where ceil(bv.rw/30) = 1;

select * from board_view b where b.title like '%84%';


select bcr.cno, count(*) as clike from board_comment_reaction bcr where bcr.type = 'LIKE' group by bcr.cno;

select bcr.cno, count(*) as clike from board_comment_reaction bcr where bcr.type = 'DISLIKE' group by bcr.cno;

select * from board_comment bc where bno = 1;

with board_comment_like as (select bcr.cno, count(*) as clike from board_comment_reaction bcr where bcr.type = 'LIKE' group by bcr.cno),
board_comment_dislike as (select bcr.cno, count(*) as chate from board_comment_reaction bcr where bcr.type = 'DISLIKE' group by bcr.cno)
select bc.*, bm.nickname, ifnull(bcl.clike,0) as clike, 
ifnull(bch.chate,0) as chate from board_comment bc 
  left outer join board_member bm on bm.id = bc.mid 
  left outer join board_comment_like bcl on bc.cno = bcl.cno
  left outer join board_comment_dislike bch on bc.cno = bch.cno;


create or replace view board_comment_view
AS
with board_comment_like as (select bcr.cno, count(*) as clike from board_comment_reaction bcr where bcr.type = 'LIKE' group by bcr.cno),
board_comment_dislike as (select bcr.cno, count(*) as chate from board_comment_reaction bcr where bcr.type = 'DISLIKE' group by bcr.cno)
select bc.*, bm.nickname, ifnull(bcl.clike,0) as clike, 
ifnull(bch.chate,0) as chate from board_comment bc 
  left outer join board_member bm on bm.id = bc.mid 
  left outer join board_comment_like bcl on bc.cno = bcl.cno
  left outer join board_comment_dislike bch on bc.cno = bch.cno;

select * from board_comment_view where bno = 999;

SELECT
  count(case when br.`type` = 'like' then 1 end) as likeCount,
  count(case when br.`type` = 'dislike' then 1 end) as dislikeCount
FROM
  board b LEFT OUTER JOIN board_reaction br ON b.bno = br.bno
WHERE 
  b.bno = 1003;


SELECT
      count(case when br.`type` = 'like' then 1 end) as likeCount,
      count(case when br.`type` = 'dislike' then 1 end) as dislikeCount
    FROM
      board_comment b LEFT OUTER JOIN board_comment_reaction br ON b.cno = br.cno
    WHERE 
      b.cno = 9848;