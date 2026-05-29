package com.spring.constructor;

public class FixedDiscountPolicy implements DiscountPolicy{
	
	private final int discountAmount;
	
	public FixedDiscountPolicy(int discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	@Override
	public int discount(int amount) {
		int result = Math.min(amount, discountAmount);
		System.out.println("[FixedDiscountPolicy] 할인 금액 : "+result);
		return result;
	}

}
