package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.spring.service.MemberService;

import jakarta.validation.Valid;

import com.spring.entity.Member;
import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ModelAndView list(ModelAndView view) {
        List<Member> members = memberService.findAll();
        view.addObject("members", members);
        view.setViewName("member/list");
        return view;
    }

    @GetMapping("/{id}")
    public ModelAndView detail(ModelAndView view, @PathVariable("id") Long id) {
        Member member = memberService.findById(id);
        view.addObject("member", member);
        view.setViewName("member/detail");
        return view;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView view, @PathVariable("id") Long id) {
        Member member = memberService.findById(id);
        view.addObject("member", member);
        view.setViewName("member/form");
        return view;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") Long id, @Valid @ModelAttribute Member member,
            BindingResult bindingResult, ModelAndView view) {
        if (bindingResult.hasErrors()) {    
            view.addObject("member", member);
            view.setViewName("member/form");
            return view;
        } else {
            memberService.update(id, member);
            return new ModelAndView("redirect:/members/" + id);
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return "redirect:/members";
    }

    @GetMapping("/new")
    public ModelAndView newMember(ModelAndView view) {
        view.addObject("member", new Member());
        view.setViewName("member/form");
        return view;
    }

    @PostMapping
    public ModelAndView newMember(@Valid @ModelAttribute Member member,
            BindingResult bindingResult, ModelAndView view) {
        if (bindingResult.hasErrors()) {
            view.addObject("member", member);
            view.setViewName("member/form");
            return view;
        }
        Member newMember = memberService.create(member);
        view.addObject("member", newMember);
        view.setViewName("member/detail");
        return view;
    }

}
