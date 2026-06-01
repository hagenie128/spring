package com.spring.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	/**
	 * 
	 * @param model 뷰에 전달할 데이터 컨테이너(request에 저장)
	 * @return 이동할 뷰 이름 -> ViewResolver가 /WEB-INF/views/home.jsp로 변환
	 */
	@GetMapping
	public String home(Model model) {
		model.addAttribute("message","Spring MVC 테스트");
		model.addAttribute("serverTime",new Date().toLocaleString());
		return "home";
	}
	
	
	
}
