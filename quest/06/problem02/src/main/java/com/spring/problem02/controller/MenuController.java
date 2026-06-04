package com.spring.problem02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@GetMapping
	public String menu(Model model) {
		model.addAttribute("menuName", "불고기버거");
		model.addAttribute("price", 8500);
		model.addAttribute("category", "버거");
		return "menu";
	}

	@GetMapping("/order")
	public String order(@RequestParam String item, @RequestParam(defaultValue = "1") int qty, Model model) {
		model.addAttribute("itemName", item);
		model.addAttribute("quantity", qty);
		model.addAttribute("totalPrice", qty * 8500);
		return "order";
	}

}
