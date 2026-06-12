package com.spring.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.MenuItem;
import com.spring.repository.MenuItemRepository;

// 메뉴 조회/등록 기능을 담당할 서비스 계층입니다.
@Service
@Transactional(readOnly =  true)
public class MenuItemService {
	
	private final MenuItemRepository menuItemRepository;

	public MenuItemService(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}

    public List<MenuItem> findAll() {
		return menuItemRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

	@Transactional
    public void save(MenuItem menu) {
		menuItemRepository.save(menu);
    }

	public MenuItem findById(long id) {
		return menuItemRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다."));
	}

	@Transactional
	public void update(MenuItem menu) {
		MenuItem raw = findById(menu.getId());
		raw.setName(menu.getName());
		raw.setPrice(menu.getPrice());
		raw.setCategory(menu.getCategory());
		raw.setAvailable(menu.isAvailable());
	}

	@Transactional
	public void delete(long id) {
		menuItemRepository.deleteById(id);
	}
}
