package com.spring;

import com.spring.entity.*;
import com.spring.repository.MemberRepository;
import com.spring.repository.MenuItemRepository;
import com.spring.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;

    public DataInitializer(MemberRepository memberRepository,
                           MenuItemRepository menuItemRepository,
                           OrderRepository orderRepository) {
        this.memberRepository = memberRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (memberRepository.count() > 0) return;

        // 회원 3명
        Member m1 = new Member("홍길동", "hong@cafe.com", "010-1111-2222");
        Member m2 = new Member("김영희", "kim@cafe.com", "010-3333-4444");
        Member m3 = new Member("이철수", "lee@cafe.com", "010-5555-6666");
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        // 메뉴 5개
        MenuItem americano  = new MenuItem("아메리카노",  3500, "커피");
        MenuItem latte      = new MenuItem("카페라떼",   4500, "커피");
        MenuItem cheesecake = new MenuItem("치즈케이크", 6500, "케이크");
        MenuItem sandwich   = new MenuItem("에그샌드위치", 5500, "푸드");
        MenuItem smoothie   = new MenuItem("망고스무디",  5000, "음료");
        menuItemRepository.save(americano);
        menuItemRepository.save(latte);
        menuItemRepository.save(cheesecake);
        menuItemRepository.save(sandwich);
        menuItemRepository.save(smoothie);

        // 주문 3건
        Order order1 = new Order(m1);
        order1.addOrderItem(new OrderItem(americano, 2, americano.getPrice()));
        order1.addOrderItem(new OrderItem(cheesecake, 1, cheesecake.getPrice()));
        order1.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order1);

        Order order2 = new Order(m2);
        order2.addOrderItem(new OrderItem(latte, 1, latte.getPrice()));
        order2.addOrderItem(new OrderItem(sandwich, 2, sandwich.getPrice()));
        order2.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order2);

        Order order3 = new Order(m1);
        order3.addOrderItem(new OrderItem(smoothie, 2, smoothie.getPrice()));
        order3.addOrderItem(new OrderItem(americano, 1, americano.getPrice()));
        orderRepository.save(order3);
    }
}
