package com.spring.problem03.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuForm {

    @NotBlank(message = "메뉴명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    private String category;

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
