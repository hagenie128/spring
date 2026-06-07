package com.spring.problem01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// [완성된 파일 — 수정 불필요]
// dispatcher-context.xml의 TODO 1~3을 완성하면 이 컨트롤러가 동작합니다.
@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("message", "환영합니다! Thymeleaf 첫 번째 실습");
        model.addAttribute("studentName", "수강생");
        return "welcome";
    }
}
