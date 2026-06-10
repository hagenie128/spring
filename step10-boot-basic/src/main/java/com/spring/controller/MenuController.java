package com.spring.controller;

import com.spring.dto.MenuDTO;
import com.spring.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 메뉴 관리(CRUD)를 위한 컨트롤러입니다.
 * @RequestMapping("/menus"): 이 컨트롤러의 모든 메서드는 /menus 경로를 기반으로 동작합니다.
 */
@Controller
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    // 생성자 주입: MenuService를 스프링으로부터 주입받습니다.
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 메뉴 목록 조회 및 검색 기능
     * @param keyword: 검색어 (Optional)
     */
    @GetMapping
    public ModelAndView list(ModelAndView view, String keyword){
        List<MenuDTO> list;
        // 검색어가 있으면 검색 결과를, 없으면 전체 목록을 가져옵니다.
        if (keyword != null && !keyword.isEmpty()) {
            list = menuService.search(keyword);
            view.addObject("keyword", keyword); // 검색어 유지
        } else {
            list = menuService.findAll();
        }

        view.addObject("menus", list); // 화면에 전달할 데이터
        view.setViewName("menu/list"); // 렌더링할 템플릿: menu/list.html

        return view;
    }

    /**
     * 메뉴 삭제 처리
     * @param id: 삭제할 메뉴의 고유 번호
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // RedirectAttributes: 리다이렉트 시 데이터를 전달하기 위해 사용 (일회성 알림창 등)
        redirectAttributes.addFlashAttribute("successMessage", "메뉴가 삭제되었습니다.");
        menuService.delete(id);
        return "redirect:/menus"; // 삭제 후 다시 목록 페이지로 이동
    }

    /**
     * 메뉴 수정 폼(화면) 호출
     */
    @GetMapping("/{id}/edit")
    public ModelAndView updateForm(@PathVariable Long id, ModelAndView view){
        // 해당 ID의 메뉴 정보를 찾아옵니다. 없으면 예외 발생.
        MenuDTO menu = menuService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("메뉴를 찾을 수 없습니다. id=" + id));
        
        view.addObject("menu", menu);
        view.addObject("categories", List.of("커피", "음료", "디저트")); // 카테고리 목록 전달
        view.setViewName("menu/form");
        return view;
    }

    /**
     * 메뉴 수정 실행
     * @Valid: 입력값 검증(Validation) 활성화
     * @ModelAttribute: 폼 데이터를 객체로 매핑
     * BindingResult: 검증 오류 정보를 담는 객체
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute MenuDTO menu,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, Model model) {
        
        // 검증 오류 발생 시 다시 폼 화면으로 이동
        if (bindingResult.hasErrors()) {
            model.addAttribute("menu", menu);
            model.addAttribute("categories", List.of("커피", "음료", "디저트"));
            return "menu/form";
        }
        
        redirectAttributes.addFlashAttribute("successMessage", "메뉴가 수정되었습니다.");
        menuService.update(id, menu);
        return "redirect:/menus";
    }

    /**
     * 신규 메뉴 등록 폼 호출
     */
    @GetMapping("/new")
    public ModelAndView newForm(ModelAndView view){
        view.addObject("menu", new MenuDTO()); // 빈 DTO 객체 전달 (폼 바인딩용)
        view.addObject("categories", List.of("커피", "음료", "디저트"));
        view.setViewName("menu/form");
        return view;
    }

    /**
     * 신규 메뉴 등록 실행
     */
    @PostMapping
    public String create(@Valid @ModelAttribute MenuDTO menu, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, Model model) {
        
        // 검증 오류 확인
        if (bindingResult.hasErrors()) {
            model.addAttribute("menu", menu);
            model.addAttribute("categories", List.of("커피", "음료", "디저트"));
            return "menu/form";
        }
        
        redirectAttributes.addFlashAttribute("successMessage", "메뉴가 등록되었습니다.");
        menuService.save(menu); // 데이터 저장
        return "redirect:/menus";
    }
}
