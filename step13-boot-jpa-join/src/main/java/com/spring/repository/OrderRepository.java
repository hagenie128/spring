package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Order;
import com.spring.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long>{
	// JOIN FETCH: 주문 목록을 조회할 때 회원도 함께 가져와 N+1 문제를 줄입니다.
	@Query("select o from Order o join fetch o.member order by o.orderDate DESC")
	List<Order> findAllWithMember();
	
	// 다중 JOIN FETCH: 주문, 회원, 주문 항목, 메뉴를 한 번에 조회합니다.
	@Query("select distinct o from Order o "
			+ "join fetch o.member "
			+ "join fetch o.orderItems oi "
			+ "join fetch oi.menuItem "
			+ "order by o.orderDate desc")
	List<Order> findAllWithDetails();
	
    // 상세 조회: 주문 상세 화면에서 필요한 회원, 주문 항목, 메뉴를 함께 로딩합니다.
	@Query("select distinct o from Order o "
			+ "join fetch o.member "
			+ "join fetch o.orderItems oi "
			+ "join fetch oi.menuItem "
			+ "where o.id = :id")
	Optional<Order> findByIdWithDetails(@Param("id") Long id);
	
	// 상태별 조회 - 상태값이 일치하는 주문 항목만 회원과 같이 로딩, 정렬은 최근 주문일 부터
	@Query("select distinct o from Order o "
			+ "join fetch o.member "
			+ "where o.status = :status order by o.orderDate desc")
	List<Order> findByStatusWithMember(@Param("status") OrderStatus status);
	
	// 회원별 주문 조회
	@Query("select o from Order o join fetch o.member m "
			+ "where m.id = :memberId order by o.orderDate desc")
	List<Order> findByMemberIdWithMember(@Param("memberId") Long memberId);

	// 검색 조건이 null이면 해당 조건을 무시하고, 값이 있으면 필터로 사용합니다.
	@Query("select o from Order o join fetch o.member m "
			+ "where (:memberId IS NULL or m.id = :memberId) "
			+ "and (:status IS NULL or o.status = :status) "
			+ "order by o.orderDate desc")
	List<Order> search(@Param("memberId") Long memberId, 
			@Param("status") OrderStatus status);

}







