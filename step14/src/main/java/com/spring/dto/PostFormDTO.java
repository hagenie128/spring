package com.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostFormDTO {

  @NotBlank(message = "제목을 입력해주세요.")
  @Size(max = 200, message = "제목은 200자 이하로 입력해 주세요.")
  private String title;

  @NotBlank(message = "내용을 입력해 주세요")
  private String content;

}