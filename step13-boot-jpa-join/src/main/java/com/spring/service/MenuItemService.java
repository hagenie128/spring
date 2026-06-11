package com.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.repository.MenuItemRepository;

// 메뉴 조회/등록 기능을 담당할 서비스 계층입니다.
@Service
@Transactional(readOnly =  true)
public class MenuItemService {
	
	private final MenuItemRepository menuItemRepository;

	public MenuItemService(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}
	
	

}
