package com.spring.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.spring.dto.MenuDTO;
import com.spring.service.MenuService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ModelAndView list(ModelAndView view,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "available", required = false) Boolean available) {
        List<MenuDTO> list = null;

        if ((category != null && !category.isEmpty())
                || (keyword != null && !keyword.isEmpty())
                || available != null) {
            list = menuService.search(keyword, category, available);
        } else {
            list = menuService.findAll();
        }

        view.setViewName("menu/list");
        // 카테고리 등록
        view.addObject("categories", List.of("버거", "사이드", "음료", "디저트"));
        view.addObject("menus", list);
        return view;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        int result = menuService.deleteById(id);
        if (result == 0)
            redirectAttributes.addFlashAttribute(
                    "failMessage", "메뉴 삭제에 실패했습니다. 메뉴를 다시 확인해주세요.");
        else
            redirectAttributes.addFlashAttribute(
                    "successMessage", "메뉴가 성공적으로 삭제되었습니다.");
        return "redirect:/menus";
    }

    @GetMapping("/new")
    public ModelAndView createView(ModelAndView view) {
        view.addObject("menu", new MenuDTO());
        view.addObject("categories", List.of("버거", "사이드", "음료", "디저트"));
        view.setViewName("menu/form");
        return view;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editView(ModelAndView view, @PathVariable Long id) {
        view.addObject("menu", menuService.findById(id));
        view.addObject("categories", List.of("버거", "사이드", "음료", "디저트"));
        view.setViewName("menu/form");
        return view;
    }

    @PostMapping
    public String save(@Valid MenuDTO menu, RedirectAttributes redirectAttributes,
            BindingResult bindingResult, Model view) {
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception("메뉴 등록에 실패했습니다. 입력값을 다시 확인해주세요.");
            }
            menuService.save(menu);
        } catch (Exception e) {
            view.addAttribute("menu", menu);
            view.addAttribute("categories", List.of("버거", "사이드", "음료", "디저트"));
            e.printStackTrace();
            return "menu/form";
        }
        return "redirect:/menus";
    }

    @PostMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView view, @PathVariable Long id,
         @Valid MenuDTO menu, BindingResult bindingResult) {
            try{
                if (bindingResult.hasErrors()) throw new Exception("메뉴 수정에 실패했습니다. 입력값을 다시 확인해주세요.");

                menu.setId(id);
                int result = menuService.update(menu);
                if (result == 0) throw new Exception("메뉴 수정에 실패했습니다. 메뉴를 다시 확인해주세요.");
                view.setViewName("redirect:/menus");   
            } catch (Exception e) {
                    view.addObject("menu", menu);
                    view.addObject("categories", List.of("버거", "사이드", "음료", "디저트"));
                    view.setViewName("menu/form");
            }
            return view;
    }


}
