package com.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.entity.LoanStatus;
import com.spring.service.BookService;
import com.spring.service.LoanService;
import com.spring.service.StudentService;

@Controller
@RequestMapping("/loans")
public class LoanController {

	private final LoanService loanService;
	private final StudentService studentService;
	private final BookService bookService;

	public LoanController(LoanService loanService, StudentService studentService, BookService bookService) {
		this.loanService = loanService;
		this.studentService = studentService;
		this.bookService = bookService;
	}

	@GetMapping
	public String list(
			@RequestParam(value = "studentId", required = false) Long studentId,
			@RequestParam(value = "status", required = false) LoanStatus status,
			Model model) {
		model.addAttribute("students", studentService.findAll());
		model.addAttribute("statuses", LoanStatus.values());
		model.addAttribute("loans", loanService.search(studentId, status));
		model.addAttribute("selectedStudentId", studentId);
		model.addAttribute("selectedStatus", status);
		return "loan/list";
	}

	/**
	 * ━━ 그룹 B: TODO 6 ━━
	 * students, books 를 model에 담고 "loan/form" 반환
	 * ⚠️ /new 는 /{id} 보다 위에 두세요 (problem04에서도 동일)
	 */
	@GetMapping("/new")
	public String form(Model model) {
		// ???
		return "loan/form";
	}

	/**
	 * ━━ 그룹 B: TODO 7~9 ━━
	 *
	 * TODO 7: @RequestParam("studentId") Long studentId
	 * TODO 8: @RequestParam("bookIds") List<Long> bookIds
	 * TODO 9: @RequestParam("quantities") List<Integer> quantities
	 *         성공 → redirect:/loans/{id}, 실패 → redirect:/loans/new + flash error
	 */
	@PostMapping
	public String save(RedirectAttributes ra) {
		// TODO 7: @RequestParam("studentId") Long studentId
		// TODO 8: @RequestParam("bookIds") List<Long> bookIds
		// TODO 9: @RequestParam("quantities") List<Integer> quantities
		return "redirect:/loans";
	}
}
