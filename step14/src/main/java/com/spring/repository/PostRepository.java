package com.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.Post;

/**
 * [게시판 JPA 리포지토리 인터페이스]
 * 
 * JpaRepository<Post, Long>를 상속받음으로써 기본적인 CRUD 메서드(save, findById, findAll,
 * delete 등)가
 * 스프링 데이터 JPA에 의해 런타임에 자동으로 구현체가 만들어져 주입됩니다.
 * - 첫 번째 제네릭 타입(Post): 관리할 엔티티 클래스 타입
 * - 두 번째 제네릭 타입(Long): 해당 엔티티 클래스의 @Id(기본키) 속성 데이터 타입
 */
public interface PostRepository extends JpaRepository<Post, Long> {

  /**
   * [지연 로딩 성능 최적화를 위한 페치 조인(Fetch Join) 쿼리]
   * 
   * @Query: 직접 JPQL(Java Persistence Query Language)을 지정해 사용자 정의 쿼리를 실행합니다.
   *         - join fetch p.member:
   *         게시글(Post)을 조회할 때 연관된 회원(Member) 테이블도 함께 즉시 조인(Join)하여 SQL 한 번에 한꺼번에
   *         읽어옵니다.
   *         이렇게 함으로써 지연 로딩 때문에 발생하는 추가적인 조회 쿼리 폭탄(N+1 문제)을 방지할 수 있습니다.
   *         - order by p.id desc: 게시판 최신글이 가장 먼저 보이도록 글 번호 역순 정렬을 수행합니다.
   *         - countQuery: 페이징 처리를 위해서는 전체 데이터 개수를 조회하는 COUNT 쿼리가 반드시 동반되어야 하므로
   *         성능 최적화된 카운트 전용 쿼리를 명시적으로 설정해 줍니다.
   */
  @Query(value = "select p from Post p join fetch p.member order by p.id desc", countQuery = "select count(p) from Post p")
  Page<Post> findAllWithPost(Pageable pageable);

  /** 제목 또는 작성자 닉네임으로 검색 (페이징) */
  @Query(value = "select p from Post p join fetch p.member m "
      + "where p.title like %:keyword% or m.nickname like %:keyword% "
      + "order by p.id desc", countQuery = "select count(p) from Post p join p.member m "
          + "where p.title like %:keyword% or m.nickname like %:keyword%")
  Page<Post> searchWithPost(String keyword, Pageable pageable);

}