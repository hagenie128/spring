package com.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * MenuDTO (Data Transfer Object)
 * 데이터 전송을 위한 객체입니다.
 * Lombok 어노테이션을 사용하여 getter, setter, 생성자 등을 자동으로 생성합니다.
 */
@Getter         // 모든 필드의 Getter 메서드 자동 생성
@Setter         // 모든 필드의 Setter 메서드 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
@ToString       // toString() 메서드 자동 생성
public class MenuDTO {
	private long id;
	private String name;
	private String category;
	private int price;
}





