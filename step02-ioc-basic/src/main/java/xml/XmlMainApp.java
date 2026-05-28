package xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlMainApp {

    public static void main(String[] args) {
        // ClassPathXmlApplicationContext는 classpath(src/main/resources)에 있는
        // XML 설정 파일을 읽어서 Spring 컨테이너를 만드는 클래스입니다.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext-xml.xml");

        System.out.println("=== XML 방식 Bean 등록 실습 ===");

        // id가 "book1"인 Bean을 BookBean 타입으로 꺼냅니다.
        // book1은 XML에서 <property>로 값을 넣은 setter 주입 예제입니다.
        BookBean book1 = context.getBean("book1",BookBean.class);
        System.out.println("[Book1 - setter 주입]");
        System.out.println(book1);

        // id가 "book2"인 Bean을 꺼냅니다.
        // book2는 XML에서 <constructor-arg>로 값을 넣은 생성자 주입 예제입니다.
        BookBean book2 = context.getBean("book2",BookBean.class);
        System.out.println("[Book2 - 생성자 주입]");
        System.out.println(book2);

        // LibraryBean은 BookBean 참조, List, Map을 모두 주입받는 예제입니다.
        LibraryBean libraryBean = context.getBean("libraryBean",LibraryBean.class);
        libraryBean.printInfo();

        // 컨테이너를 닫으면 Spring이 관리하던 Bean 정리 작업을 수행할 수 있습니다.
        ((ClassPathXmlApplicationContext) context).close();

    }

}