package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.service.LoanService;

@Controller
@RequestMapping("/loans")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	/**
	 * ━━ 그룹 B: TODO 7 ━━
	 *
	 * 1. loanService.findAllWithStudent() 로 목록 조회
	 * 2. model.addAttribute("loans", ...) 추가
	 * 3. "loan/list" 뷰 반환
	 */
	@GetMapping
	public String list(Model model) {
		// ???
		return "loan/list";
	}
}
