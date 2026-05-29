package com.spring.problem04;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        ConnectionPool pool = context.getBean("connectionPool", ConnectionPool.class);

        String c1 = pool.getConnection();
        String c2 = pool.getConnection();
        String c3 = pool.getConnection();
        String c4 = pool.getConnection();
        String c5 = pool.getConnection();
        String c6 = pool.getConnection();

        pool.releaseConnection(c1);
        c1 = pool.getConnection();

        System.out.println("\n▶ 컨테이너 종료");
        context.close();
    }
}
