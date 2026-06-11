package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {
	// Controller는 요청을 받고, 실제 조회 로직은 Service에 위임합니다.
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping
	public ModelAndView list(ModelAndView view) {
		// 회원 목록을 Model에 담고 templates/member/list.html로 이동합니다.
		view.addObject("members", memberService.findAll());
		view.setViewName("member/list");
		return view;
	}
}







