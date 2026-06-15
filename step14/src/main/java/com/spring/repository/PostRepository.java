package com.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Post;

/**
 * [게시글 DB 접근 계층 — Repository]
 *
 * [JpaRepository<Post, Long>]
 *  - Post  : 엔티티 타입
 *  - Long  : PK(@Id) 타입
 *  - save(), findAll(), findById(), delete() 등 기본 메서드를 상속받아 바로 사용 가능
 *
 * [인터페이스만 작성하는 이유]
 *  - 구현 클래스는 Spring Data JPA 가 런타임에 자동 생성(프록시)합니다.
 *  - 메서드 이름이나 @Query 에 맞춰 SQL/JPQL 을 만들어 줍니다.
 *
 * [JPQL]
 *  - Java Persistence Query Language
 *  - SQL 이 아니라 '엔티티 클래스 이름(Post)' 과 '필드명' 으로 쿼리를 작성합니다.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	/**
	 * 전체 게시글 페이징 조회 + 작성자(member) 함께 로딩
	 *
	 * join fetch p.member
	 *  - N+1 문제 방지: 목록 조회 시 member 를 한 번에 같이 가져옴
	 *  - list.html 에서 post.member.nickname 출력할 때 추가 쿼리가 안 나감
	 *
	 * countQuery
	 *  - Page 를 만들려면 '전체 개수' 쿼리가 따로 필요해서 지정
	 */
	@Query(value = "select p from Post p join fetch p.member order by p.id desc",
			countQuery = "select count(p) from Post p")
	Page<Post> findAllWithPost(Pageable pageable);

	/**
	 * 제목 또는 내용에 keyword 가 포함된 게시글 검색 (페이징)
	 *
	 * :keyword → @Param("keyword") 와 연결되는 이름 있는 파라미터
	 * concat('%', :keyword, '%') → SQL LIKE '%검색어%' 와 같은 의미
	 */
	@Query(
			value = """
					select p from Post p
					join fetch p.member
					where p.title like concat('%', :keyword, '%')
					   or p.content like concat('%', :keyword, '%')
					order by p.id desc
					""",
			countQuery = """
					select count(p) from Post p
					where p.title like concat('%', :keyword, '%')
					   or p.content like concat('%', :keyword, '%')
					"""
	)
	Page<Post> searchWithPost(@Param("keyword") String keyword, Pageable pageable);

}
