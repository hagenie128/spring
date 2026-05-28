package com.spring.problem03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {

        // TODO [문제 3-1] ClassPathXmlApplicationContext를 이용해 컨테이너를 생성하세요.
        //   설정 파일명: "applicationContext.xml"
        ApplicationContext context = 
        		new ClassPathXmlApplicationContext("applicationContext.xml"); // 이 줄을 수정하세요


        // TODO [문제 3-2] context에서 id가 "coffeeBean"인 CoffeeBean을 가져오세요.
        CoffeeBean coffee = context.getBean("coffeeBean",CoffeeBean.class); // 이 줄을 수정하세요


        // TODO [문제 3-3] coffee.introduce() 메서드를 호출하세요.
        coffee.introduce();


        // TODO [문제 3-4] 컨테이너를 종료하세요.
        //   힌트: context를 ClassPathXmlApplicationContext로 캐스팅한 뒤 .close() 호출
        ((ClassPathXmlApplicationContext)context).close();
    }
}
