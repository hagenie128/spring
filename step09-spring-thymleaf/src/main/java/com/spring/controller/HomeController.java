package com.spring.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@RequestMapping("/")
	public ModelAndView home(ModelAndView view) {
		view.addObject("serverTime", LocalDateTime.now());
		view.setViewName("home");
		return view;
	}
}
