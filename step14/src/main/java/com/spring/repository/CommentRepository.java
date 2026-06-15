package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Comment;

/**
 * [댓글 DB 접근 Repository]
 * DataInitializer 에서 샘플 댓글 saveAll() 에 사용됩니다.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
