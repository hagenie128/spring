package com.spring.problem04;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	//manager.jsp로 이동
    @GetMapping("/")
    public String manager() {
		return "menu/manager";
	}
}