package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.MenuDTO;
import com.spring.repository.MenuRepository;

@Service
@Transactional(readOnly = true)
public class MenuService {
	private final MenuRepository menuRepository;

	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	public List<MenuDTO> findAll() {
		return menuRepository.findAll();
	}

	public MenuDTO findById(Long id) {
		return menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다"));
	}

	@Transactional
	public MenuDTO update(MenuDTO menu) {
		MenuDTO raw = findById(menu.getId());
		raw.setName(menu.getName());
		raw.setCategory(menu.getCategory());
		raw.setPrice(menu.getPrice());
		raw.setDescription(menu.getDescription());
		raw.setAvailable(menu.isAvailable());
		return raw;
	}

	@Transactional
	public MenuDTO save(MenuDTO menu) {
		return menuRepository.save(menu);
	}

	@Transactional
	public void deleteById(Long id) {
		menuRepository.deleteById(id);
	}

	@Transactional
	public void delete(Long id) {
		deleteById(id);
	}

	public List<MenuDTO> findByNameContaining(String keyword) {
		return menuRepository.findByNameContaining(keyword);
	}

	public List<MenuDTO> findByCategoryContaining(String category) {
		return menuRepository.findByCategoryContaining(category);
	}


    public List<MenuDTO> findByNameContaining(String keyword, Boolean available) {
		return menuRepository.findByNameContainingAndAvailable(keyword,available);
    }

}
