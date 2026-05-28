package com.spring.problem02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        // applicationContext.xml에 Bean을 등록하면 아래 코드가 정상 동작합니다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        ProductBean product = context.getBean("productBean", ProductBean.class);
        product.printInfo();

        CartBean cart = context.getBean("cartBean", CartBean.class);
        cart.ready();

        ((ClassPathXmlApplicationContext) context).close();
    }
}
