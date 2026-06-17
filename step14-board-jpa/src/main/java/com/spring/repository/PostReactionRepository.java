package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.PostReaction;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

}
