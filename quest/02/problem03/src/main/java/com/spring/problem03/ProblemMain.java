package com.spring.problem03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemMain {

    public static void main(String[] args) {
        // singleton/prototype 스코프가 설정된 XML 파일을 읽어 컨테이너를 생성합니다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("=== Singleton 확인 ===");

        // TODO [문제 3-1] counterBean을 두 번 꺼내 c1, c2에 저장하세요.
        // counterBean은 singleton이므로 두 번 조회해도 같은 객체가 반환됩니다.
        CounterBean c1 = context.getBean("counterBean",CounterBean.class); 
        CounterBean c2 = context.getBean("counterBean",CounterBean.class); 

        // TODO [문제 3-2] c1.increment()를 3번 호출하세요.
        // c1과 c2가 같은 객체라면 c1에서 증가시킨 count를 c2에서도 볼 수 있습니다.
        c1.increment();
        c1.increment();
        c1.increment();

        // TODO [문제 3-3] c2.getCount()를 출력하세요. (예상 결과: 3)
        System.out.println("[Singleton] c2.getCount() = " + c2.getCount());

        // TODO [문제 3-4] c1 == c2 비교 결과를 출력하세요. (예상 결과: true)
        System.out.println("[Singleton] c1 == c2 : " + (c1 == c2));

        System.out.println("\n=== Prototype 확인 ===");

        // TODO [문제 3-5] temporaryBean을 두 번 꺼내 t1, t2에 저장하세요.
        // temporaryBean은 prototype이므로 조회할 때마다 새 객체가 생성됩니다.
        TemporaryBean t1 = context.getBean(TemporaryBean.class);
        TemporaryBean t2 = context.getBean(TemporaryBean.class);

        // TODO [문제 3-6] t1.getId()와 t2.getId()를 출력하세요. (예상 결과: 서로 다른 값)
        System.out.println("[Prototype] t1.id = " + t1.getId());
        System.out.println("[Prototype] t2.id = " + t2.getId());

        // TODO [문제 3-7] t1 == t2 비교 결과를 출력하세요. (예상 결과: false)
        // false가 나오면 t1과 t2가 서로 다른 인스턴스라는 뜻입니다.
        System.out.println("[Prototype] t1 == t2 : " + (t1 == t2));

        // 컨테이너를 닫아 예제를 마무리합니다.
        ((ClassPathXmlApplicationContext) context).close();
    }
}