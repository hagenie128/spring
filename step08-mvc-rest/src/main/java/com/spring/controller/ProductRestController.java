package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.ProductDTO;

//@RestController = @Controller+@RespoceBody
//매서드 리턴 값이 Jackson 통해서 JSON으로 변환
@RestController
@RequestMapping("/api/products")
public class ProductRestController {
	private final List<ProductDTO> products = new ArrayList<>(List.of(
	        new ProductDTO(1L, "아메리카노", 4000, "음료"),
	        new ProductDTO(2L, "카페라떼", 4500, "음료"),
	        new ProductDTO(3L, "치즈케이크", 5500, "디저트")
	    ));
	
	@GetMapping
	public List<ProductDTO> list(){
		return products;	
	}
	
	//GET api/products/{id} -> 특정상품 json을 반환
	@GetMapping("/{id}")
	public ProductDTO detail(@PathVariable long id) {

		return products.stream().filter(item->
				item.getId()==id).findFirst().orElse(
				new ProductDTO(-1, "상품정보 없음", 0, null));
	}
	
	//상품 등록 후 JSON으로 변환
	//@RequestBody : 요청 본문의 JSON을 ProductDTO로 반환
	@PostMapping
	public ProductDTO create(@RequestBody ProductDTO dto) {
		dto.setId(products.getLast().getId()+1);
		products.add(dto);
		return dto;
	}
	
}
