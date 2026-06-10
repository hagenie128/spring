package com.spring.problem04.repository;

import com.spring.problem04.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

// TODO 1: @Repository 선언
@Repository
public class ProductRepository {

    // TODO 2: LinkedHashMap<Long, Product>으로 store 필드 선언
    //         AtomicLong으로 sequence 필드 선언 (초기값 1)
    private Map<Long, Product> store = new LinkedHashMap<>();
    private AtomicLong sequence = new AtomicLong(1);

    // TODO 3: save(Product product) — id가 null이면 sequence로 id 부여 후 store에 저장
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(sequence.getAndIncrement());
        }
        store.put(product.getId(), product);
        return product;
    }

    // TODO 4: findAll() — store의 모든 값을 List로 반환
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    // TODO 5: findById(Long id) — Optional<Product> 반환
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // TODO 6: deleteById(Long id) — store에서 제거
    public void deleteById(Long id) {
        store.remove(id);
    }
}
