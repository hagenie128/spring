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
@Table(name = "study_application")
public class StudyApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO problem03-2: 신청자와 모집글 연관관계 매핑
    @Transient
    private StudyRecruit studyRecruit;

    @Transient
    private Member applicant;

    // TODO problem03-3: 지원 메시지, 상태, 신청일 매핑
    private String message;

    @Transient
    private ApplicationStatus status = ApplicationStatus.WAITING;

    private LocalDateTime createdAt;
}
