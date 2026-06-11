package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    //메뉴별 판매 수량 조회
    @Query("select oi.menuItem, sum(oi.quantity) as totalQuantity from OrderItem oi group by oi.menuItem")
    List<Object[]> findMenuSalesSummary();
}
