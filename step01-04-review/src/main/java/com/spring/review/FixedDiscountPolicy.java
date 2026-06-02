package com.spring.review;

public class FixedDiscountPolicy implements DiscountPolicy {
	private final int fixedAmount;

	public FixedDiscountPolicy(int fixedAmount) {
		this.fixedAmount = fixedAmount;
	}

	@Override
	public int discount(int amount) {
		int discountAmount = Math.min(amount, fixedAmount);
		System.out.println("[FixedDiscountPolicy] 할인 금액: " + discountAmount);
		return discountAmount;
	}
}
