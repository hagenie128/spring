package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [게시글 좋아요/싫어요 엔티티 — post_reaction 테이블]
 *
 * @UniqueConstraint(member_id, post_id)
 *  - 한 회원이 같은 게시글에 중복으로 반응하지 못하게 DB 에서 막음
 *
 * @Enumerated(EnumType.STRING)
 *  - enum 을 DB 에 "LIKE", "DISLIKE" 문자열로 저장 (가독성 좋음)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_reaction", uniqueConstraints = @UniqueConstraint(
    columnNames = {"member_id", "post_id"}))
@Entity
public class PostReaction {

    // @Id: 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    // @GeneratedValue: 기본 키 생성 전략을 설정하며, IDENTITY는 DB의 AUTO_INCREMENT와 매핑됩니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne: 하나의 회원(Member)은 여러 게시글 반응을 작성할 수 있습니다.
    // fetch = FetchType.LAZY: 실제로 member를 사용할 때까지 조회를 미룹니다.
    // @JoinColumn: 외래 키 컬럼명을 member_id로 매핑합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // @ManyToOne: 하나의 게시글(Post)은 여러 개의 게시글 반응을 가질 수 있습니다.
    // @JoinColumn: 외래 키 컬럼명을 post_id로 매핑합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // @Enumerated(EnumType.STRING): enum 순서가 아니라 LIKE/DISLIKE 문자열로 저장합니다.
    // ORDINAL 방식은 enum 순서가 바뀌면 데이터 의미가 달라질 수 있어 사용하지 않습니다.
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

