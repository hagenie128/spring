package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{
	
		// 전체 주문 목록 조회 메뉴명으로 정렬(오름차순)해서 조회
		List<MenuItem> findAllByOrderByNameAsc();
		// 메뉴 조회, 카테고리로 검색
		List<MenuItem> findByCategory(String category);
		// 메뉴 조회, 주문이 가능한 메뉴만 검색
		List<MenuItem> findByAvailableTrue();
		

}
