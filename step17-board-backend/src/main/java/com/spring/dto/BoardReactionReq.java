package com.spring.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("boardReq")
@Getter
@Setter
public class BoardReactionReq {
  private int id;
  private int bno;
  private String type;
  private Long mid;

}
