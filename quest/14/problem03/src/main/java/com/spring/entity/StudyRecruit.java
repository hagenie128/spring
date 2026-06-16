package com.spring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "study_recruit")
public class StudyRecruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO problem01-1: 제목, 소개글, 기술스택, 진행방식, 조회수 필드 매핑
    private String title;

    private String description;

    private String techStack;

    private String method;

    private Long viewCount = 0L;

    // TODO problem01-2: 모집 정원과 합격 인원 필드 매핑
    private Integer capacity;

    private Integer acceptedCount = 0;

    // TODO problem01-3: 모집 상태 enum 매핑
    private RecruitStatus status = RecruitStatus.OPEN;

    // TODO problem01-4: 작성자 Member와 연관관계 매핑
    @Transient
    private Member leader;

    @Transient
    private List<StudyApplication> applications = new ArrayList<>();

    @Transient
    private List<StudyInterest> interests = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // TODO problem02-4: 정원이 찼는지 판단
    public boolean isFull() {
        throw new UnsupportedOperationException("TODO problem02-4");
    }

    // TODO problem02-5: 정원 도달 시 CLOSED로 변경
    public void closeIfFull() {
        throw new UnsupportedOperationException("TODO problem02-5");
    }

    public void increaseAcceptedCount() {
        throw new UnsupportedOperationException("TODO problem03");
    }

    // TODO problem01-5: 작성일/수정일 자동 설정
    @PrePersist
    public void onCreate() {
        // TODO problem01-5
    }

    @PreUpdate
    public void onUpdate() {
        // TODO problem01-5
    }
}
