package com.spring.problem04;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * [도전 4-3] ApplicationContext로 Student Bean 두 개를 꺼내
 * 각각 introduce()를 호출하세요.
 *
 * 기대 출력:
 *   안녕하세요! 저는 홍길동이고, 25살이며 컴퓨터공학을 공부하고 있습니다.
 *   안녕하세요! 저는 김춘향이고, 23살이며 소프트웨어공학을 공부하고 있습니다.
 */
public class ProblemMain {

    public static void main(String[] args) {

        // TODO: ApplicationContext(컨테이너)를 생성하세요.
    	ApplicationContext context = 
    			new ClassPathXmlApplicationContext("applicationContext.xml");

        // TODO: "student1" Bean을 꺼내 introduce()를 호출하세요.
    	Student student1 = context.getBean("student1",Student.class);
    	student1.introduce();

        // TODO: "student2" Bean을 꺼내 introduce()를 호출하세요.
    	Student student2 = context.getBean("student2",Student.class);
    	student2.introduce();


        // TODO: 컨테이너를 종료하세요.
    	((ClassPathXmlApplicationContext)context).close();
    }
}
