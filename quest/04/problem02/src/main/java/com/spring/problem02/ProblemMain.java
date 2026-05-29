package com.spring.problem02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CouponService couponService = context.getBean("couponService", CouponService.class);
        couponService.issueWelcomeCoupon();
    }
}
