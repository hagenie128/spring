package com.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {
		// 1. Spring 컨테이너 생성
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println("===Spring 컨테이너 생성 완료===");
		// 2. MessageBean 꺼내기
//		MessageBean messageBean = (MessageBean) context.getBean("messageBean");
		MessageBean messageBean = 
				context.getBean("messageBean",MessageBean.class);
		messageBean.printMessage();
		
		// 3. 커스텀 꺼내기
		MessageBean customMessageBean = 
				context.getBean("customMessageBean",MessageBean.class);
		customMessageBean.printMessage();
		
		// 4. Calculator 꺼내기
//		Calculator calculator = context.getBean(Calculator.class);
		Calculator calculator = context.getBean("calculator",Calculator.class);
		
		System.out.println("[Calculator]"+calculator.add(10, 5));
		System.out.println("[Calculator]"+calculator.sub(10, 5));
		System.out.println("[Calculator]"+calculator.mul(10, 5));
		System.out.println("[Calculator]"+calculator.div(10, 5));
		
		// 5. 컨테이너 종료 - Bean을 소멸하는 콜백 호출
		((ClassPathXmlApplicationContext)context).close();
		System.out.println("===Spring 컨테이너 종료===");
		
	}

}
