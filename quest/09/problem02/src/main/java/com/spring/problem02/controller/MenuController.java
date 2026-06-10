package com.spring.problem02.controller;

import com.spring.problem02.dto.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    // TODO 1: menuList를 초기화하세요.
    //   - 아래 4개 메뉴를 List.of(...)로 초기화합니다.
    //   - new MenuItem(1L, "불고기버거", "버거", 8500)
    //   - new MenuItem(2L, "치킨버거",  "버거", 9000)
    //   - new MenuItem(3L, "새우버거",  "버거", 8000)
    //   - new MenuItem(4L, "아이스아메리카노", "음료", 3500)
    private final List<MenuItem> menuList = List.of(
    	       new MenuItem(1L, "불고기버거", "버거", 8500),
    	       new MenuItem(2L, "치킨버거",  "버거", 9000),
    	       new MenuItem(3L, "새우버거",  "버거", 8000),
    	       new MenuItem(4L, "아이스아메리카노", "음료", 3500)
    		);

    // TODO 2: GET /menu 요청을 처리하는 메서드를 완성하세요.
    //   - model에 "menus" 키로 menuList를 추가합니다.
    //   - 뷰 이름 "menu/list"를 반환합니다.
    @GetMapping
    public String list(Model model) {
        // TODO 2
    	model.addAttribute("menus",menuList);
        return "menu/list";
    }

    // TODO 3: GET /menu/{id} 요청을 처리하는 메서드를 완성하세요.
    //   - menuList에서 id가 일치하는 MenuItem을 찾습니다. (stream + filter 활용)
    //   - model에 "menu" 키로 찾은 MenuItem을 추가합니다.
    //   - 뷰 이름 "menu/detail"을 반환합니다.
    //   - 일치하는 메뉴가 없으면 예외를 던지거나 빈 Optional로 처리해도 됩니다.
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // TODO 3
    	model.addAttribute("menu", menuList.stream().filter(item-> 
		item.getId() == id).findFirst().orElse(
				new MenuItem(0L, "잘못된 메뉴", null, 0)));
        return "menu/detail";
    }
}
