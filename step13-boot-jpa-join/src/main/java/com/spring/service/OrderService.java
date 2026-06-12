package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Order;
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
	
	
	
	
	
}






