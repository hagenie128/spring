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

/*회원 번호(Long,PK)
로그인 아이디(String,NotNull,Unique)
암호(String,NotNull)
닉네임(String,NotNull)
권한(String,role= USER | ADMIN, defalt=USER)
가입일(LocalDateTime, NotNull, defalt = CURRENT_TIMESTAMP)*/

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
	private String password;

	@Column(name = "nickname", nullable = false,unique = true, length = 20)
	private String nickname;

	@Column(name = "role", length = 10)
	private String role = "USER";

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
}
