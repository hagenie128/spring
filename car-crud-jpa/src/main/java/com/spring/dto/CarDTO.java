package com.spring.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 차량 정보를 담는 JPA 엔티티입니다.
 * cars 테이블과 매핑되며 화면 폼 데이터 바인딩에도 함께 사용합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class CarDTO {
    // 차량 테이블의 기본키입니다. DB의 AUTO_INCREMENT 값을 사용합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer carId;
    
    // 브랜드명은 필수 입력값이며 DB에서도 null을 허용하지 않습니다.
    @Column(nullable = false, length = 100)
    @NotBlank(message = "브랜드를 반드시 입력하세요")
    private String brand;

    // 모델명은 필수 입력값입니다.
    @Column(nullable = false)
    @NotBlank(message = "모델명을 반드시 입력하세요")
    private String model;
    
    // 연식은 null일 수 없고 1900년 이상만 허용합니다.
    @Column(nullable = false)
    @NotNull(message = "연식을 반드시 입력하세요")
    @Min(value = 1900, message = "연식은 1900년 이상이어야 합니다")
    private Integer year;
    
    // 주행거리입니다. 선택적으로 입력할 수 있게 별도 검증은 두지 않았습니다.
    @Column
    private Integer mileage;
    
    // 가격은 필수 입력값이며 0 이상만 허용합니다.
    @Column(nullable = false)
    @NotNull(message = "가격을 반드시 입력하세요")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    private Integer price;

    // 등록일은 최초 저장 시 자동 입력되고 수정 시에는 변경하지 않습니다.
    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registeredAt;

    // INSERT 직전에 실행되어 등록일을 현재 시간으로 설정합니다.
    @PrePersist
    void prePersist() {
        this.registeredAt = LocalDateTime.now();
    }
}
