package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.entity.LoanStatus;
import com.spring.service.LoanService;

@Controller
@RequestMapping("/loans")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	/**
	 * ━━ 그룹 B: TODO 4~7 ━━
	 *
	 * TODO 4: @RequestParam(value = "studentId", required = false) Long studentId
	 * TODO 5: @RequestParam(value = "status", required = false) LoanStatus status
	 * TODO 6: loanService.search(...) 결과를 model "loans"에 담기
	 * TODO 7: students, statuses, selectedStudentId, selectedStatus 도 model에 담기
	 */
	@GetMapping
	public String list(Model model, @RequestParam(value = "studentId", required = false) Long studentId, @RequestParam(value = "status", required = false) LoanStatus status)  {
		// TODO 6~7: search 결과 + students, statuses, selected 값 model에 담기
		model.addAttribute("loans",loanService.search(studentId, status));
		model.addAttribute("students", loanService.findAllStudents());
		model.addAttribute("statuses", LoanStatus.values());
		model.addAttribute("selectedStudentId", studentId);
		model.addAttribute("selectedStatus", status);
		return "loan/list";
	}
}
