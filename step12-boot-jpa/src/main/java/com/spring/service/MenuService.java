package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.MenuDTO;
import com.spring.repository.MenuRepository;

/**
 * 비즈니스 로직을 처리하는 서비스 계층입니다.
 *
 * @Service: Spring 관리 빈으로 등록
 * @Transactional(readOnly = true): 기본적으로 트랜잭션을 읽기 전용으로 설정하여 성능 최적화
 */
@Service
@Transactional(readOnly = true)
public class MenuService {
	private final MenuRepository menuRepository;

	/**
	 * 생성자 주입
	 */
	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	/**
	 * 모든 메뉴 목록을 조회합니다.
	 */
	public List<MenuDTO> findAll() {
		return menuRepository.findAll();
	}

	/**
	 * 특정 ID를 가진 메뉴 하나를 조회합니다.
	 */
	public MenuDTO findById(Long id) {
		return menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다"));
	}

	/**
	 * 메뉴 정보를 수정합니다.
	 *
	 * [Dirty Checking(변경 감지)]:
	 * JPA에서 엔티티를 조회한 뒤 필드값을 변경하면,
	 * 트랜잭션이 끝나는 시점에 변경된 내용을 자동으로 감지하여 UPDATE SQL을 실행합니다.
	 * 따라서 별도로 save() 메서드를 호출할 필요가 없습니다.
	 */
	@Transactional
	public MenuDTO update(MenuDTO menu) {
		// 1. 영속성 컨텍스트에서 엔티티를 조회 (영속 상태로 만듦)
		MenuDTO raw = findById(menu.getId());
		// 2. 조회된 객체의 필드값 변경
		raw.setName(menu.getName());
		raw.setCategory(menu.getCategory());
		raw.setPrice(menu.getPrice());
		raw.setDescription(menu.getDescription());
		raw.setAvailable(menu.isAvailable());
		// 3. 트랜잭션 종료 시 변경 사항이 DB에 반영됨
		return raw;
	}

	/**
	 * 신규 메뉴를 저장합니다.
	 */
	@Transactional
	public MenuDTO save(MenuDTO menu) {
		// save()는 id가 없으면 INSERT, id가 있으면 UPDATE로 동작합니다.
		return menuRepository.save(menu);
	}

	/**
	 * ID로 메뉴를 삭제합니다.
	 */
	@Transactional
	public void deleteById(Long id) {
		menuRepository.deleteById(id);
	}

	/**
	 * 메뉴를 삭제합니다.
	 */
	@Transactional
	public void delete(Long id) {
		deleteById(id);
	}

	/**
	 * 메뉴명 포함 검색
	 */
	public List<MenuDTO> findByNameContaining(String keyword) {
		// Repository의 쿼리 메서드 이름을 기반으로 LIKE 검색 SQL이 자동 생성됩니다.
		return menuRepository.findByNameContaining(keyword);
	}

	/**
	 * 카테고리 포함 검색
	 */
	public List<MenuDTO> findByCategoryContaining(String category) {
		return menuRepository.findByCategoryContaining(category);
	}


	/**
	 * 메뉴명 포함 검색 + 판매 여부 필터
	 */
    public List<MenuDTO> findByNameContaining(String keyword, Boolean available) {
		return menuRepository.findByNameContainingAndAvailable(keyword,available);
    }

}
