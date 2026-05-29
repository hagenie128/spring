package com.spring.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScopeMainApp {

	public static void main(String[] args) {
		try(AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppConfig.class)){
			
			PrototypeService prototypeService1 = 
					ctx.getBean("prototypeService",PrototypeService.class);
			PrototypeService prototypeService2 = 
					ctx.getBean("prototypeService",PrototypeService.class);
			
			SingletonService singletonService1 = ctx.getBean(SingletonService.class);
			SingletonService singletonService2 = ctx.getBean(SingletonService.class);
		}
		
	
	}

}
