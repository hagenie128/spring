package com.spring.problem02.dto;

// [완성된 파일 — 수정 불필요]
public class MenuItem {

    private Long id;
    private String name;
    private String category;
    private int price;

    public MenuItem(Long id, String name, String category, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
}
