package com.spring.problem03;

public class DiscountCalculator {

    public int calculate(int price, int rate) {
        System.out.println("[DiscountCalculator] " + rate + "% 할인 적용");
        return price - price * rate / 100;
    }
}
