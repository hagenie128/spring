package com.spring.problem03;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CartService cartService = ctx.getBean(CartService.class);
        cartService.addToCart("불고기버거", 2, 12000);

        ctx.close();
    }
}
