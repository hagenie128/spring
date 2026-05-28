package com.spring.problem02;

// TODO [문제 2-3] 이 클래스를 일반 컴포넌트 Bean으로 등록하는 어노테이션을 추가하세요.
//  힌트: @Component
public class NotificationSender {

    public void send(String message) {
        System.out.println("[NotificationSender] 알림 전송: " + message);
    }
}
