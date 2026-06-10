package com.spring.service;

import com.spring.dto.MenuDTO;
import com.spring.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 컨트롤러와 저장소(Repository) 사이에서 가교 역할을 합니다.
 */
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    // 생성자 주입
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /** 전체 목록 가져오기 */
    public List<MenuDTO> findAll() {
        return menuRepository.findAll();
    }

    /** 판매 가능 메뉴만 가져오기 */
    public List<MenuDTO> findAvailable() {
        return menuRepository.findByAvailable();
    }

    /** 카테고리별 조회 */
    public List<MenuDTO> findByCategory(String category) {
        return menuRepository.findByCategory(category);
    }

    /** 이름 검색 수행 */
    public List<MenuDTO> search(String keyword) {
        return menuRepository.findByNameContaining(keyword);
    }

    /** 단건 상세 조회 */
    public Optional<MenuDTO> findById(Long id) {
        return menuRepository.findById(id);
    }

    /** 신규 메뉴 등록 */
    public MenuDTO save(MenuDTO menuDTO) {
        return menuRepository.save(menuDTO);
    }

    /** 메뉴 정보 수정 로직 */
    public MenuDTO update(Long id, MenuDTO form) {
        // 기존 데이터를 먼저 찾아옵니다.
        MenuDTO item = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다. id=" + id));
        
        // 폼에서 넘어온 새로운 정보로 변경(Update)합니다.
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setCategory(form.getCategory());
        item.setDescription(form.getDescription());
        item.setAvailable(form.isAvailable());
        
        // 다시 저장소에 저장하여 반영합니다.
        return menuRepository.save(item);
    }

    /** 메뉴 삭제 */
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

}
