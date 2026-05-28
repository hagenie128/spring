package annotiation;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
	public List<String> findAll(){
		return List.of("노트북","스마트폰","태블릿");
	}
}
