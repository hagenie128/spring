package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [댓글 엔티티 클래스]
 * 특정 게시글(`Post`)에 달리는 댓글 내용을 저장하고 관리하는 데이터베이스 매핑 클래스입니다.
 */
@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

  /**
   * 댓글 고유 번호 (PK)
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 댓글 본문 내용 (최대 500자 제한)
   */
  @Column(nullable = false, length = 500)
  private String content;

  /**
   * 댓글을 작성한 회원 (N:1 연관관계)
   * 
   * 한 명의 회원(One)이 여러 개의 댓글(Many)을 작성할 수 있으므로 다대일 매핑을 사용합니다.
   * 외래 키(FK) 컬럼 명을 'member_id'로 생성하여 회원 데이터와 조인합니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false, name = "member_id")
  private Member member;

  /**
   * 댓글이 작성된 게시글 (N:1 연관관계)
   * 
   * 하나의 게시글(One)에 여러 개의 댓글(Many)이 작성되므로 다대일 매핑을 사용합니다.
   * 외래 키(FK) 컬럼 명을 'post_id'로 지정합니다. 
   * 이 필드는 Post 엔티티의 @OneToMany(mappedBy = "post") 와 연결되어 양방향 관계를 이룹니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false, name = "post_id")
  private Post post;

  /**
   * 댓글 등록 시간 (수정 불가)
   */
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * 댓글 정보가 DB에 저장되기 전 호출되는 콜백 메서드
   */
  @PrePersist
  public void onCreate() {
    this.createdAt = LocalDateTime.now();
  }
}
