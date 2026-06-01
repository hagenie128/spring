package com.spring.qualifier;

import org.springframework.stereotype.Component;

@Component
public class KakaoPaymentGateway implements PaymentGateway {

	@Override
	public String pay(int amount) {
		return "카카오페이로 " + amount + "원 결제 완료";
	}

}
