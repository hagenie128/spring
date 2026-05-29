package com.spring.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterMain {

	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext-setter.xml");
		
		MenuService menuService = context.getBean(MenuService.class);
		
		System.out.println("---setter 주입 실습---");
		menuService.printMenus();
		
		
	}

}
