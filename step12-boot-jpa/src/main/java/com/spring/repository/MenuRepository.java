package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.dto.MenuDTO;

/**
 * Spring Data JPA Repository
 *
 * JpaRepository<T, ID>를 상속하면 아래 메서드가 자동으로 제공됩니다:
 *   - save(S entity)           : INSERT / UPDATE
 *   - findById(Long id)        : SELECT WHERE id = ?
 *   - findAll()                : SELECT *
 *   - deleteById(Long id)      : DELETE WHERE id = ?
 *   - count()                  : SELECT COUNT(*)
 *   - existsById(Long id)      : SELECT EXISTS WHERE id = ?
 *
 * 쿼리 메서드: 메서드 이름 규칙으로 SQL 자동 생성
 * @Query: JPQL로 복잡한 쿼리 직접 작성
 */
public interface MenuRepository extends JpaRepository<MenuDTO, Long> {

}