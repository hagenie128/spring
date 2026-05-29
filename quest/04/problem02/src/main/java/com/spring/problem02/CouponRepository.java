package com.spring.problem02;

public class CouponRepository {
    public void save(String couponCode) {
        System.out.println("[CouponRepository] 쿠폰 저장: " + couponCode);
    }
}
