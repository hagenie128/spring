package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [회원 엔티티 — DB 의 member 테이블과 1:1 매핑]
 *
 * [Entity / JPA 어노테이션]
 *  @Entity     : JPA 가 관리하는 DB 테이블과 연결되는 클래스
 *  @Table      : 실제 테이블 이름 지정 (없으면 클래스명 member)
 *  @Id         : Primary Key (기본키)
 *  @GeneratedValue(IDENTITY) : MySQL AUTO_INCREMENT 처럼 DB가 번호 자동 생성
 *  @Column     : 컬럼 세부 설정 (nullable, length, unique 등)
 *  @PrePersist : INSERT 직전에 실행 (createdAt 자동 설정)
 *
 * [Lombok]
 *  @Data              : getter, setter, toString, equals, hashCode 자동 생성
 *  @NoArgsConstructor : 기본 생성자 (JPA 필수)
 *  @AllArgsConstructor: 모든 필드 생성자
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
public class Member {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique = true, length = 20)
	private String username;

	@Column(name = "password", nullable = false, length = 60)
	private String password;  // 추후 BCrypt 해시 저장 (spring-security-crypto)

	@Column(name = "nickname", nullable = false, unique = true, length = 20)
	private String nickname;

	@Column(name = "role", length = 10)
	private String role = "USER";  // USER | ADMIN

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
}
