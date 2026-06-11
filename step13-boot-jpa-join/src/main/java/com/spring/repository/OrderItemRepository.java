package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	// 메뉴별 판매 수량 집계 - 메뉴명, 판매된 메뉴 개수 합
	@Query("select oi.menuItem.name, sum(oi.quantity)"
			+ " from OrderItem oi"
			+ " group by oi.menuItem.name"
			+ " order by sum(oi.quantity) desc")	
	List<Object[]> findMenuSalesSurmmary();

}






