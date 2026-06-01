package com.spring.problem03;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final DiscountCalculator discountCalculator;

    public CartService(CartRepository cartRepository, DiscountCalculator discountCalculator) {
        this.cartRepository = cartRepository;
        this.discountCalculator = discountCalculator;
    }

    public void addToCart(String item, int qty, int price) {
        int discountedPrice = discountCalculator.calculate(price, 10);
        cartRepository.addItem(item, qty);
        int total = discountedPrice * qty;
        System.out.println("[CartService] 장바구니 합계: " + total + "원");
    }
}
