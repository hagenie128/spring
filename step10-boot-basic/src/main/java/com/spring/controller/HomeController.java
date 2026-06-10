package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 페이지 처리를 담당하는 컨트롤러입니다.
 */
@Controller
public class HomeController {

    /**
     * "/" 경로로 접속했을 때 실행되는 메서드입니다.
     * @return "home": resources/templates/home.html 파일을 찾아 렌더링합니다.
     */
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
