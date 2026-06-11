package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	// 루트 주소(/)로 들어오면 templates/index.html 화면을 보여줍니다.
	@GetMapping("/")
	public String index() {
		return "index";
	}
}
