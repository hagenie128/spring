package com.spring.problem02;

public class ProductBean {

    private String name;
    private int price;

    public void printInfo() {
        System.out.println("[ProductBean] 상품명: " + name + ", 가격: " + price + "원");
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}
