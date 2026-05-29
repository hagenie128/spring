package com.spring.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigMainApp {

	public static void main(String[] args) {
		
		try(AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppConfig.class)){
			
			ReportService reportService1 = ctx.getBean(ReportService.class);
			ReportService reportService2 = ctx.getBean("reportService",ReportService.class);
			
			System.out.println(reportService1 == reportService2);
			
			NotificationComponent notificationComponent1 = ctx.getBean(NotificationComponent.class); 
			NotificationComponent notificationComponent2 = ctx.getBean(NotificationComponent.class); 
			
			System.out.println(notificationComponent1==notificationComponent2);
		}
	}

}
