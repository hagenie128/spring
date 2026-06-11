package com.spring.repository;

import com.spring.dto.CarDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 차량 엔티티의 DB 접근을 담당하는 Repository입니다.
 * JpaRepository를 상속하면 기본 CRUD 메서드가 자동 제공됩니다.
 */
public interface CarRepository extends JpaRepository<CarDTO, Integer> {
}
