package com.spring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [게시글 엔티티 클래스]
 * 데이터베이스의 'post' 테이블과 매핑되는 엔티티로, 게시판의 주요 정보를 담고 있습니다.
 * 회원(`Member`), 댓글(`Comment`), 첨부파일(`Attachment`)과의 유기적인 연관관계를 맺고 있습니다.
 */
@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  /**
   * 게시글 번호 (기본 키, PK)
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 게시글 제목
   */
  @Column(nullable = false, length = 200)
  private String title;

  /**
   * 게시글 내용
   * 
   * - columnDefinition = "TEXT": 일반적인 VARCHAR 타입은 대용량 본문을 담기 부족하므로, 
   *   데이터베이스에서 긴 텍스트를 담을 수 있는 TEXT 데이터 타입으로 컬럼 타입을 강제 설정합니다.
   */
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  /**
   * 게시글 작성자 (N:1 단방향/양방향 연관관계)
   * 
   * - @ManyToOne: 여러 개의 게시글(Many)이 하나의 회원(One)에 속하므로 다대일 관계를 나타냅니다.
   * - fetch = FetchType.LAZY: **지연 로딩(Lazy Loading)** 전략을 취합니다.
   *   게시글을 조회할 때 연관된 회원 데이터를 처음부터 강제로 가져오지 않고, 실제 회원 정보가 필요할 때(예: member.getNickname())
   *   프록시 객체를 통해 SQL을 실행하도록 하여 DB 조회 부하와 메모리 낭비를 방지합니다.
   * - @JoinColumn(name = "member_id"): 테이블 상에서 외래 키(FK) 컬럼 명을 'member_id'로 생성합니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  /**
   * 조회수 (기본값: 0)
   */
  @Column(nullable = false)
  private Long viewCount = 0L;

  /**
   * 게시글 작성일
   */
  @Column(nullable = false)
  private LocalDateTime createdAt;

  /**
   * 게시글 최종 수정일
   */
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  /**
   * 게시글에 달린 댓글 목록 (1:N 양방향 연관관계)
   * 
   * - @OneToMany(mappedBy = "post"): 일대다 관계를 나타냅니다.
   *   - mappedBy = "post": **연관관계의 주인(Owner)**이 반대편(Comment.java)의 'post' 필드임을 지정합니다.
   *     데이터베이스에서는 댓글(Comment)이 외래 키(FK)를 가지므로 Comment가 연관관계의 주인이 되고, 
   *     Post 엔티티 측은 단순 읽기용 조회를 위해 매핑만 받아두는 구조입니다.
   *   - cascade = CascadeType.ALL: **영속성 전이(Cascade)** 옵션입니다.
   *     게시글 엔티티가 저장, 수정, 삭제될 때 연관된 댓글 리스트들도 데이터베이스 상에 함께 저장, 수정, 삭제가 자동 수행됩니다.
   *   - orphanRemoval = true: **고아 객체 제거(Orphan Removal)** 기능입니다.
   *     게시글에서 댓글 객체만 리스트에서 지웠을 경우(`post.getComments().remove(comment)`), 
   *     연관관계가 끊어진 댓글 데이터를 데이터베이스에서도 자동으로 DELETE 쿼리를 날려 삭제합니다.
   */
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  /**
   * 게시글에 업로드된 첨부파일 목록 (1:N 양방향 연관관계)
   */
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Attachment> attachements = new ArrayList<>();

  /**
   * 게시글 등록 시 호출되는 생명주기 콜백
   */
  @PrePersist
  public void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * 게시글 수정 시 호출되는 생명주기 콜백
   */
  @PreUpdate
  public void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
