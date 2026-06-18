package com.spring.controller;

import java.net.Authenticator.RequestorType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spring.dto.ReactionDTO;
import com.spring.entity.Member;
import com.spring.entity.ReactionType;
import com.spring.service.PostReactionService;

@RestController
@RequestMapping("/reaction")
public class ReactionController {

  private final PostReactionService postReactionService;

  public ReactionController(PostReactionService postReactionService) {
    this.postReactionService = postReactionService;
  }

  @GetMapping("/post/{postId}/{type}")
  public ReactionDTO postReaction(@PathVariable(name = "postId") Long postId,
      @PathVariable(name = "type") String type,
      @SessionAttribute(value = "loginMember", required = false) Member loginMember) {
    System.out.println(postId + " / " + type);
    ReactionType reactionType = ReactionType.valueOf(type.toUpperCase());
    postReactionService.addReaction(postId, reactionType, loginMember.getId());

    ReactionDTO reactionDTO = new ReactionDTO();

    reactionDTO.setLikes(postReactionService.getReactionCount(postId, ReactionType.LIKE));
    reactionDTO.setDislikes(postReactionService.getReactionCount(postId, ReactionType.DISLIKE));
    return reactionDTO;
  }
}
