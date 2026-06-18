package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.PostReaction;
import com.spring.entity.ReactionType;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

  List<PostReaction> findByPostId(Long postId);

  int countByPostIdAndType(Long postId, ReactionType type);

  Optional<PostReaction> findByPostIdAndMemberId(Long postId, Long memberId);

}
