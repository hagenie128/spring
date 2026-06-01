package com.spring.problem03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// TODO 1: @Configuration 어노테이션을 추가하라.
@Configuration
@ComponentScan("com.spring.problem03")
public class AppConfig {

    // TODO 2: @Bean 어노테이션을 추가하고 CartRepository 객체를 반환하는 메서드를 작성하라.
    //   메서드 이름: cartRepository
    //   반환 타입: CartRepository
	@Bean
	public CartRepository cartRepository() {
		return new CartRepository();
	}

    // TODO 3: @Bean 어노테이션을 추가하고 DiscountCalculator 객체를 반환하는 메서드를 작성하라.
    //   메서드 이름: discountCalculator
    //   반환 타입: DiscountCalculator
	@Bean
	public DiscountCalculator discountCalculator() {
		return new DiscountCalculator();
	}

    // TODO 4: @Bean 어노테이션을 추가하고 CartService 객체를 반환하는 메서드를 작성하라.
    //   메서드 이름: cartService
    //   반환 타입: CartService
    //   cartRepository()와 discountCalculator()를 인자로 전달하라.
//	public CartService cartService() {
//		return new CartService(cartRepository(), discountCalculator());
//	}
}
