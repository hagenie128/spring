package com.spring.problem03;

import org.springframework.stereotype.Repository;

public class CartRepository {

    public void addItem(String item, int qty) {
        System.out.println("[CartRepository] 저장: " + item + " x" + qty);
    }
}
