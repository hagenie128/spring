package com.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.Post;

/**
 * [게시판 JPA 리포지토리 인터페이스]
 * 
 * JpaRepository<Post, Long>를 상속받아 데이터베이스 테이블인 'post'를 조작합니다.
 * 스프링 데이터 JPA가 제공하는 풍부한 CRUD 기능에 더하여, 성능 최적화용 커스텀 쿼리를 정의해 사용합니다.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

  /**
   * [전체 게시글 페이징 조회 + 페치 조인(Fetch Join)을 이용한 N+1 문제 최적화]
   * 
   * - N+1 문제란?
   *   게시글 목록(N개)을 화면에 뿌릴 때 작성자 이름(Member)을 보여주려 하면, 지연 로딩(LAZY) 설정 때문에 
   *   게시글 리스트 전체를 가져오는 쿼리 1번(1) 외에 각 게시글의 회원 정보를 가져오는 SELECT 쿼리가 
   *   게시글 수만큼(N번) 매번 날아가 성능이 심각하게 저하되는 문제입니다.
   * 
   * - 페치 조인(join fetch) 해결법:
   *   "join fetch p.member"는 게시글을 조회할 때 Member 데이터까지 SQL Inner Join을 사용하여 한 번의 쿼리로 
   *   미리 몽땅 가져옵니다(Eager loading 효과). 이로써 단 1회의 쿼리로 N+1 문제를 방지하며 최적화 조회를 수행합니다.
   * 
   * - 페이징 처리와 countQuery:
   *   스프링 데이터 JPA에서 페이징 결과인 `Page<Post>`를 리턴하려면 데이터베이스 상의 전체 행 개수를 세는 COUNT 쿼리가 반드시 필요합니다.
   *   페치 조인이 적용된 주 쿼리에는 다중 테이블 조인이 포함되어 있어 성능이 무거울 수 있으므로, 카운트만 빠르게 수행하도록 
   *   가벼운 카운트 전용 쿼리(`countQuery = "select count(p) from Post p"`)를 따로 정의해 주는 것이 실무적인 최적화 기법입니다.
   * 
   * @param pageable 페이징 설정 정보(현재 페이지, 크기, 정렬 등)
   * @return 페이징 결과와 메타데이터가 담긴 Page 객체
   */
  @Query(value = "select p from Post p join fetch p.member order by p.id desc", countQuery = "select count(p) from Post p")
  Page<Post> findAllWithPost(Pageable pageable);

  /**
   * [검색어를 기준으로 게시글 제목 또는 작성자 닉네임 검색 + 페이징 및 페치 조인]
   * 
   * - :keyword: JPQL에서 사용하는 네임드 파라미터(Named Parameter) 표기법입니다. 
   *   메서드 매개변수 중 `String keyword` 값이 바인딩됩니다.
   * - p.title like %:keyword% or m.nickname like %:keyword%: 
   *   제목이나 작성자 닉네임에 해당 검색어가 포함된 경우(SQL의 LIKE '%검색어%') 데이터를 필터링합니다.
   * 
   * @param keyword  검색어 (예: "샘플")
   * @param pageable 페이징 설정 정보
   * @return 검색 및 페이징 처리가 완료된 게시글 리스트
   */
  @Query(value = "select p from Post p join fetch p.member m "
                + "where p.title like %:keyword% or m.nickname like %:keyword% "
                + "order by p.id desc", 
      countQuery = "select count(p) from Post p join p.member m "
                + "where p.title like %:keyword% or m.nickname like %:keyword% "
                + "order by p.id desc")
  Page<Post> searchWithPost(String keyword, Pageable pageable);
}
