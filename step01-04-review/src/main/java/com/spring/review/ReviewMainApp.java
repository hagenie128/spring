package com.spring.review;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReviewMainApp {

	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("applicationContext-review.xml")) {

			System.out.println("=== Step01~04 총정리 예제 ===");

			// 생성자 주입으로 조립된 서비스
			OrderService orderService = context.getBean(OrderService.class);
			orderService.order("아메리카노", 2, 8000);

			// setter 주입으로 조립된 서비스
			MenuService menuService = context.getBean(MenuService.class);
			menuService.printMenus();

			// component-scan과 @Autowired/@Qualifier로 조립된 서비스
			NotificationService notificationService = context.getBean(NotificationService.class);
			notificationService.sendNotification("픽업 준비가 완료되었습니다.");

			// singleton bean은 여러 번 꺼내도 같은 객체다.
			MenuService menuService2 = context.getBean(MenuService.class);
			System.out.println("menuService == menuService2 : " + (menuService == menuService2));

			// prototype bean은 getBean()을 호출할 때마다 새 객체다.
			RequestTracker tracker1 = context.getBean(RequestTracker.class);
			RequestTracker tracker2 = context.getBean(RequestTracker.class);
			System.out.println("tracker1 id : " + tracker1.getRequestId());
			System.out.println("tracker2 id : " + tracker2.getRequestId());
			System.out.println("tracker1 == tracker2 : " + (tracker1 == tracker2));
		}
	}
}
