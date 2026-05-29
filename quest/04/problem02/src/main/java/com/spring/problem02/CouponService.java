package com.spring.problem02;

public class CouponService {
    private CouponRepository couponRepository;
    private String couponPrefix;

    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void setCouponPrefix(String couponPrefix) {
        this.couponPrefix = couponPrefix;
    }

    public void issueWelcomeCoupon() {
        couponRepository.save(couponPrefix + "-WELCOME-1000");
        System.out.println("[CouponService] 쿠폰 발급 완료");
    }
}
