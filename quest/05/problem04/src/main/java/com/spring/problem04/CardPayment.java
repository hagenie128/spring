package com.spring.problem04;

import org.springframework.stereotype.Component;

// TODO: @Component 어노테이션을 추가하라.
@Component
public class CardPayment implements PaymentProcessor {

    @Override
    public String process(int amount) {
        System.out.println("[CardPayment] 카드 결제: " + amount + "원");
        return "카드결제완료";
    }
}
