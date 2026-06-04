package com.spring.controller;

import java.text.Normalizer.Form;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.dto.MenuDTO;
import com.spring.dto.OrderDTO;

@Controller
@RequestMapping("/orders")
public class OrderController {
	private final List<MenuDTO> menuList = List.of(new MenuDTO(1L, "불고기버거", "버거", 8500),
			new MenuDTO(2L, "치킨버거", "버거", 9000), new MenuDTO(3L, "새우버거", "버거", 8000),
			new MenuDTO(4L, "아이스아메리카노", "음료", 3500));

	@GetMapping("/new")
	public ModelAndView orderForm(long menuId, ModelAndView view) {
		view.addObject("menus", menuList);
		view.addObject("selectedMenuId", menuId);
		view.setViewName("orders/form");

		return view;
	}

	@PostMapping
	public ModelAndView order(OrderDTO order, ModelAndView view) {
		// 선택된 메뉴 저장
		MenuDTO menu = menuList.stream().filter(item -> 
				item.getId() == order.getMenuId()).findFirst()
				.orElse(new MenuDTO(0, "알수없는 메뉴", "기타", 0));
		view.addObject("menu",menu);
		// 주문 내용 저장
		view.addObject("orderForm", order);
		// 주문 총액
		view.addObject("totalPrice", order.getQuantity() * menu.getPrice());
		// 이동 경로 orders/result
		view.setViewName("orders/result");
		return view;
	}
}
