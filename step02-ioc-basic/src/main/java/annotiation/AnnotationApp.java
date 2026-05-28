package annotiation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-anno.xml");

		System.out.println("=== Annotation 방식 Bean 등록 실습 ===");

		// id 속성값으로 읽을 때에는 클래스명에서 첫글자를 소문자로 지정
		// ProductRepository -> productRepository 라는 기본 Bean 이름이 만들어집니다.
		ProductRepository pr = 
				context.getBean("productRepository",ProductRepository.class);

		System.out.println("[ProductRepository] 상품 전체 조회 : "
		+pr.findAll());
		
		ProductService service = context.getBean(ProductService.class);
		
		service.processOrder("스마트폰");
		
		EmailSender email = context.getBean(EmailSender.class);
		
		email.send("support@spring.com", "주문이 완료 되었습니다");
		
		((ClassPathXmlApplicationContext)context).close();
		
	}
}
