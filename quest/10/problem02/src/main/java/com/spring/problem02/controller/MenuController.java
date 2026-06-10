package com.spring.problem02.controller;

import com.spring.problem02.dto.MenuDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuController {

    @GetMapping("/menus")
    public String list(Model model) {
        List<MenuDto> menus = List.of(
            new MenuDto("불고기버거",  6500, true,  "BEST"),
            new MenuDto("치킨버거",    5900, true,  "NEW"),
            new MenuDto("새우버거",    6000, false, "NORMAL"),
            new MenuDto("감자튀김",    2500, true,  "BEST"),
            new MenuDto("콜라",        1500, false, "NORMAL")
        );
        model.addAttribute("menus", menus);
        return "menu/list";
    }
}
