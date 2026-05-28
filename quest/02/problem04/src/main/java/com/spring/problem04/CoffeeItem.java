package com.spring.problem04;

/**
 * [도전 4-1] CoffeeItem 클래스를 완성하세요.
 *
 * 요구사항:
 *   1. 아래 필드를 선언하세요.
 *        String name  — 커피 이름
 *        int    price — 가격
 *        String size  — 사이즈 (예: "Large", "Medium")
 *
 *   2. 기본 생성자, getter/setter를 작성하세요.
 *
 *   3. toString()을 아래 형식으로 작성하세요.
 *        "아메리카노 | 4,500원 | Large"
 */
public class CoffeeItem {

    // TODO 1: 필드를 선언하세요.
	// CoffeeItem 하나가 메뉴판의 커피 한 종류를 나타냅니다.
	private String name;
	private int price;
	private String size;

    // TODO 2: 기본 생성자를 작성하세요.
	// Spring은 <property>로 값을 넣기 전에 기본 생성자로 객체를 먼저 만듭니다.
	public CoffeeItem() {	}

    // TODO 2: getter / setter를 작성하세요.
    // setter는 XML의 <property> 값이 들어오는 입구입니다.
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	// TODO 3: toString()을 작성하세요.
    @Override
    public String toString() {
        // CoffeeMenu에서 객체를 출력할 때 보기 좋은 메뉴 형식으로 보여줍니다.
        return String.format("%s | %,d원 | %s", name,price,size); // TODO: 형식에 맞게 수정하세요.
    }

}