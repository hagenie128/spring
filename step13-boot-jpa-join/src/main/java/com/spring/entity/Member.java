package com.spring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {
	// 회원 테이블의 기본키입니다. IDENTITY는 DB의 AUTO_INCREMENT 전략을 사용합니다.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @NonNull 필드는 Lombok의 RequiredArgsConstructor 생성자 대상에 포함됩니다.
	@Column(nullable = false, length = 50)
	@NonNull
	@NotBlank(message = "이름을 반드시 입력하세요")
	private String name;
	
	// 이메일은 로그인/식별 값으로 사용할 수 있으므로 unique 제약을 둡니다.
	@Column(nullable = false, length = 100, unique = true)
	@NonNull
	@NotBlank(message = "이메일을 반드시 입력하세요")
	@Email(message = "이메일 형식이 아닙니다.")
	private String email;
	
	
	@Column(nullable = false, length = 100)
	@NonNull
	private String phone;
	
	@Column(name = "created_At", updatable = false)
	private LocalDateTime createdAt;
	
	// Member(1) : Order(N) 관계입니다. mappedBy는 연관관계의 주인이 Order.member임을 뜻합니다.
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();
	
}








