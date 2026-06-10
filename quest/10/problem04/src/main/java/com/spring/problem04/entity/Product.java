package com.spring.problem04.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Product {

    private Long id;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    private String description;

    // ─── 생성자 ────────────────────────────────────────────
    public Product() {}

    public Product(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // ─── Getters / Setters ─────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
