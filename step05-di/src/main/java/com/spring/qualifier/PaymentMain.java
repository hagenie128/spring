package com.spring.qualifier;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PaymentMain {

	public static void main(String[] args) {
		System.out.println("=== @Qualifier / @primary ===");
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(AppConfig.class);
		
		PaymentService paymentService = 
				context.getBean(PaymentService.class);
		paymentService.processPayment(30000);
		
		context.close();
		
		
	}

}
