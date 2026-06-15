package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.PostReaction;

/**
 * [게시글 좋아요/싫어요 DB Repository]
 */
public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

}
