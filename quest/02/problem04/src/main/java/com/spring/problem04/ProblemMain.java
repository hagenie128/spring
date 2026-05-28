package com.spring.problem04;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * [도전 4-6] 아래 순서대로 코드를 완성하세요.
 *
 *  ① ApplicationContext를 생성한다.
 *  ② "cafeService" Bean을 꺼내 printWelcome()을 호출한다.
 *  ③ "americano", "latteCoffee", "vanillaLatte" Bean을 꺼낸다.
 *  ④ "coffeeMenu" Bean을 꺼내 printMenu(아메리카노, 라떼, 바닐라라떼)를 호출한다.
 *  ⑤ 컨테이너를 종료한다.
 *
 * 기대 출력:
 *   [CafeService] ☕ Spring Cafe에 오신 것을 환영합니다!
 *   [CoffeeMenu] === 오늘의 메뉴 ===
 *     아메리카노 | 4,500원 | Large
 *     카페라떼   | 5,000원 | Medium
 *     바닐라라떼 | 5,500원 | Medium
 */
public class ProblemMain {

    public static void main(String[] args) {

        // TODO ①: ApplicationContext를 생성하세요.
        // XML 설정에는 CoffeeItem Bean 등록과 component-scan 설정이 함께 들어 있습니다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // TODO ②: cafeService Bean을 꺼내 printWelcome()을 호출하세요.
        // CafeService는 @Service로 자동 등록된 Bean입니다.
        CafeService cafeService = context.getBean(CafeService.class);
        cafeService.printWelcome();

        // TODO ③: CoffeeItem Bean 3개를 꺼내세요.
        // CoffeeItem들은 XML에서 <bean>으로 직접 등록했기 때문에 id로 조회합니다.
        CoffeeItem item1 = context.getBean("americano",CoffeeItem.class);
        CoffeeItem item2 = context.getBean("latteCoffee",CoffeeItem.class);
        CoffeeItem item3 = context.getBean("vanillaLatte",CoffeeItem.class);

        // TODO ④: coffeeMenu Bean을 꺼내 printMenu()를 호출하세요.
        // CoffeeMenu는 @Component로 자동 등록된 Bean입니다.
        CoffeeMenu coffeeMenu = context.getBean(CoffeeMenu.class);
        coffeeMenu.printMenu(item1,item2,item3);

        // TODO ⑤: 컨테이너를 종료하세요.
        // close()를 호출하면 Spring 컨테이너가 관리하던 자원을 정리합니다.
        ((ClassPathXmlApplicationContext) context).close();
    }
}