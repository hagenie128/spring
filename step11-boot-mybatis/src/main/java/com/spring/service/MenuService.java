package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.dto.MenuDTO;
import com.spring.mapper.MenuMapper;

import jakarta.validation.Valid;

@Service
public class MenuService {
    private final MenuMapper menuMapper;

    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public List<MenuDTO> findAll() {
        return menuMapper.findAll();
    }

    public List<MenuDTO> search(String keyword, String category, Boolean available) {
        return menuMapper.search(keyword, category, available);
    }

    public int deleteById(Long id) {
        return menuMapper.deleteById(id);
    }

    public MenuDTO findById(Long id) {
        return menuMapper.findById(id);
    }

    public void save(MenuDTO menu) {
        menuMapper.save(menu);
    }

    public int update(MenuDTO menu) {
        return menuMapper.update(menu);
    }
    
}
