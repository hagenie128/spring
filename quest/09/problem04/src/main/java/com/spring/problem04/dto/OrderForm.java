package com.spring.problem04.dto;

// [완성된 파일 — 수정 불필요]
public class OrderForm {

    private String customerName;
    private Long menuId;
    private int quantity;
    private String requestMessage;

    public OrderForm() {}

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Long getMenuId() { return menuId; }
    public void setMenuId(Long menuId) { this.menuId = menuId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getRequestMessage() { return requestMessage; }
    public void setRequestMessage(String requestMessage) { this.requestMessage = requestMessage; }
}
