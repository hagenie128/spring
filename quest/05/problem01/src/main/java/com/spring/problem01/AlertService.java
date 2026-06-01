package com.spring.problem01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    @Autowired
    private NotificationSender notificationSender;

    public void notify(String msg) {
        notificationSender.send(msg);
        System.out.println("[AlertService] 알림 전송 완료");
    }
}
