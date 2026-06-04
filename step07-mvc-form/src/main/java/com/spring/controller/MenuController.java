package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.dto.MenuDTO;

@Controller
@RequestMapping("/menus")
public class MenuController {
	private final List<MenuDTO> menuList = List.of(
			new MenuDTO(1L, "불고기버거", "버거", 8500),
            new MenuDTO(2L, "치킨버거", "버거", 9000),
            new MenuDTO(3L, "새우버거", "버거", 8000),
            new MenuDTO(4L, "아이스아메리카노", "음료", 3500));
	
	@GetMapping
	public ModelAndView list(ModelAndView view) {
		view.addObject("menus", menuList);
		view.setViewName("menus/list");
		return view;
	}
	@GetMapping("/search")
	public ModelAndView search(ModelAndView view,String keyword) {
		//리스트 검색 결과 저장
		//검색어가 메뉴명에 포함되어 있거나? 검색어가 카테고리에 포함되어 있거나	
		List<MenuDTO> list = menuList.stream().filter(dto -> 
			dto.getName().contains(keyword) || 
			dto.getCategory().contains(keyword)).toList();
//		for (MenuDTO menuDTO : menuList) {
//			if(menuDTO.getName().contains(keyword) || menuDTO.getCategory().contains(keyword))
//				list.add(menuDTO);
//		}
		view.addObject("menus", list);
		view.setViewName("menus/list");
		return view;
	}
	
	@GetMapping("/{menuId}")
	public ModelAndView detail(@PathVariable("menuId") long menuId, ModelAndView view) {
		for(MenuDTO dto : menuList) {
			if(dto.getId() == menuId) {
				view.addObject("menu",dto);
				break;
			}
		}
		view.setViewName("menus/detail");
		return view;
	}
}
