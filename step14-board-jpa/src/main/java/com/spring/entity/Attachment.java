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
 * [첨부파일 엔티티 클래스]
 * 게시글(`Post`)에 업로드되어 서버 내 물리적 공간에 보관되는 파일들의 정보를 담는 테이블 구조입니다.
 */
@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

  /**
   * 첨부파일 일련번호 (PK)
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 사용자가 업로드할 당시의 원본 파일명 (예: "포트폴리오.pdf")
   * 
   * 화면에 파일 링크나 이름을 노출할 때는 이 이름을 보여줍니다.
   */
  @Column(nullable = false, name = "original_name")
  private String originalName;

  /**
   * 서버 물리 경로에 실제로 저장되는 고유한 파일명 (예: "3f82a9d1-8b2c-473d-9d41-3b7c89f53e20.pdf")
   * 
   * 여러 사용자가 동일한 파일명("image.png")을 올렸을 때 덮어쓰기(파일 깨짐) 현상을 피하기 위해 UUID 등으로 고유하게 변형합니다.
   */
  @Column(nullable = false, name = "stored_name")
  private String storedName;

  /**
   * 업로드된 파일 크기 (바이트 수)
   */
  @Column(name = "file_size")
  private Long fileSize;

  /**
   * 파일 업로드 시간 (최초 생성 시간)
   */
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * 이 첨부파일이 소속된 게시글 (N:1 연관관계)
   * 
   * - @ManyToOne(fetch = FetchType.LAZY): 
   *   하나의 게시글(Post)에 여러 첨부파일(Attachment)이 존재할 수 있으므로 N:1 매핑을 지정하고 지연 로딩을 수행합니다.
   * - @JoinColumn(name = "post_id"): 
   *   외래 키(FK) 컬럼을 생성하고 이름을 'post_id'로 매핑합니다.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  /**
   * 첨부파일 정보 DB 저장 전 호출되는 생명주기 콜백
   */
  @PrePersist
  public void onCreate() {
    this.createdAt = LocalDateTime.now();
  }
}
