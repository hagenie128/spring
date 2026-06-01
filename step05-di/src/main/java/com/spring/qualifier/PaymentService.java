package com.spring.qualifier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 결제 처리를 담당하는 서비스 클래스
 * 
 * @Service: 이 클래스가 비즈니스 로직을 수행하는 서비스 객체임을 나타냅니다.
 */
@Service
public class PaymentService {
	// 의존성 주입(DI): 서비스에서 사용할 결제 게이트웨이 객체
	private final PaymentGateway paymentGateway;

	/**
	 * 생성자 주입 방식
	 * 
	 * @Qualifier("beanName"): 동일한 인터페이스(PaymentGateway)를 구현한 클래스가 여러 개일 때,
	 * 어떤 구현체를 주입할지 이름을 지정하여 명시합니다.
	 * 
	 * 구버전에서는 @Autowired 지정해야 역주입 가능
	 */
	public PaymentService(@Qualifier("kakaoPaymentGateway") PaymentGateway paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	
	public void processPayment(int amount) {
		// 주입된 게이트웨이를 사용하여 결제 수행
		System.out.println(paymentGateway.pay(amount));
	}
}