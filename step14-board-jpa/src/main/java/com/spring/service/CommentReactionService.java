package com.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.dto.ReactionDTO;
import com.spring.entity.Comment;
import com.spring.entity.ReactionType;
import com.spring.repository.CommentReactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentReactionService {

  private final CommentReactionRepository commentReactionRepository;

  public List<ReactionDTO> getCommentReactionByCommentId(List<Comment> comments) {
    List<ReactionDTO> list = new ArrayList<>();
    for (Comment comment : comments) {
      ReactionDTO reaction = new ReactionDTO();
      reaction.setId(comment.getId());
      reaction.setLikes(commentReactionRepository.countByCommentIDAndType(comment.getId(), ReactionType.LIKE));
      reaction.setDislikes(commentReactionRepository.countByCommentIDAndType(comment.getId(), ReactionType.DISLIKE));
      list.add(reaction);
    }
    return list;
  }
}
