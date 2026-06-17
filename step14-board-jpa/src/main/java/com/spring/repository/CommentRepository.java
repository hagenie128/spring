package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Comment;

/**
 * [댓글 JPA 리포지토리 인터페이스]
 *
 * 【핵심 개념】 JpaRepository<Comment, Long>
 *   JpaRepository를 상속받으면 스프링 데이터 JPA가 런타임에 구현체를 자동 생성합니다.
 *   save() / findById() / findAll() / deleteById() 등 기본 CRUD가 모두 제공됩니다.
 *
 * 【두 가지 쿼리 방법 비교】
 *   1) @Query JPQL 방식 (getCommentByPost)
 *      - 복잡한 조인 / N+1 문제 해결이 필요할 때 직접 JPQL을 작성합니다.
 *      - "JOIN FETCH c.member m" 으로 댓글과 작성자를 SQL 1번에 같이 가져옵니다.
 *   2) 메서드명 기반 쿼리 (findByPostIdOrderByCreatedAtAsc)
 *      - 스프링 데이터 JPA가 메서드 이름을 파싱해 자동으로 JPQL을 생성합니다.
 *      - findBy + PostId + OrderBy + CreatedAt + Asc 규칙으로 동작합니다.
 *
 * 【응용 방법 (메서드명 규칙 예시)】
 *   findByPostIdOrderByCreatedAtDesc(Long id)  → 최신 댓글이 먼저 오는 정렬
 *   findByMemberId(Long memberId)               → 특정 회원의 전체 댓글 목록
 *   countByPostId(Long postId)                  → 특정 게시글의 댓글 수
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

  /**
   * [JPQL 페치 조인: 특정 게시글의 댓글 + 작성자 정보를 한 번의 SQL로 조회]
   *
   * N+1 방지를 위해 JOIN FETCH 사용 — 댓글 목록을 뿌릴 때 작성자 닉네임이
   * 필요한데, LAZY 로딩만 하면 댓글 N개마다 SELECT 1번씩 추가 쿼리가 발생합니다.
   * JOIN FETCH를 사용하면 단 1번의 쿼리로 댓글+회원 데이터를 모두 가져옵니다.
   *
   * 실행되는 SQL:
   *   SELECT c.*, m.*
   *   FROM comment c INNER JOIN member m ON c.member_id = m.id
   *   WHERE c.post_id = ? ORDER BY c.created_at ASC
   */
  @Query("select c from Comment c JOIN FETCH c.member m where c.post.id = :postId order by c.createdAt ASC")
  List<Comment> getCommentByPost(@Param("postId") Long id);

  /**
   * [메서드명 기반 쿼리: post.id = ? 조건으로 댓글 목록을 등록일 오름차순 조회]
   *
   * 메서드명 파싱 규칙:
   *   findBy → WHERE / PostId → post_id = ? / OrderBy → ORDER BY /
   *   CreatedAt → created_at / Asc → ASC
   *
   * 참고: 이 방법은 JOIN FETCH를 자동으로 넣어주지 않아 LAZY 로딩 시 N+1 발생 가능.
   *       댓글 목록 출력 시 작성자 닉네임 접근이 필요하다면 위의 getCommentByPost() 권장.
   */
  List<Comment> findByPostIdOrderByCreatedAtAsc(Long id);

}
