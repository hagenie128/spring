package com.spring.problem04;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO 1: @RestController 추가
// TODO 2: @RequestMapping("/api/menus") 추가
@RestController
@RequestMapping("/api/menus")
public class MenuApiController {

    private final List<MenuDto> menus = new ArrayList<>(List.of(
        new MenuDto(1L, "아메리카노", 4000, true),
        new MenuDto(2L, "카페라떼", 4500, true),
        new MenuDto(3L, "녹차라떼", 4500, false)
    ));

    // TODO 3: GET /api/menus → List<MenuDto> 전체 반환
    //   @GetMapping 추가, 반환 타입: List<MenuDto>
    @GetMapping
    public List<MenuDto> list(){
    	return menus;
    }
    
    // TODO 4: GET /api/menus/available → available=true인 메뉴만 필터링하여 반환
    //   @GetMapping("/available")
    @GetMapping("/available")
    public List<MenuDto> available(){
    	List<MenuDto> menuList = new ArrayList<MenuDto>();
    	for (MenuDto dto : menus) {
    		if(dto.isAvailable()==true)menuList.add(dto);
		}
    	return menuList;
    }

    // TODO 5: POST /api/menus → @RequestBody MenuDto 받아서 리스트에 추가 후 반환
    //   새 id는 menus.size() + 1L
    @PostMapping
	public MenuDto addMenu(@RequestBody MenuDto menuDto) {
		menuDto.setId((long) (menus.size() + 1));
		menus.add(menuDto);
		return menuDto;
    }
}
