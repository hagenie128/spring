package com.spring.dto;

import org.apache.ibatis.type.Alias;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Alias("board")
@Getter
@Setter
@Valid
public class BoardDTO {
  private int bno;
  
  @NotBlank(message = "제목을 반드시 입력하셔야합니다.")
  private String title;
  
  @NotBlank(message = "내용을 반드시 입력하셔야합니다.")
  private String content;
  
  private String writeDate;
  private String writeUpdateDate;
  private int bcount;
  private int blike;
  private int bhate;
  private Long mid;
  private String nickname;

}
