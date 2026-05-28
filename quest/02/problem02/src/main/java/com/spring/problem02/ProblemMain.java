package com.spring.problem02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        // 어노테이션 + component-scan 설정이 완성되면 정상 동작한다.
        // XML 파일의 component-scan이 아래 어노테이션 Bean들을 자동 등록합니다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // @Repository가 붙은 PaymentRepository Bean을 이름으로 조회합니다.
        PaymentRepository repo = context.getBean("paymentRepository", PaymentRepository.class);
        repo.save(1001);

        // @Service가 붙은 PaymentService Bean을 이름으로 조회합니다.
        PaymentService service = context.getBean("paymentService", PaymentService.class);
        service.processPayment(50000);

        // @Component가 붙은 NotificationSender Bean을 두 번 조회합니다.
        // 기본 스코프는 singleton이므로 sender1과 sender2는 같은 객체입니다.
        NotificationSender sender1 = context.getBean("notificationSender", NotificationSender.class);
        NotificationSender sender2 = context.getBean("notificationSender", NotificationSender.class);
        sender1.send("결제가 완료되었습니다.");
        
        // true가 출력되면 두 변수가 같은 Bean 인스턴스를 바라본다는 뜻입니다.
        System.out.println(sender1 == sender2);

        // 컨테이너 종료: Bean 정리 작업을 수행할 수 있도록 close()를 호출합니다.
        ((ClassPathXmlApplicationContext) context).close();
    }
}