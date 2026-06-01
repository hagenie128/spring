package com.spring.field;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FieldMainApp {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
			NotificationService notificationService = context.getBean(NotificationService.class);
			System.out.println("===필드 주입 테스트===");
			notificationService.notifyOrder("주문이 접수되었습니다.");
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
