package com.spring.problem04;

import org.springframework.stereotype.Component;

// TODO [도전 4-2] 이 클래스를 일반 컴포넌트 Bean으로 등록하는 어노테이션을 추가하세요.
// @Component를 붙이면 component-scan이 이 클래스를 찾아 coffeeMenu Bean으로 등록합니다.
@Component
public class CoffeeMenu {

    public void printMenu(CoffeeItem... items) {
        // 가변 인자(CoffeeItem... items)는 CoffeeItem을 여러 개 받을 수 있게 해 줍니다.
        System.out.println("[CoffeeMenu] === 오늘의 메뉴 ===");
        for (CoffeeItem item : items) {
            System.out.println("  " + item);
        }
    }
}