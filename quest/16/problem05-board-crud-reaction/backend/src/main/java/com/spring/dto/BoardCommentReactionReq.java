package com.spring.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("boardCommentReq")
@Getter
@Setter
public class BoardCommentReactionReq {
  private int id;
  private int cno;
  private String type;
  private Long mid;

}
