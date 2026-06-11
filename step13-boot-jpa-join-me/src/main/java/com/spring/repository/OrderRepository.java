package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Order;
import com.spring.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // join fetch를 사용하여 주문 목록+회원 (n+1) 방지
    @Query("select o from Order o left join fetch o.member order by o.orderDate desc")
    List<Order> findAllWithMember();

    // 다중 fetch join을 사용하여 주문+회원+주문항목+메뉴
    @Query("select distinct o from Order o "
            + "join fetch o.member "
            + "join fetch o.orderItems oi "
            + "join fetch oi.menuItem  "
            + "order by o.orderDate desc")
    List<Order> findAllWithDetails();

    //상세 조회 - 주문항목+메뉴
    @Query("select distinct o from Order o "
    + "join fetch o.member "
    + "join fetch o.orderItems oi "
    + "join fetch oi.menuItem  "
    + "where o.id = :id")
    Optional<Order> findByIdWithDetails(@Param("id") Long id);

    //상태별 조회 - 주문 대기, 주문 확정, 주문 완료, 주문 취소
    @Query("select distinct o from Order o "
    + "join fetch o.member "
    + "where o.status = :status "
    + "order by o.orderDate desc")
    List<Order> findByStatusWithMember(@Param("status") OrderStatus status);


    //회원별 주문 목록 조회
    @Query("select o from Order o "
    + "join fetch o.member m "
    + "where m.id = :memberId "
    + "order by o.orderDate desc")
    List<Order> findByMemberIdWithMember(@Param("memberId") Long memberId);

    //검색 회원+상태 필터
    @Query("select distinct o from Order o "
    + "join fetch o.member m "
    + "where (:memberId is null or m.id = :memberId) "
    + "and (:status is null or o.status = :status) "
    + "order by o.orderDate desc")
    List<Order> search(@Param("name") String name, @Param("status") OrderStatus status);
}
