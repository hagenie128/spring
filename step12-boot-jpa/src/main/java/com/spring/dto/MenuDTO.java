package com.spring.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메뉴 정보를 담는 엔티티(Entity) 클래스입니다.
 * DB의 menu_item 테이블과 직접 매핑됩니다.
 *
 * @Entity  : 이 클래스가 JPA가 관리하는 엔티티임을 선언
 * @Table   : 매핑할 DB 테이블명 지정
 * @Id      : PK 필드 선언
 * @GeneratedValue(strategy = IDENTITY) : AUTO_INCREMENT 사용
 * @Column  : 컬럼 속성 설정 (nullable, length, name 등)
 * @PrePersist : INSERT 직전 자동 실행 (createdAt 자동 설정)
 */

@Data // Getter, Setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 자동 생성
@Entity // 이 클래스가 JPA 엔티티임을 선언 (DB 테이블과 매핑됨)
@Table(name="menu_item") // 매핑할 테이블 이름을 "menu_item"으로 지정
public class MenuDTO {
	@Id // 기본키(Primary Key) 필드임을 선언
	@GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 AUTO_INCREMENT 기능을 사용함
	private Long id;
	
	@Column(nullable = false, length = 100) // NULL 불가, 최대 길이 100
	@NotBlank(message="메뉴명을 입력하세요") // 문자열이 비어있거나 공백이면 에러
	private String name;
	
	@Column(nullable = false)
	@NotNull(message = "가격을 입력하세요") // NULL이면 에러
	@Min(value = 0, message = "가격은 100원 이상이어야 합니다") // 최소값 검증
	private Integer price;
	
	@Column(length = 50)
	private String category;
	
	@Column(length = 500)
	private String description;
	
	@Column
	private boolean available=true;
	
	@Column(name = "created_at", updatable = false) // 수정 시 이 컬럼은 제외함
	private LocalDateTime createdAt;
	
	/**
	 * 엔티티가 영속화(DB에 저장)되기 직전에 자동으로 실행됩니다.
	 * 생성 시간을 현재 시간으로 설정합니다.
	 */
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}


	/**
	 * 샘플 데이터 삽입 등을 위한 생성자
	 */
	public MenuDTO(@NotBlank(message = "메뉴명을 입력하세요") String name,
			@NotNull(message = "가격을 입력하세요") @Min(value = 0, message = "가격은 100원 이상이어야 합니다") Integer price,
			String category, String description, boolean available) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
		this.available = available;
	}
	
}
