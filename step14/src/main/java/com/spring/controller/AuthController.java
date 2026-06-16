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

@Controller
@RequestMapping("/auth")
public class AuthController {

    
    private final MemberService memberService;

    AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("form", new MemberDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") MemberDTO form,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println(form);
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

    @GetMapping("/login")
    public String loginView() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes){
        try {
            Member member = memberService.login(username,password);
            session.setAttribute("loginMember", member);
            return "redirect:/board/list";  
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/auth/login";
        }
    }
}
