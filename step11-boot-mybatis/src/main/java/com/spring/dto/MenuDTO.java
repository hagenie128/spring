package com.spring.dto;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("menu")
public class MenuDTO {
    private Long id;
    @NotBlank(message = "메뉴 이름은 필수입니다.")
    private String name;
    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;
    private String description;
    private String category;
    private boolean available = true;
    private LocalDateTime createdAt;
    
}
