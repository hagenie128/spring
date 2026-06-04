package com.spring.problem01;

/**
 * 주문 데이터 전송 객체
 * Jackson 직렬화를 위해 기본 생성자 + getter 필수
 */
public class OrderDto {

    private Long orderId;
    private String menuName;
    private int quantity;
    private int totalPrice;

    // Jackson 직렬화를 위한 기본 생성자 (필수!)
    public OrderDto() {}

    // 편의용 전체 생성자
    public OrderDto(Long orderId, String menuName, int quantity, int totalPrice) {
        this.orderId = orderId;
        this.menuName = menuName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // --- Getter/Setter ---

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
}
