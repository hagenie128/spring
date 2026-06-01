package com.spring.problem04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

/**
 * [실습] 메인 페이지 요청을 다른 페이지로 리다이렉트하는 컨트롤러
 */
@Controller
@RequestMapping("/")
public class HomeController {
	
	/**
	 * 사이트 루트("/") 접속 시 실행되는 메서드
	 * 
	 * "redirect:/path": 브라우저에게 해당 경로로 다시 요청하라고 응답을 보냅니다.
	 * 결과적으로 사용자는 "/product/list" 페이지를 보게 됩니다.
	 */
	@GetMapping
	public String main(HttpServletRequest request) {
		return "redirect:/kiosk/home";
	}
}