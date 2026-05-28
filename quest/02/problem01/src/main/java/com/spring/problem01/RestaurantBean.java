package com.spring.problem01;

public class RestaurantBean {

    private String   name;
    private MenuBean menu;

    public void printInfo() {
        System.out.println("[RestaurantBean] 식당명: " + name);
        menu.printInfo();
    }

    public String getName()           { return name; }
    public void   setName(String n)   { this.name = n; }

    public MenuBean getMenu()         { return menu; }
    public void     setMenu(MenuBean m){ this.menu = m; }
}
