package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.MenuItem;
import com.spring.entity.Order;
import com.spring.entity.OrderItem;
import com.spring.entity.OrderStatus;
import com.spring.repository.MemberRepository;
import com.spring.repository.MenuItemRepository;
import com.spring.repository.OrderRepository;

// 주문 관련 비즈니스 로직을 담당할 서비스 계층입니다.
@Service
@Transactional(readOnly =  true)
public class OrderService {
	
	// 주문 생성/조회 시 주문, 회원, 메뉴 정보를 함께 사용하므로 세 Repository를 주입받습니다.
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final MenuItemRepository menuItemRepository;

	public OrderService(OrderRepository orderRepository, MemberRepository memberRepository,
			MenuItemRepository menuItemRepository) {
		this.orderRepository = orderRepository;
		this.memberRepository = memberRepository;
		this.menuItemRepository = menuItemRepository;
	}

    public List<Order> search(Long memberId, OrderStatus status) {
        return orderRepository.search(memberId, status);
    }

    public Order findByIdWithDetails(Long id) {
        return orderRepository.findByIdWithDetails(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public void update(Order order) {
        Order raw = findByIdWithDetails(order.getId());
        raw.setStatus(order.getStatus());
    }

    @Transactional
    public Order save(Long memberId, List<Long> menuItemIds, List<Integer> quantities) {
        
        Order order = new Order();
        //주문한 회원 정보 등록
        order.setMember(memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("없는 회원 입니다")));
        // 주문 내용 등록
        for(int i=0; i< menuItemIds.size();i++){
            //주문 수량이 0이면 건너뜀
            if(quantities.get(i)==0) continue;
            //주문 메뉴 아이템 조회
            MenuItem item = menuItemRepository.findById(menuItemIds.get(i)).orElseThrow(()-> new IllegalArgumentException("없는 메뉴 입니다"));
            order.addOrderItem(new OrderItem(item, quantities.get(i), item.getPrice()));
        }
        if(order.getOrderItems().isEmpty()){
            throw new IllegalArgumentException("메뉴를 한개 이상 골라주세요");
        }
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
	
	
	
	
	
}






