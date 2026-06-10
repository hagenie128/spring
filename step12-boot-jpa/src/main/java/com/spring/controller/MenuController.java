package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.service.MenuService;

@Controller
@RequestMapping("/menus")
public class MenuController {
	private final MenuService menuService;

	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping
	public String list(String keyword, String category,Model model) {
		model.addAttribute("keyword",keyword);
		model.addAttribute("category",category);
		model.addAttribute("menus",menuService.findAll());
		return "menu/list";
	}
	
}
