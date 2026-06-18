package com.spring.service;

import com.spring.controller.ReactionController;
import com.spring.repository.CommentReactionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.dto.ReactionDTO;
import com.spring.entity.Comment;
import com.spring.entity.CommentReaction;
import com.spring.entity.ReactionType;

@Service
public class CommentReactionService {

  private final ReactionController reactionController;
  private final CommentReactionRepository commentReactionRepository;

  CommentReactionService(CommentReactionRepository commentReactionRepository, ReactionController reactionController) {
    this.commentReactionRepository = commentReactionRepository;
    this.reactionController = reactionController;
  }

  public List<ReactionDTO> getCommentReactionByCommentId(List<Comment> comments) {
    List<ReactionDTO> list = null;
    ReactionDTO reaction;
    for (int i = 0; i < comments.size(); i++) {
      List<CommentReaction> reactions = commentReactionRepository.findByCommentId(comments.get(i).getId());
      if(!reactions.isEmpty()){
        reaction.setId(comments.get(i).getId());
        reaction.setLikes(reactions.stream().;
      }
    }
  }

}
