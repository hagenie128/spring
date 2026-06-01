package com.spring.value;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ValueMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(AppConfig.class);
		
		StoreProperties storeProperties = context.getBean(StoreProperties.class);
		
		storeProperties.printInfo();
		
		context.close();
	}

}