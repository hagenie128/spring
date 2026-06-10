package com.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dto.MenuDTO;
import com.spring.service.MenuService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/menus")
public class MenuController {
	
	private final MenuService menuService;

	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping({"", "/"})
	public String list(Model model,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "available", required = false) Boolean available) {
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		model.addAttribute("available", available);
		model.addAttribute("categories", List.of("버거","사이드","음료"));
		if ((keyword != null && !keyword.isEmpty()) && available != null)
			model.addAttribute("menus", menuService.findByNameContaining(keyword, available));
		else if ((category != null && !category.isEmpty()) && available != null)
			model.addAttribute("menus", menuService.findByCategoryContaining(category));
		else if (keyword != null && !keyword.isEmpty())
			model.addAttribute("menus", menuService.findByNameContaining(keyword));
		else if (category != null && !category.isEmpty())
			model.addAttribute("menus", menuService.findByCategoryContaining(category));
		else
			model.addAttribute("menus", menuService.findAll());
		return "menu/list";
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView editView(ModelAndView view,@PathVariable(name = "id") Long id) {
		view.addObject("categories", List.of("버거","사이드","음료"));
		view.addObject("menu", menuService.findById(id));
		view.setViewName("menu/form");
		
		return view;
	}
	
	@PostMapping("/{id}/edit")
	public String edit(Model model, @PathVariable(name = "id") Long id,
				@ModelAttribute("menu") @Valid MenuDTO menu, 
				BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("categories", List.of("버거","사이드","음료"));
			return "menu/form";
		}
		menu.setId(id);
		menuService.update(menu);
		redirectAttributes.addFlashAttribute("successMessage", "데이터 수정 성공");
		
		return "redirect:/menus";
	}
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id,
			 RedirectAttributes redirectAttributes) {
		try {
			menuService.deleteById(id);
			redirectAttributes.addFlashAttribute("successMessage", "데이터 삭제 성공");
		}catch (Exception e) {
			redirectAttributes.addFlashAttribute("failMessage", "데이터 삭제 실패");
		}
		return "redirect:/menus";
	}

	@GetMapping("/new")
	public String saveView(Model model) {
		model.addAttribute("categories", List.of("버거","사이드","음료"));
		model.addAttribute("menu", new MenuDTO(null, null, null, null, true));
		return "menu/form";
	}

	@PostMapping
	public String save(Model model, @ModelAttribute("menu") @Valid MenuDTO menu,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", List.of("버거","사이드","음료"));
			return "menu/form";
		}
		menuService.save(menu);
		redirectAttributes.addFlashAttribute("successMessage", "데이터 등록 성공");
		return "redirect:/menus";
	}
}
