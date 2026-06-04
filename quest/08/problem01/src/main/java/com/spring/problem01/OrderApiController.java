package com.spring.problem01;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO 1: @RestController 어노테이션을 추가하라.
// TODO 2: @RequestMapping("/api/orders") 를 추가하라.
@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    private final List<OrderDto> orders = new ArrayList<>(List.of(
        new OrderDto(1L, "아메리카노", 2, 8000),
        new OrderDto(2L, "치즈케이크", 1, 5500)
    ));

    // TODO 3: GET /api/orders 요청 시 orders 목록을 JSON으로 반환하는 메서드를 작성하라.
    //   - @GetMapping 추가
    //   - 반환 타입: List<OrderDto>
    @GetMapping
    public List<OrderDto> list(){
    	return orders;
    }

    // TODO 4: GET /api/orders/{id} 요청 시 해당 id의 OrderDto를 JSON으로 반환하는 메서드를 작성하라.
    //   - @GetMapping("/{id}") 추가
    //   - @PathVariable Long id 파라미터
    //   - orders 리스트에서 id가 일치하는 항목 반환 (없으면 null)
    @GetMapping("/{id}")
    public OrderDto item(@PathVariable long id) {
		return orders.stream().filter(item->
		item.getOrderId()==id).findFirst().orElse(
		new OrderDto(-1L, "주문내역 없음", 0, 0));
    }
}
