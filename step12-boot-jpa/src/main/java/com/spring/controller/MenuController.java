package com.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dto.MenuDTO;
import com.spring.service.MenuService;

import jakarta.validation.Valid;

/**
 * 메뉴와 관련된 모든 웹 요청을 처리하는 컨트롤러 클래스입니다.
 */
@Controller
@RequestMapping("/menus")
public class MenuController {
	
	private final MenuService menuService;

	/**
	 * 생성자 주입(Constructor Injection):
	 * Spring이 MenuService 객체를 생성하여 이 컨트롤러에 자동으로 넣어줍니다.
	 */
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	/**
	 * 메뉴 목록을 조회하고 검색 기능(이름, 카테고리)을 제공합니다.
	 *
	 * @param keyword 검색어 (메뉴명)
	 * @param category 필터링할 카테고리
	 * @param available 판매 여부 조건
	 */
	@GetMapping({"", "/"})
	public String list(Model model,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "available", required = false) Boolean available) {
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		model.addAttribute("available", available);
		model.addAttribute("categories", List.of("버거","사이드","음료"));
		if ((keyword != null && !keyword.isEmpty()) && available != null)
			// 이름 검색 (available 조건 포함)
			model.addAttribute("menus", menuService.findByNameContaining(keyword, available));
		else if ((category != null && !category.isEmpty()) && available != null)
			// 카테고리 검색
			model.addAttribute("menus", menuService.findByCategoryContaining(category));
		else if (keyword != null && !keyword.isEmpty())
			// 이름 검색
			model.addAttribute("menus", menuService.findByNameContaining(keyword));
		else if (category != null && !category.isEmpty())
			// 카테고리 검색
			model.addAttribute("menus", menuService.findByCategoryContaining(category));
		else
			// 전체 목록 조회
			model.addAttribute("menus", menuService.findAll());
		return "menu/list"; // templates/menu/list.html 렌더링
	}
	
	/**
	 * 수정 폼 페이지를 보여줍니다.
	 *
	 * @param id 수정할 메뉴의 고유 ID
	 */
	@GetMapping("/{id}/edit")
	public ModelAndView editView(ModelAndView view,@PathVariable(name = "id") Long id) {
		view.addObject("categories", List.of("버거","사이드","음료"));
		view.addObject("menu", menuService.findById(id)); // ID로 기존 데이터 조회
		view.setViewName("menu/form"); // templates/menu/form.html 사용
		
		return view;
	}
	
	/**
	 * 수정 폼에서 제출된 데이터를 처리합니다.
	 *
	 * @param menu 폼에서 바인딩된 데이터 객체
	 * @param bindingResult 유효성 검사(Validation) 결과
	 */
	@PostMapping("/{id}/edit")
	public String edit(Model model, @PathVariable(name = "id") Long id,
				@ModelAttribute("menu") @Valid MenuDTO menu, 
				BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		// 입력값 검증 오류가 있는 경우 다시 폼으로 이동
		if(bindingResult.hasErrors()) {
			model.addAttribute("categories", List.of("버거","사이드","음료"));
			return "menu/form";
		}
		menu.setId(id);
		menuService.update(menu); // 데이터 업데이트 실행
		// 리다이렉트 시 한 번만 사용할 수 있는 메시지 전달
		redirectAttributes.addFlashAttribute("successMessage", "데이터 수정 성공");
		
		return "redirect:/menus";
	}

	/**
	 * 메뉴를 삭제 처리합니다.
	 */
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id,
			 RedirectAttributes redirectAttributes) {
		try {
			menuService.deleteById(id);
			redirectAttributes.addFlashAttribute("successMessage", "데이터 삭제 성공");
		}catch (Exception e) {
			redirectAttributes.addFlashAttribute("failMessage", "데이터 삭제 실패");
		}
		return "redirect:/menus";
	}

	/**
	 * 신규 메뉴 등록 폼 페이지를 보여줍니다.
	 */
	@GetMapping("/new")
	public String saveView(Model model) {
		model.addAttribute("categories", List.of("버거","사이드","음료"));
		model.addAttribute("menu", new MenuDTO(null, null, null, null, true));
		return "menu/form";
	}

	/**
	 * 등록 폼에서 제출된 신규 메뉴 데이터를 저장합니다.
	 */
	@PostMapping
	public String save(Model model, @ModelAttribute("menu") @Valid MenuDTO menu,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", List.of("버거","사이드","음료"));
			return "menu/form";
		}
		menuService.save(menu);
		redirectAttributes.addFlashAttribute("successMessage", "데이터 등록 성공");
		return "redirect:/menus";
	}
}
