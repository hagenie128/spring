package com.spring.review;

public class OrderService {
	private final OrderRepository orderRepository;
	private final DiscountPolicy discountPolicy;
	private final NotificationService notificationService;

	public OrderService(
			OrderRepository orderRepository,
			DiscountPolicy discountPolicy,
			NotificationService notificationService) {
		this.orderRepository = orderRepository;
		this.discountPolicy = discountPolicy;
		this.notificationService = notificationService;
	}

	public void order(String menuName, int quantity, int amount) {
		orderRepository.save(menuName + " x " + quantity);
		int discountAmount = discountPolicy.discount(amount);
		System.out.println("[OrderService] 결제 금액: " + (amount - discountAmount));
		notificationService.sendNotification("주문이 접수되었습니다: " + menuName);
	}
}
