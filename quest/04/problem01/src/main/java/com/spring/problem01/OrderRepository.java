package com.spring.problem01;

public class OrderRepository {
    public void save(String menuName) {
        System.out.println("[OrderRepository] 주문 저장: " + menuName);
    }
}
