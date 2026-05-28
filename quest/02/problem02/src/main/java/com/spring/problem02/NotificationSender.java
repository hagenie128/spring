package com.spring.problem02;

import org.springframework.stereotype.Component;

//TODO [문제 2-3] 이 클래스를 일반 컴포넌트 Bean으로 등록하는 어노테이션을 추가하세요.
//힌트: @Component
//@Component는 특정 계층 의미 없이 "Spring이 관리하는 객체"로 등록할 때 사용합니다.
@Component
//아래 @Scope 예시는 Bean 스코프를 바꿀 때 사용할 수 있는 참고 코드입니다.
//주석을 해제하면 getBean() 할 때마다 새 NotificationSender가 만들어지는 prototype으로 바뀝니다.
//@Scope("prototype")
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NotificationSender {

	public void send(String message) {
		System.out.println("[NotificationSender] 알림 전송: " + message);
	}
}
