package com.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [게시글 작성·수정 폼 DTO]
 *
 * write.html 의 th:object="${form}" 과 연결됩니다.
 *  - th:field="*{title}"   → title 필드
 *  - th:field="*{content}" → content 필드 (Quill 에디터 내용이 hidden input 으로 들어옴)
 *
 * [@NotBlank]  : 제목·내용 필수 입력
 * [@Size]      : 제목 최대 200자
 */
@Data
@NoArgsConstructor
public class PostFormDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 200, message = "제목은 200자 이하로 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요")
    private String content;

}
