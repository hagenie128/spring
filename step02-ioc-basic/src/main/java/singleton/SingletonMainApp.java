package singleton;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SingletonMainApp {

	public static void main(String[] args) {
        // singleton/prototype 스코프가 설정된 XML 파일로 Spring 컨테이너를 생성합니다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext-singleton.xml");

        System.out.println("=== Singleton vs Prototype 비교 ===");
        System.out.println("=== Singletone ===");
        
        CounterBean c1 =context.getBean(CounterBean.class);
        CounterBean c2 =context.getBean(CounterBean.class);
        
        // identityHashCode는 객체의 실제 식별값을 확인할 때 사용합니다.
        // 두 값이 같으면 같은 인스턴스를 보고 있다고 이해할 수 있습니다.
        System.out.println("c1 identityHashCode : " + System.identityHashCode(c1) );
        System.out.println("c2 identityHashCode : " + System.identityHashCode(c2) );
        System.out.println("c1 == c2 : " + (c1 == c2));
        
        // c1과 c2가 같은 객체이므로, c1에서 증가시킨 count를 c2에서도 확인할 수 있습니다.
        c1.increment();
        c1.increment();
        c1.increment();
        c2.increment();
        System.out.println(c1.getCount() + " " + c2.getCount());
        
        System.out.println("=== Prototype ===");
        
        // temporaryBean은 prototype이므로 getBean()을 호출할 때마다 새 객체가 생성됩니다.
        TemporaryBean b1 = context.getBean(TemporaryBean.class);
        TemporaryBean b2 = context.getBean(TemporaryBean.class);

        // 서로 다른 객체이므로 identityHashCode와 == 비교 결과가 다르게 나옵니다.
        System.out.println("b1 identityHashCode : " + System.identityHashCode(b1) );
        System.out.println("b2 identityHashCode : " + System.identityHashCode(b2) );
        System.out.println("b1 == b2 : " + (b1 == b2));

        // 실습이 끝났으므로 컨테이너를 닫습니다.
        ((ClassPathXmlApplicationContext) context).close();
	}

}
