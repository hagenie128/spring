package com.spring.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p join fetch p.member order by p.id desc", 
        countQuery = "select count(p) from Post p")
    Page<Post> findAllWithPost(Pageable pageable);

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
