package com.spring.problem04;

/**
 * 메뉴 데이터 전송 객체 [완성된 파일 — 수정 불필요]
 * Jackson 직렬화를 위해 기본 생성자 + getter 필수
 */
public class MenuDto {

    private Long id;
    private String name;
    private int price;
    private boolean available;

    // Jackson 직렬화를 위한 기본 생성자 (필수!)
    public MenuDto() {}

    // 편의용 전체 생성자
    public MenuDto(Long id, String name, int price, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
    }

    // --- Getter/Setter ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
