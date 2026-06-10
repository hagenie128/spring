package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 홈페이지 접속 시의 처리를 담당하는 컨트롤러입니다.
 */
@Controller
public class HomeController {
	/**
	 * 루트 경로("/")로 접속하면 메뉴 목록 페이지("/menus")로 자동 이동(리다이렉트)시킵니다.
	 */
	@RequestMapping("/")
	public String index() {
		return "redirect:/menus";
	}
}
