package com.spring.controller;

import com.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.dto.MemberDTO;
import com.spring.entity.Member;
import com.spring.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * [사용자 인증(회원가입, 로그인, 로그아웃) 컨트롤러 클래스]
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
  
  // 생성자 주입 방식으로 주입되는 리포지토리 필드입니다. (추천 방식)
  private final MemberRepository memberRepository;
  
  /**
   * [필드 주입 방식의 예시]
   * 
   * @Autowired: 스프링이 관리하는 빈(MemberService)을 필드에 직접 주입합니다.
   *   *팁*: 단순하고 편리하지만 외부에서 변경하기 어렵고, 테스트 코드 작성 시 Mock 객체를 넣기 힘들어 
   *   실무에서는 생성자 주입 방식을 훨씬 권장합니다.
   */
  @Autowired
  private MemberService memberService;

  /**
   * [생성자를 통한 의존성 주입]
   */
  AuthController(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /**
   * [회원가입 폼 반환 API]
   * 
   * @param model 화면에 바인딩할 객체를 넘겨주는 모델 객체
   * @return 회원가입 입력 화면 뷰 경로 ("templates/auth/register.html")
   */
  @GetMapping("/register")
  public String registerForm(Model model) {
      // 타임리프 화면 폼 태그(th:object)에서 참조할 빈 MemberDTO 객체를 미리 담아 전달합니다.
      model.addAttribute("form", new MemberDTO());
      return "auth/register";
  }
  
  /**
   * [회원가입 요청 처리 API]
   * 
   * @param form @Valid 검증이 들어간 회원가입 입력 DTO
   * @param bindingResult @Valid 검증 결과 에러 정보를 담는 스프링 제공 객체
   * @param redirectAttributes 리다이렉트 시 화면으로 데이터를 살려서 보내주는 객체
   * @return 회원가입 성공 시 로그인 화면으로 리다이렉트, 실패 시 가입 폼 화면 반환
   */
  @PostMapping("/register")
  public String register(@Valid @ModelAttribute("form") MemberDTO form, 
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
      
      // 1. DTO 필드 제약조건(@NotBlank, @Size 등) 검증에서 걸린 에러가 있는지 체크합니다.
      if(bindingResult.hasErrors()) {
        // 에러 정보를 안고 다시 register 폼 뷰로 이동합니다. (화면에 에러 메시지 자동 노출)
        return "auth/register";
      }

      try{
        // 2. 가입 처리 비즈니스 로직 실행 (중복 확인 및 암호화 등)
        memberService.register(form);
        
        // 3. 리다이렉트 후 한 번만 사용할 수 있는 일회성 세션성 플래시 메시지를 등록합니다.
        //    (새로고침을 해도 메시지가 반복 노출되지 않도록 세션에서 자동으로 삭제됩니다)
        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
        
        return "redirect:/auth/login";
      } catch(IllegalArgumentException e) {
        // 4. 비즈니스 로직 도중 중복 아이디/닉네임 예외가 발생한 경우, 
        //    특정 필드가 아닌 글로벌 에러(Global Error)로 예외 메시지를 추가해 가입 폼으로 되돌립니다.
        bindingResult.reject(e.getMessage());
        return "auth/register";
      }
  }

  /**
   * [로그인 화면 반환 API]
   */
  @GetMapping("/login")
  public String loginForm() {
      return "auth/login";
  }
  
  /**
   * [로그인 요청 처리 API]
   * 
   * @param username 입력한 아이디
   * @param password 입력한 비밀번호
   * @param session 사용자별 고유한 상태 정보를 보관하는 WAS 세션 객체
   * @param redirectAttributes 에러 메시지 전달을 위한 리다이렉트 객체
   */
  @PostMapping("/login")
  public String login(String username, String password, HttpSession session,
    RedirectAttributes redirectAttributes
  ) {
      try {
        // 1. 아이디 및 비밀번호 검증 비즈니스 로직 실행
        Member member = memberService.login(username, password);
        
        // 2. 검증 성공 시, 사용자의 로그인 정보 객체(Member)를 세션에 "loginMember"라는 Key로 저장합니다.
        //    이후 사용자가 브라우저를 닫거나 세션이 만료되기 전까지 매 요청 시 로그인 정보가 유지됩니다.
        session.setAttribute("loginMember", member);
        
        return "redirect:/board";
      } catch (Exception e) {
        // 3. 로그인 실패 시 에러 메시지를 플래시 속성에 담아 로그인 폼으로 돌려보냅니다.
        redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호 올바르지 않습니다.");
        return "redirect:/auth/login";
      }
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/board";
  }
  
}
