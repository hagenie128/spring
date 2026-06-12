package com.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/new")
	public String form(Model model) {
		model.addAttribute("students", studentService.findAll());
		model.addAttribute("books", bookService.findAll());
		return "loan/form";
	}

	@PostMapping
	public String save(
			@RequestParam("studentId") Long studentId,
			@RequestParam("bookIds") List<Long> bookIds,
			@RequestParam("quantities") List<Integer> quantities,
			RedirectAttributes ra) {
		try {
			var loan = loanService.save(studentId, bookIds, quantities);
			return "redirect:/loans/" + loan.getId();
		} catch (IllegalArgumentException e) {
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/loans/new";
		}
	}

	/**
	 * ━━ TODO 6 ━━
	 * findByIdWithDetails → model "loan", statuses → "loan/detail"
	 */
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		// ???
		return "loan/detail";
	}

	/**
	 * ━━ TODO 7~8 ━━
	 * PathVariable id + RequestParam status → updateStatus → redirect
	 */
	@PostMapping("/{id}/status")
	public String updateStatus(
			@PathVariable("id") Long id,
			@RequestParam("status") LoanStatus status,
			RedirectAttributes ra) {
		// ???
		return "redirect:/loans/" + id;
	}

	/**
	 * ━━ TODO 9 ━━
	 * delete 후 redirect:/loans + flash message
	 */
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable("id") Long id, RedirectAttributes ra) {
		// ???
		return "redirect:/loans";
	}
}
