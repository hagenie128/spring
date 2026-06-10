package com.spring.problem03.controller;

import com.spring.problem03.dto.MenuForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MenuController {

    // TODO 1: GET /menus/new → "menu/form" 뷰 반환
    //         model에 "form" 이름으로 new MenuForm()을 추가하세요.
    //         model에 "categories" 이름으로 List.of("버거","사이드","음료","디저트")를 추가하세요.
    @GetMapping("/menus/new")
    public String newForm(Model model) {
        model.addAttribute("form", new MenuForm());
        model.addAttribute("categories", List.of("버거","사이드","음료","디저트"));
        return "menu/form";
    }

    // TODO 2: POST /menus → @Valid로 유효성 검사 후
    //         오류가 있으면 "menu/form"으로 다시 돌아가고 (categories도 다시 추가)
    //         오류가 없으면 redirectAttributes에 "successMessage" 추가 후 redirect:/menus/result
    @PostMapping("/menus")
    public String create(@Valid @ModelAttribute("form") MenuForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", List.of("버거","사이드","음료","디저트"));
            model.addAttribute("form", form);
            return "menu/form";
        }
        redirectAttributes.addFlashAttribute("successMessage",
                                     "메뉴가 성공적으로 등록되었습니다.");
        return "redirect:/menus/result";
    }

    @GetMapping("/menus/result")
    public String result(Model model) {
        return "menu/result";
    }
}
