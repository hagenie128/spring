package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dto.MemberDTO;
import com.spring.entity.Member;
import com.spring.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * [인증(로그인·회원가입) 컨트롤러]
 *
 * URL 예시:
 *   GET  /auth/register  → 회원가입 화면
 *   POST /auth/register  → 회원가입 처리
 *   GET  /auth/login     → 로그인 화면
 *   POST /auth/login     → 로그인 처리
 *
 * [HttpSession]
 *  로그인 성공 시 session.setAttribute("loginMember", member) 로
 *  로그인한 회원 정보를 세션에 저장합니다.
 *  이후 다른 컨트롤러에서 @SessionAttribute("loginMember") 로 꺼내 씁니다.
 *
 * [RedirectAttributes]
 *  redirect: 뒤에 한 번만 보여 줄 메시지(Flash Attribute)를 넘길 때 사용합니다.
 *  예) 회원가입 완료 후 로그인 화면에 "회원가입이 완료되었습니다." 표시
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    /** 회원가입 화면 — 빈 MemberDTO 를 form 이라는 이름으로 템플릿에 전달 */
    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("form", new MemberDTO());
        return "auth/register";
    }

    /**
     * 회원가입 처리
     *
     * [@Valid]          : MemberDTO 의 @NotBlank, @Size 등 검증 실행
     * [BindingResult]   : 검증 실패 시 에러 목록 보관 → 화면에 th:errors 로 표시
     * [try-catch]       : 아이디/닉네임 중복 시 다시 회원가입 화면으로
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") MemberDTO form,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "auth/register";

        try {
            memberService.register(form);
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/auth/login";
        } catch (IllegalArgumentException e) {
            bindingResult.reject(e.getMessage());
            return "auth/register";
        }
    }

    /** 로그인 화면 */
    @GetMapping("/login")
    public String loginView() {
        return "auth/login";
    }

    /**
     * 로그인 처리
     *
     * [@RequestParam]  : form 의 name="username", name="password" 값을 받음
     * [HttpSession]    : 로그인 성공 시 loginMember 를 세션에 저장
     * 실패 시 errorMessage 를 Flash 로 넘기고 다시 로그인 화면으로
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.login(username, password);
            session.setAttribute("loginMember", member);
            return "redirect:/board/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/auth/login";
        }
    }
}
