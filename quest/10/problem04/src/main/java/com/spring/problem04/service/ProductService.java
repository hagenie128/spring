package com.spring.problem04.service;

import com.spring.problem04.entity.Product;
import com.spring.problem04.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO 7: @Service 선언
@Service
public class ProductService {

    // TODO 8: ProductRepository 필드 선언 + 생성자 주입
    @Autowired
    private ProductRepository productRepository;

    // TODO 9: findAll() — 전체 목록 반환
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // TODO 10: findById(Long id) — Optional<Product> 반환
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // TODO 11: save(Product product) — 저장 후 반환
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // TODO 12: update(Long id, Product form) — name, price, description 수정 후 저장·반환
    //          findById로 찾고, 없으면 IllegalArgumentException 발생
    public Product update(Long id, Product form) throws IllegalArgumentException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다. id=" + id));

        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setDescription(form.getDescription());

        return productRepository.save(product);
    }

    // TODO 13: delete(Long id) — 삭제
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
