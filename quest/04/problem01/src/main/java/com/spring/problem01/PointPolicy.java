package com.spring.problem01;

public class PointPolicy {
    private final int rate;

    public PointPolicy(int rate) {
        this.rate = rate;
    }

    public int calculate(int amount) {
        int point = amount * rate / 100;
        System.out.println("[PointPolicy] 적립 포인트: " + point + "점");
        return point;
    }
}
