package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.CommentReaction;
import com.spring.entity.ReactionType;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {

  Long countByCommentIDAndType(Long id, ReactionType like);

  int countByCommentId(Long id);

}
