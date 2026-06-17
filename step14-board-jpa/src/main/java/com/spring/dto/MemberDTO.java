package com.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [회원 가입 폼 DTO (Data Transfer Object) 클래스]
 * DTO는 계층 간 데이터 전송을 위해 사용하는 객체입니다. 
 * 주로 화면(HTML)의 폼 데이터(Form Data)를 서버 컨트롤러로 전달받을 때 사용합니다.
 * 
 * jakarta.validation 검증 어노테이션들을 통해 컨트롤러 진입 전 사용자 입력값 유효성 검사(Validation)를 자동 수행합니다.
 */
@Data
@NoArgsConstructor
public class MemberDTO {
  
  /**
   * 가입할 사용자 아이디
   * 
   * - @NotBlank: null이 아니고, 최소 한 개 이상의 공백이 아닌 문자가 포함되어야 합니다.
   * - @Size(min = 4, max = 20): 문자열의 길이를 최소 4자에서 최대 20자 사이로 제한합니다.
   * - @Pattern: 지정된 정규표현식(regexp)과 일치해야 합니다.
   *   - "^[a-zA-Z0-9_]+$": 영문 대소문자, 숫자, 언더스코어(_)로만 구성되도록 정규식 제한을 둡니다.
   * - message: 유효성 검사 실패 시 화면에 출력될 경고 메시지를 지정합니다.
   */
  @NotBlank(message = "아이디를 입력해 주세요")
  @Size(min = 4, max = 20, message = "아이디는 4~20 글자로 입력해 주세요")
  @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문, 숫자, _ 만 가능합니다.")
  private String username;

  /**
   * 가입할 비밀번호
   * 
   * - @Size(min = 6): 최소 6자 이상의 길이 제한을 둡니다.
   */
  @NotBlank(message = "비밀번호를 입력해 주세요")
  @Size(min = 6, message = "비밀번호는 6 글자 이상 입력해주세요.")
  private String password;

  /**
   * 가입할 닉네임 (게시판에 표시될 이름)
   * 
   * - @Size(min = 4, max = 10): 닉네임 길이를 4자에서 10자 사이로 제한합니다.
   */
  @NotBlank(message = "닉네임을 입력해 주세요")
  @Size(min = 4, max = 10, message = "닉네임은 4~10 글자로 입력해 주세요")
  private String nickname;
}
