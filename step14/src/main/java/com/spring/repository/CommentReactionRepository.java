package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.CommentReaction;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {

}
