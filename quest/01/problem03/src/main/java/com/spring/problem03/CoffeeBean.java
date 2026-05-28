package com.spring.problem03;

public class CoffeeBean {

    private String name;
    private int price;
    private String origin;

    public void introduce() {
        System.out.println("[CoffeeBean] 이름: " + name
                + " | 가격: " + price + "원"
                + " | 원산지: " + origin);
    }

    public String getName()   { return name; }
    public void setName(String name)     { this.name = name; }

    public int getPrice()     { return price; }
    public void setPrice(int price)      { this.price = price; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
}
