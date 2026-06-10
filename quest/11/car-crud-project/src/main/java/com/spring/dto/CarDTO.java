package com.spring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("car")
public class CarDTO {
    private Integer carId;
    private String brand;
    @NotBlank(message = "모델명을 반드시 입력하세요")
    private String model;
    private Integer year;
    private Integer mileage;
    @NotNull(message = "가격을 반드시 입력하세요")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    private Integer price;
    private LocalDateTime registeredAt;
}
