package com.spring.constructor;

public class KioskOrderService {
	private final OrderRepository orderRepository;
	private final DiscountPolicy discountPolicy;
	
	public KioskOrderService(OrderRepository orderRepository, DiscountPolicy discountPolicy) {
		this.orderRepository = orderRepository;
		this.discountPolicy = discountPolicy;
	}
	
	public void order(String menuName, int quantity, int amount) {
		orderRepository.save(menuName+" X "+quantity);
		int discountAmount = discountPolicy.discount(amount);
		System.out.println("[KioskOrderService] 결제 예정 금액 : "+(amount-discountAmount));
	}
}
