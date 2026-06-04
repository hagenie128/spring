package com.spring.problem01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// TODO 1: @Controller 추가
// TODO 2: @RequestMapping("/menus") 추가
@Controller
@RequestMapping("/menus")
public class MenuController {

    private final List<MenuItem> menuList = List.of(
            new MenuItem(1L, "불고기버거", "버거", 8500),
            new MenuItem(2L, "치킨버거", "버거", 9000),
            new MenuItem(3L, "새우버거", "버거", 8000)
    );

    // TODO 3: GET /menus 요청을 처리하는 @GetMapping 추가
    @GetMapping
    public String list(Model model) {
        // TODO 4: model에 "menus" 이름으로 menuList 추가
    	model.addAttribute("menus",menuList);
        return "menu/list";
    }

    // TODO 5: GET /menus/{menuId} 요청을 처리하는 @GetMapping 추가
    @GetMapping("/{menuId}")
    public String detail(@PathVariable Long menuId, Model model) {
        // TODO 6: model에 "menu" 이름으로 findMenu(menuId) 결과 추가
    	model.addAttribute("menu",findMenu(menuId));
        return "menu/detail";
    }

    // TODO 7: GET /menus/search 요청을 처리하는 @GetMapping 추가
    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword, Model model) {
        List<MenuItem> result = menuList.stream()
                .filter(menu -> menu.getName().contains(keyword) || menu.getCategory().contains(keyword))
                .toList();
        // TODO 8: model에 "keyword"와 "menus" 추가
        model.addAttribute("menus",result);
        model.addAttribute("keyword",keyword);
        return "menu/list";
    }

    private MenuItem findMenu(Long menuId) {
        return menuList.stream()
                .filter(menu -> menu.getId().equals(menuId))
                .findFirst()
                .orElse(new MenuItem(0L, "알 수 없는 메뉴", "기타", 0));
    }

    public static class MenuItem {
        private final Long id;
        private final String name;
        private final String category;
        private final int price;

        public MenuItem(Long id, String name, String category, int price) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public int getPrice() {
            return price;
        }
    }
}
