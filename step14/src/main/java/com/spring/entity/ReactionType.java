package com.spring.entity;

/**
 * [반응(좋아요/싫어요) 종류 — enum]
 *
 * enum : 정해진 상수만 가질 수 있는 타입 (LIKE, DISLIKE)
 * JPA 가 DB 에 문자열 또는 ordinal 로 저장합니다.
 *
 * PostReaction, CommentReaction 엔티티에서 reactionType 필드로 사용됩니다.
 */
public enum ReactionType {
	LIKE, DISLIKE
}
