package com.spring.problem02.dto;

public class MenuDto {

    private String name;
    private int price;
    private boolean available;
    private String grade; // "BEST", "NEW", "NORMAL"

    public MenuDto(String name, int price, boolean available, String grade) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.grade = grade;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public String getGrade() { return grade; }
}
