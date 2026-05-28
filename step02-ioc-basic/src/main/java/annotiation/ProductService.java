package annotiation;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

	public void processOrder(String productName) {
		System.out.println("[ProductService] 주문처리 시작 : "+productName);
	}
}
