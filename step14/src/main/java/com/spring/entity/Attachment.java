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
import lombok.NoArgsConstructor;

/**
 * [첨부파일 엔티티 — attachment 테이블]
 *
 * originalName : 사용자가 올린 원본 파일명 (예: 보고서.pdf)
 * storedName   : 서버 디스크에 저장할 때 쓰는 UUID 등 고유 이름 (중복 방지)
 * post         : 어느 게시글에 붙은 파일인지 (@ManyToOne)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachment")
@Entity
public class Attachment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "original_name")
  private String originalName;

  @Column(nullable = false, name = "stored_name")
  private String storedName;

  @Column(name = "file_size")
  private Long fileSize;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  public void onCreate() {
    createdAt = LocalDateTime.now();
  }
}