package com.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [게시글 등록/수정 폼 DTO 클래스]
 * 사용자가 입력한 게시글 제목과 본문 내용을 바인딩(Binding)하고 검증하는 전송 객체입니다.
 */
@Data
@NoArgsConstructor
public class PostFormDTO {

  /**
   * 작성할 게시글 제목
   * 
   * - @NotBlank: 제목은 빈 상태나 공백으로 남겨둘 수 없습니다.
   * - @Size(max = 200): 데이터베이스 컬럼 크기인 200글자를 넘지 않도록 제한합니다.
   */
  @NotBlank(message = "제목을 입력해주세요.")
  @Size(max = 200, message = "제목은 200자 이하로 입력해 주세요.")
  private String title;

  /**
   * 작성할 게시글 본문 내용
   * 
   * - @NotBlank: 본문은 내용을 필수로 작성해야 합니다.
   */
  @NotBlank(message = "내용을 입력해 주세요")
  private String content;
}
