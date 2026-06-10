package com.spring.problem01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuController {
 
    @GetMapping("/menus")
    public String list(Model model) {
        // TODO 1: model에 "storeName" 이름으로 "키오스크 1호점" 문자열을 추가하세요.
        model.addAttribute("storeName", "키오스크 1호점");
        // TODO 2: model에 "menus" 이름으로 아래 리스트를 추가하세요.
        //         List.of("불고기버거", "치킨버거", "새우버거", "감자튀김", "콜라")
        model.addAttribute("menus", List.of("불고기버거", "치킨버거", "새우버거", "감자튀김", "콜라"));

        return "menu/list";
    }
}
