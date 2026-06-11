package com.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.repository.OrderRepository;
import com.spring.repository.MenuItemRepository;
import com.spring.repository.MemberRepository;


@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final MemberRepository memberRepository;

    public OrderService(OrderRepository orderRepository, MenuItemRepository menuItemRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.memberRepository = memberRepository;
    }
}
