package com.spring.entity;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [댓글 반응(좋아요/싫어요) 엔티티 클래스]
 * 
 * @Entity: JPA가 관리하는 데이터베이스 매핑 객체임을 선언합니다.
 * @Table: 매핑할 테이블 정보 및 제약조건을 설정합니다.
 *         - name: 테이블 이름을 'comment_reaction'으로 지정합니다.
 *         - uniqueConstraints: 한 명의 회원이 하나의 댓글에 단 하나의 반응만 남기도록 (member_id, comment_id) 복합 유니크 제약을 겁니다.
 * @Data: Lombok 어노테이션으로 Getter, Setter, toString, equals, hashCode 등을 자동으로 생성합니다.
 * @NoArgsConstructor: JPA 엔티티 사용을 위한 파라미터가 없는 기본 생성자를 생성합니다.
 * @AllArgsConstructor: 모든 필드를 매개변수로 갖는 생성자를 생성합니다.
 */
@Entity
@Table(name = "comment_reaction", uniqueConstraints = @UniqueConstraint(columnNames = { "member_id", "comment_id" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReaction {

  // @Id: 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
  // @GeneratedValue: 기본 키 생성 전략을 설정하며, IDENTITY는 DB의 AUTO_INCREMENT와 매핑됩니다.
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @ManyToOne: N:1(다대일) 연관관계 매핑입니다. 하나의 회원(Member)은 여러 댓글 반응을 작성할 수 있습니다.
  // fetch = FetchType.LAZY: 지연 로딩을 설정하여, 실제로 member 객체를 조회해 사용하기 전까지는 DB 조회를 미뤄 성능을 최적화합니다.
  // @JoinColumn: 외래 키(FK) 컬럼을 지정합니다. 여기서는 member_id와 매핑됩니다.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  // @ManyToOne: 하나의 댓글(Comment)은 여러 개의 댓글 반응을 가질 수 있습니다. 지연 로딩을 적용합니다.
  // @JoinColumn: 외래 키 컬럼명을 comment_id로 매핑합니다.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "comment_id", nullable = false)
  private Comment comment;

  // @Enumerated: 자바의 Enum 타입을 테이블에 저장할 때 설정을 지정합니다.
  // EnumType.STRING: Enum의 이름(LIKE, DISLIKE 문자열 자체)을 DB에 저장합니다. (기본값인 ORDINAL은 순서 숫자를 저장하므로 Enum 수정 시 안전하지 않습니다.)
  // @Column: DB 컬럼 속성을 지정합니다. NULL을 허용하지 않고 최대 길이를 10글자로 제한합니다.
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private ReactionType type;
}
