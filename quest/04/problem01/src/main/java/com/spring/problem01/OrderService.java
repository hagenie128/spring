package com.spring.problem01;

public class OrderService {
    private final OrderRepository orderRepository;
    private final PointPolicy pointPolicy;

    public OrderService(OrderRepository orderRepository, PointPolicy pointPolicy) {
        this.orderRepository = orderRepository;
        this.pointPolicy = pointPolicy;
    }

    public void order(String menuName, int amount) {
        orderRepository.save(menuName);
        pointPolicy.calculate(amount);
        System.out.println("[OrderService] 주문 금액: " + amount + "원");
    }
}
