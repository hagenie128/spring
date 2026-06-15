package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
