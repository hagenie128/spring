package com.spring.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

	@Bean
	public ReportService reportService() {
		return new ReportService("월간");
	}
	
	@Bean
	@Scope("prototype")
	public NotificationComponent notificationComponent() {
		return new NotificationComponent();
	}
	
}
