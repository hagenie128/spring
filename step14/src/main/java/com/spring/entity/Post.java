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
import lombok.NoArgsConstructor;

/**
 * [게시글 엔티티 — DB 의 post 테이블]
 *
 * [연관관계]
 *  @ManyToOne Member   : 게시글 여러 개 → 회원 한 명 (post.member_id FK)
 *  @OneToMany Comment  : 게시글 한 개 → 댓글 여러 개
 *  @OneToMany Attachment : 게시글 한 개 → 첨부파일 여러 개
 *
 * [FetchType.LAZY]
 *  - member, comments 등을 당장 안 쓰면 DB 조회를 나중으로 미룸 (지연 로딩)
 *  - 목록에서 nickname 만 필요할 때 join fetch 로 한 번에 가져오기도 함 (PostRepository)
 *
 * [CascadeType.ALL + orphanRemoval]
 *  - 게시글 삭제 시 딸린 댓글·첨부도 같이 삭제
 */
@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private Long viewCount = 0L;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attachment> attachments = new ArrayList<>();

	/** INSERT 전: 작성일·수정일 자동 설정 */
	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	/** UPDATE 전: 수정일만 갱신 */
	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
