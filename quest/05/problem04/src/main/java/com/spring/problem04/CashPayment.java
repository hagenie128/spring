package com.spring.problem04;

import org.springframework.stereotype.Component;

// TODO: @Component 어노테이션을 추가하라.
@Component
public class CashPayment implements PaymentProcessor {

    @Override
    public String process(int amount) {
        System.out.println("[CashPayment] 현금 결제: " + amount + "원");
        return "현금결제완료";
    }
}
