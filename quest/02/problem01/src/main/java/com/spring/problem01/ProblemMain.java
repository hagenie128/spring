package com.spring.problem01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        // applicationContext.xml 완성 후 실행하면 정상 출력된다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        RestaurantBean restaurant = context.getBean("restaurantBean", RestaurantBean.class);
        restaurant.printInfo();

        ((ClassPathXmlApplicationContext) context).close();
    }
}
