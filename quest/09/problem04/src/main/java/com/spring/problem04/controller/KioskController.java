package com.spring.problem04.controller;

import com.spring.problem04.dto.MenuItem;
import com.spring.problem04.dto.OrderForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/kiosk")
public class KioskController {

    // 메뉴 목록 (완성된 코드)
    private final List<MenuItem> menuList = List.of(
        new MenuItem(1L, "불고기버거",     "버거", 8500),
        new MenuItem(2L, "치킨버거",       "버거", 9000),
        new MenuItem(3L, "새우버거",       "버거", 8000),
        new MenuItem(4L, "아이스아메리카노", "음료", 3500)
    );

    // TODO 1: GET /kiosk/menus
    //   - model에 "menus" 키로 menuList 전체를 추가합니다.
    //   - 뷰 이름 "kiosk/menus"를 반환합니다.
    @GetMapping("/menus")
    public String list(Model model) {
    	model.addAttribute("menus",menuList);
    	return "kiosk/menus";
    }
    
    // TODO 3: GET /kiosk/menus/search?keyword=xxx
    //   - @RequestParam(defaultValue="") String keyword 로 검색어를 받습니다.
    //   - keyword가 비어있으면 menuList 전체를, 아니면 name 또는 category에
    //     keyword가 포함된 항목만 필터링합니다. (대소문자 무시: toLowerCase() 활용)
    //   - model에 "menus"와 "keyword"를 추가합니다.
    //   - 뷰 이름 "kiosk/menus"를 반환합니다.
    //
    //   주의: @GetMapping 경로가 /menus/{menuId} 와 /menus/search 가 충돌하지 않도록
    //         search 매핑을 {menuId} 보다 먼저 선언하거나, 별도 경로로 설계하세요.
    @GetMapping("/menus/menus/search")
    public String menuList(@RequestParam(defaultValue="") String keyword,Model model) {
    	List<MenuItem> menus = null;
    	if(keyword==null) {
    		model.addAttribute("menus",menuList);
    	}else {
    		for (MenuItem item : menuList) {
				if(item.getCategory().contains(keyword.toLowerCase()) || item.getName().contains(keyword.toLowerCase())){
					menus.add(item);
				}
			}
    		model.addAttribute("menus",menus);
    	}
    	model.addAttribute("keyword",keyword);
    	return "kiosk/detail";
    }
    
    // TODO 2: GET /kiosk/menus/{menuId}
    //   - @PathVariable Long menuId로 경로 변수를 받습니다.
    //   - menuList에서 id가 일치하는 MenuItem을 찾아 model에 "menu" 키로 추가합니다.
    //   - 뷰 이름 "kiosk/detail"을 반환합니다.
    @GetMapping("/menus/{menuId}")
    public String menuDetail(@PathVariable Long menuId,Model model) {
    	model.addAttribute("menu",menuList.stream().filter(item-> item.getId() == menuId).findFirst().orElse(new MenuItem(-1L, "해당 메뉴 없음", null, 0)));
    	return "kiosk/detail";
    }
    


    // TODO 4: GET /kiosk/orders/new?menuId=xxx
    //   - @RequestParam(required=false) Long menuId 로 선택된 메뉴 ID를 받습니다.
    //   - model에 "menus" (menuList 전체), "selectedMenuId" (menuId), "orderForm" (new OrderForm()) 추가
    //   - 뷰 이름 "kiosk/form"을 반환합니다.
    @GetMapping("orders/new")
    public String form(@RequestParam(required=false) Long menuId,Model model) {
    	model.addAttribute("menus",menuList);
    	model.addAttribute("selectedMenuId",menuId);
    	model.addAttribute("orderForm",new OrderForm());
    	return "kiosk/form";
    }

    // TODO 5: POST /kiosk/orders
    //   - @ModelAttribute OrderForm form 으로 폼 데이터를 받습니다.
    //   - menuList에서 form.getMenuId()와 일치하는 MenuItem을 찾습니다.
    //   - totalPrice = menu.getPrice() * form.getQuantity() 로 계산합니다.
    //   - model에 "orderForm", "menu", "totalPrice"를 추가합니다.
    //   - 뷰 이름 "kiosk/result"를 반환합니다.
    @PostMapping("orders")
    public String orders(@ModelAttribute OrderForm form,Model model) {
    	MenuItem menu = menuList.stream().filter(item-> item.getId()==form.getMenuId()).findFirst().orElse(new MenuItem(-1L, "해당메뉴 없음", null, 0));
    	int totalPrice = menu.getPrice() * form.getQuantity();
    	model.addAttribute("orderForm",form);
    	model.addAttribute("menu",menu);
    	model.addAttribute("totalPrice",totalPrice);
    	return "kiosk/result";
    }
}
