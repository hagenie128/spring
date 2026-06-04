package com.spring.interceptor;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 로그인/로그아웃 컨트롤러
 * 세션에 "loginUser" 속성을 저장/삭제하여 인터셉터와 연동
 */
@Controller
public class LoginController {

    /**
     * GET /login → 로그인 폼 표시
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * POST /login → 로그인 처리 후 게시판 목록으로 이동
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, HttpSession session) {
        session.setAttribute("loginUser", username);
        System.out.println("[LoginController] 로그인 성공: " + username);
        return "redirect:/board/list";
    }

    /**
     * GET /logout → 세션 무효화 후 로그인 페이지로 이동
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
