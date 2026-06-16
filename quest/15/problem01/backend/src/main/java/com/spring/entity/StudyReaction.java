package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "study_reaction")
public class StudyReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO backend-7: 회원+모집글+반응 타입을 매핑하세요.
    @Transient
    private StudyRecruit studyRecruit;

    @Transient
    private Member member;

    private ReactionType type;
    private LocalDateTime createdAt;
}
