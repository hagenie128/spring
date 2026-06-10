package com.spring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Menu 정보를 전달하기 위한 데이터 전송 객체(DTO)입니다.
 * @Data: Getter, Setter, toString, equals, hashCode 자동 생성
 * @NoArgsConstructor: 파라미터가 없는 기본 생성자 생성
 * @AllArgsConstructor: 모든 필드를 파라미터로 받는 생성자 생성
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class MenuDTO {
    
    // 고유 식별 번호 (아이디)
    private Long id;
    
    // 메뉴 이름 (공백 허용 안함)
    @NotBlank(message = "메뉴명은 필수 입력값입니다.")
    private String name;
    
    // 메뉴 가격 (null 허용 안함, 최소 0원 이상)
    @NotNull(message = "가격은 필수 입력값입니다.")
    @Min(value = 0, message = "가격은 0원 이상 입력값입니다.")
    private Integer price;
    
    // 메뉴 카테고리 (커피, 음료 등)
    private String category;
    
    // 메뉴 설명
    private String description;
    
    // 판매 가능 여부 (기본값: true)
    private boolean available = true;
}
