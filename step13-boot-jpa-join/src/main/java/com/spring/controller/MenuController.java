package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.entity.MenuItem;
import com.spring.service.MenuItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/menus")
public class MenuController {

    private final MenuItemService menuItemService;

    public MenuController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public ModelAndView MenuList(ModelAndView view) {
        view.addObject("menus", menuItemService.findAll());
        view.setViewName("menu/list");
        return view;
    }

    @GetMapping("/new")
    public ModelAndView saveView(ModelAndView view) {
        view.addObject("menu", new MenuItem());
        view.setViewName("menu/form");
        return view;
    }

    @PostMapping
    public ModelAndView save(@Valid @ModelAttribute MenuItem menu, BindingResult bindingResult, ModelAndView view) {
        if (bindingResult.hasErrors()) {
            view.addObject("menu", menu);
            view.setViewName("menu/form");
            return view;
        }
        menuItemService.save(menu);
        view.addObject("message", "메뉴가 등록되었습니다.");
        view.setViewName("redirect:/menus");
        return view;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView view, @PathVariable("id") long id) {
        view.addObject("menu", menuItemService.findById(id));
        view.setViewName("menu/form");
        return view;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView update(@PathVariable("id") long id, @Valid @ModelAttribute MenuItem menu,
            BindingResult bindingResult, ModelAndView view) {
        if (bindingResult.hasErrors()) {
            view.addObject("menu", menu);
            view.setViewName("menu/form");
            return view;
        }
        menu.setId(id);
        menuItemService.update(menu);
        view.addObject("message", "메뉴가 수정되었습니다.");
        view.setViewName("redirect:/menus");
        return view;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id, RedirectAttributes ra) {
        menuItemService.delete(id);
		ra.addFlashAttribute("message","메뉴가 삭제되었습니다.");
		return "redirect:/menus";
    }
}
