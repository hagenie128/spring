package com.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionDTO {
  private Long likes;
  private long dislikes;
  private String myReaction;
  private long id;
}
