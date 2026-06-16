package com.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [회원가입 폼 DTO]
 *
 * DTO(Data Transfer Object) : 화면(form) ↔ 컨트롤러 사이에서 데이터를 옮기는 전용 객체입니다.
 * Entity(Member)와 분리해서, 화면에 필요한 필드·검증 규칙만 담습니다.
 *
 * [Bean Validation 어노테이션]
 *  @NotBlank  : null, 빈 문자열, 공백만 있는 값 불가
 *  @Size      : 글자 수 제한
 *  @Pattern   : 정규식 패턴 검사 ($ 는 "끝까지" 매칭 — [a-zA-Z0-9_]+ 는 4글자 이상도 통과)
 *
 * 컨트롤러에서 @Valid 와 함께 쓰면, 조건에 맞지 않을 때 message 가 화면에 표시됩니다.
 */
@Data
@NoArgsConstructor
public class MemberDTO {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 20, message = "아이디는 4~20자로 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문, 숫자, _만 가능합니다.")
    private String username;

    @NotBlank(message = "비밀 번호를 입력해주세요")
    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min = 4, max = 10, message = "닉네임은 4~10자로 입력해주세요")
    private String nickname;

}
