package com.spring.problem04;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        // TODO: AnnotationConfigApplicationContext를 생성하라. (PaymentAppConfig.class 사용)
    	AnnotationConfigApplicationContext context = 
    			new AnnotationConfigApplicationContext(PaymentAppConfig.class);
        // TODO: getBean("checkoutServiceWithCard", CheckoutService.class)로 Bean을 가져와 checkout(25000)을 호출하라.
    	CheckoutService card = context.getBean("checkoutServiceWithCard",CheckoutService.class);
    	card.checkout(3000);
        // TODO: getBean("checkoutServiceWithCash", CheckoutService.class)로 Bean을 가져와 checkout(10000)을 호출하라.
    	CheckoutService cash = context.getBean("checkoutServiceWithCash",CheckoutService.class);
    	cash.checkout(3000);
        // TODO: 컨텍스트를 close하라.
    	context.close();
    }
}