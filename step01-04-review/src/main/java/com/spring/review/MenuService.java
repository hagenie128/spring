package com.spring.review;

public class MenuService {
	private MenuRepository menuRepository;
	private String storeName;

	public void setMenuRepository(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void printMenus() {
		System.out.println("[MenuService] 매장명: " + storeName);
		System.out.println("[MenuService] 메뉴 목록: " + menuRepository.findAll());
	}
}
