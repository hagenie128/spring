package com.spring.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("reaction")
@Getter
@Setter
public class ReactionCountDTO {
  private int likeCount;
  private int dislikeCount;
}
