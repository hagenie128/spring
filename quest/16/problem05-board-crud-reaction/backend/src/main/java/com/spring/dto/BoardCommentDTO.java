package com.spring.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("comment")
@Getter
@Setter
public class BoardCommentDTO {
  private int cno;
  private int bno;
  private String content;
  private String cdate;
  private String nickname;
  private int clike;
  private int chate;
  private Long mid;

}
