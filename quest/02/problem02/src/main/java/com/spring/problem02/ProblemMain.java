package com.spring.problem02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        // 어노테이션 + component-scan 설정이 완성되면 정상 동작한다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        PaymentRepository repo = context.getBean("paymentRepository", PaymentRepository.class);
        repo.save(1001);

        PaymentService service = context.getBean("paymentService", PaymentService.class);
        service.processPayment(50000);

        NotificationSender sender = context.getBean("notificationSender", NotificationSender.class);
        sender.send("결제가 완료되었습니다.");

        ((ClassPathXmlApplicationContext) context).close();
    }
}
