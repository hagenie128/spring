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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [회원 엔티티 클래스]
 * 데이터베이스의 'member' 테이블과 1:1로 매핑되는 객체입니다.
 * JPA(Java Persistence API)를 사용하여 자바 객체 관점으로 회원 데이터를 관리합니다.
 * 
 * @Entity: 이 클래스가 JPA 엔티티임을 선언합니다. 즉, 데이터베이스 테이블과 매핑되는 클래스입니다.
 * @Table(name = "member"): 이 엔티티가 매핑될 실제 데이터베이스 테이블 이름을 'member'로 지정합니다.
 * @Data: Lombok 어노테이션으로, Getter, Setter, toString, equals, hashCode 메서드를 자동으로 생성해 줍니다.
 * @NoArgsConstructor: 파라미터가 없는 기본 생성자(Default Constructor)를 자동으로 만들어 줍니다. (JPA 엔티티 필수)
 * @AllArgsConstructor: 모든 필드를 파라미터로 받는 생성자를 자동으로 만들어 줍니다.
 */
@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  /**
   * 회원 고유 번호 (기본 키, PK)
   * 
   * @Id: 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
   * @GeneratedValue(strategy = GenerationType.IDENTITY): 
   *   기본 키 생성 전략을 데이터베이스에 위임합니다. (MySQL/MariaDB의 AUTO_INCREMENT와 동일)
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 로그인 아이디 (중복 불가)
   * 
   * @Column: 테이블의 컬럼과 매핑 설정을 합니다.
   *   - nullable = false: NOT NULL 제약조건을 설정합니다. (필수 입력)
   *   - unique = true: UNIQUE 제약조건을 설정하여 중복 가입을 방지합니다.
   *   - length = 20: 데이터베이스 컬럼의 최대 크기를 20바이트/글자로 제한합니다.
   */
  @Column(nullable = false, unique = true, length = 20)
  private String username;

  /**
   * 암호화된 비밀번호
   * 
   * 비밀번호는 단방향 해시 알고리즘(BCrypt)으로 암호화되어 저장되므로, 
   * 암호화 후 늘어나는 길이를 감안하여 여유 있게 길이를 60으로 설정합니다.
   */
  @Column(nullable = false, length = 60)
  private String password;

  /**
   * 사용자 닉네임
   */
  @Column(nullable = false, length = 20)
  private String nickname;

  /**
   * 회원 권한 (기본값: 'USER')
   * 
   * 일반 회원(USER)과 관리자(ADMIN) 등의 구분을 위해 사용합니다.
   */
  @Column(nullable = false, length = 10)
  private String role = "USER";

  /**
   * 회원 가입일 (수정 불가)
   * 
   * - updatable = false: 데이터가 최초 저장(Insert)된 후, 수정(Update) 쿼리 시 이 컬럼은 제외됩니다.
   * - name = "created_at": 실제 테이블 컬럼 명을 'created_at'으로 매핑합니다.
   */
  @Column(nullable = false, updatable = false, name = "created_at")
  private LocalDateTime createdAt;

  /**
   * [영속성 전 처리(Pre-Persist) 콜백 메서드]
   * 
   * @PrePersist: 엔티티가 디비에 저장(insert)되기 직전에 자동으로 호출되는 메서드입니다.
   *   회원 등록 시 현재 시간을 자동으로 등록 날짜로 세팅해 줍니다.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }
}
