package com.spring.controller;

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
	private final List<MenuDTO> menuList = List.of(
			new MenuDTO(1L, "불고기버거", "버거", 8500),
			new MenuDTO(2L, "치킨버거", "버거", 9000), 
			new MenuDTO(3L, "새우버거", "버거", 8000),
			new MenuDTO(4L, "아이스아메리카노", "음료", 3500));
	
	@GetMapping("/new")
	public ModelAndView orderForm(ModelAndView view, Long menuId) {
		OrderDTO order = new OrderDTO();
		if(menuId != null)
			order.setMenuId(menuId); 
		
		view.addObject("orderForm", order);
		view.addObject("menus", menuList);
		view.setViewName("order/form");
		return view;
	}

	@PostMapping
	public ModelAndView orderSubmit(OrderDTO order, ModelAndView view) {
		// 주문 처리 로직
		MenuDTO menu = menuList.stream()
				.filter(m -> m.getId() == order.getMenuId())
				.findFirst()
				.orElse(new MenuDTO(0L, "Unknown", "Unknown", 0));

		int totalPrice = menu.getPrice() * order.getQuantity();

		view.addObject("orderForm", order);
		view.addObject("totalPrice", totalPrice);
		view.addObject("menu", menu);
		
		view.setViewName("/order/result");
		return view;
	}
}






