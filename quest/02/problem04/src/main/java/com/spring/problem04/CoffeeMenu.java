package com.spring.problem04;

// TODO [도전 4-2] 이 클래스를 일반 컴포넌트 Bean으로 등록하는 어노테이션을 추가하세요.
public class CoffeeMenu {

    public void printMenu(CoffeeItem... items) {
        System.out.println("[CoffeeMenu] === 오늘의 메뉴 ===");
        for (CoffeeItem item : items) {
            System.out.println("  " + item);
        }
    }
}
