package com.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@ToString
@AllArgsConstructor  //모든 필드를 매개변수로 받는 생성자 자동 생성
//@Data - @Getter, @Setter, @ToString, @EqualsAndHashCode, 
//			@RequiredArgsConstructor를 모두 포함하는 어노테이션
public class MenuDTO {
	private long id;
	private String name;
	private String category;
	private int price;
	
}
