package com.spring.constructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorMainApp {

	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext-constructor.xml");
		
		KioskOrderService kioskOrderService = context.getBean(KioskOrderService.class);
		
		System.out.println("---생성자 주입---");
		
		kioskOrderService.order("아메리카노", 5, 25000);
		
		((ClassPathXmlApplicationContext)context).close();
		
	}

}
