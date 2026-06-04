package com.spring.problem04.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.problem04.dto.MenuItem;
import com.spring.problem04.dto.OrderForm;

@Controller
@RequestMapping("/kiosk")
public class KioskController {

    // TODO 7: MenuItem 4개를 담은 menuList 작성
    // 불고기버거/버거/8500, 치킨버거/버거/9000, 새우버거/버거/8000, 아메리카노/음료/3500
	private final List<MenuItem> menuList = List.of(
			new MenuItem(1, "불고기버거", "버거", 8500),
			new MenuItem(2, "치킨버거", "버거", 9000),
			new MenuItem(3, "새우버거", "버거", 8000),
			new MenuItem(4, "아메리카노", "음료", 3500)
			);
	
    @GetMapping("/menus")
    public String menus(Model model) {
        // TODO 8: model에 "menus" 추가
    	model.addAttribute("menus", menuList);
        return "kiosk/menus";
    }

    @GetMapping("/menus/{menuId}")
    public String detail(@PathVariable Long menuId, Model model) {
        // TODO 9: menuId에 해당하는 메뉴를 찾아 model에 "menu"로 추가
    	MenuItem menu = menuList.stream().filter(item->item.getId()==menuId).findFirst()
				.orElse(new MenuItem(0, "알수없는 메뉴", "기타", 0));
    	model.addAttribute("menu",menu);
        return "kiosk/detail";
    }

    @GetMapping("/menus/search")
    public String search(@RequestParam(defaultValue = "") String keyword, Model model) {
        // TODO 10: 메뉴명 또는 카테고리에 keyword가 포함된 메뉴만 model에 "menus"로 추가
    	List<MenuItem> list = menuList.stream().filter(dto -> 
		dto.getName().contains(keyword) || 
		dto.getCategory().contains(keyword)).toList();
    	model.addAttribute("menus",list);
        model.addAttribute("keyword", keyword);
        return "kiosk/menus";
    }

    @GetMapping("/orders/new")
    public String orderForm(@RequestParam(required = false, defaultValue = "1") Long menuId, Model model) {
        // TODO 11: 전체 메뉴 목록과 선택된 menuId를 model에 추가
    	model.addAttribute("menus",menuList);
    	model.addAttribute("selectedMenuId",menuId);
        return "kiosk/form";
    }

    @PostMapping("/orders")
    public String order(@ModelAttribute OrderForm form, Model model) {
        // TODO 12: form.menuId로 선택 메뉴를 찾고 총 금액을 계산
    	MenuItem menu = menuList.stream().filter(item->item.getId()==form.getMenuId()).findFirst()
				.orElse(new MenuItem(0, "알수없는 메뉴", "기타", 0));
        // TODO 13: model에 orderForm, menu, totalPrice 추가
    	model.addAttribute("orderForm",form);
    	model.addAttribute("menu",menu);
    	model.addAttribute("totalPrice",menu.getPrice()*form.getQuantity());
        return "kiosk/result";
    }
}

