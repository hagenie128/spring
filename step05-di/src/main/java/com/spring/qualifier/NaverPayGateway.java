package com.spring.qualifier;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("naverPayGateway")
@Primary
public class NaverPayGateway implements PaymentGateway {
	
	@Override
	public String pay(int amount) {
		return "네이버페이로 " + amount + "원 결제 완료";
	}

}
