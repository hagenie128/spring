package com.spring.problem03.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * [문제 3] OrderForm.java
 * =====================================================================
 * TODO 1: 아래 4개 필드를 선언하세요.
 *   - String customerName  (고객명)
 *   - String menuName      (메뉴명)
 *   - int    quantity      (수량)
 *   - String requestMessage (요청사항)
 *
 * TODO 2: 기본 생성자(매개변수 없는 생성자)를 선언하세요.
 *         Spring MVC가 @ModelAttribute로 바인딩할 때 기본 생성자가 필요합니다.
 *
 * TODO 3: 각 필드의 getter/setter를 구현하세요.
 *         Thymeleaf의 th:field는 getter/setter를 통해 값을 바인딩합니다.
 * =====================================================================
 */
@Data
@NoArgsConstructor
public class OrderForm {

    // TODO 1: 필드 선언
	private String customerName;
	private String menuName;
	private int    quantity;
	private String requestMessage;
    // TODO 2: 기본 생성자 선언

    // TODO 3: getter/setter 구현
}
