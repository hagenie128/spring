package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.dto.MenuDTO;

/**
 * DB에 접근하여 메뉴 데이터를 처리하는 인터페이스입니다.
 *
 * JpaRepository<엔티티타입, ID타입>를 상속받으면 기본적인 CRUD 메서드가 자동으로 구현됩니다.
 * 제공되는 기본 메서드:
 * - save(entity): 저장 및 수정
 * - findById(id): ID로 단건 조회
 * - findAll(): 전체 조회
 * - deleteById(id): ID로 삭제
 * - count(): 전체 갯수 조회
 *
 * 쿼리 메서드(Query Method):
 * 메서드 이름 규칙을 지키면 Spring Data JPA가 자동으로 SQL을 생성해줍니다.
 */
public interface MenuRepository extends JpaRepository<MenuDTO, Long> {

    // 카테고리에 키워드가 포함된 메뉴 목록 조회
    List<MenuDTO> findByCategoryContaining(String category);

    // 이름에 키워드가 포함된 메뉴 목록 조회 (LIKE %keyword%)
    List<MenuDTO> findByNameContaining(String keyword);

    // 이름 포함 검색 + 판매 여부 필터링
    // 예: findByNameContainingAndAvailable("버거", true)
    // -> name like '%버거%' and available = true 조건으로 조회합니다.
    List<MenuDTO> findByNameContainingAndAvailable(String keyword,boolean available);

}
