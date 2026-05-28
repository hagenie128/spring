package com.spring.problem01;

import java.util.List;
import java.util.Map;

public class MenuBean {

    private String              category;
    private List<String>        items;
    private Map<String, String> prices;

    public void printInfo() {
        System.out.println("[MenuBean] 카테고리: " + category);
        System.out.println("  메뉴 목록: " + items);
        System.out.println("  가격표: " + prices);
    }

    public String getCategory()                    { return category; }
    public void   setCategory(String category)     { this.category = category; }

    public List<String> getItems()                 { return items; }
    public void         setItems(List<String> items){ this.items = items; }

    public Map<String, String> getPrices()                    { return prices; }
    public void                setPrices(Map<String, String> prices) { this.prices = prices; }
}
