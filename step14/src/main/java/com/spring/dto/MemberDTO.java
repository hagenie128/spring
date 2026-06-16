package com.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

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
