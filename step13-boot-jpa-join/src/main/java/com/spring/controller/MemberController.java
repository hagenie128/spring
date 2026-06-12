package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.entity.Member;
import com.spring.service.MemberService;

import jakarta.validation.Valid;

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

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id,RedirectAttributes ra){
		memberService.delete(id);
		ra.addFlashAttribute("message","회원이 삭제되었습니다.");
		return "redirect:/members";
	}

	@GetMapping("/new")
	public ModelAndView newMember(ModelAndView view){
		view.addObject("member", new Member());
		view.setViewName("member/form");
		return view;
	}

	@PostMapping
	public ModelAndView newMember(@Valid @ModelAttribute Member member, BindingResult bindingResult, ModelAndView view){
		if(bindingResult.hasErrors()){
			view.addObject("member", member);
			view.setViewName("member/form");
			return view;
		}
		view.addObject("member", memberService.save(member));
		view.addObject("message", "회원이 등록되었습니다.");
		view.setViewName("redirect:/members");
		return view;
	}

	@GetMapping("/{id}")
	public ModelAndView detail(ModelAndView view, @PathVariable("id") long id){
		view.addObject("member", memberService.findById(id));
		view.setViewName("member/detail");
		return view;
	}

	@GetMapping("/{id}/edit")
	public ModelAndView edit(ModelAndView view, @PathVariable("id") long id){
		view.addObject("member", memberService.findById(id));
		view.setViewName("member/form");
		return view;
	}
	
	@PostMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable("id") long id, @Valid @ModelAttribute Member member, BindingResult bindingResult, ModelAndView view){
		if(bindingResult.hasErrors()){
			view.addObject("member", member);
			view.setViewName("member/form");
			return view;
		}
		member.setId(null);
		memberService.update(member);
		view.addObject("message", "회원이 수정되었습니다.");
		view.setViewName("redirect:/members");
		return view;
	}
}







