package com.spring.context;

/**
 * Java Config(@Configuration)에서 @Bean으로 등록하는 일반 POJO.
 */
public class NotificationComponent {

    public NotificationComponent() {
        System.out.println("[NotificationComponent] 생성");
    }

    public void notify(String message) {
        System.out.println("[NotificationComponent] 알림: " + message);
    }
}
