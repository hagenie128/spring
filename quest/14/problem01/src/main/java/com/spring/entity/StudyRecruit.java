package com.spring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
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
    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 200)
    private String techStack;

    @Column(nullable = false, length = 30)
    private String method;

    @Column(nullable = false)
    private Long viewCount = 0L;

    // TODO problem01-2, problem02-2: 모집 정원과 합격 인원 관리
    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer acceptedCount = 0;

    // TODO problem01-3, problem02-3: 모집 상태 매핑과 기본값
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RecruitStatus status = RecruitStatus.OPEN;

    // TODO problem01-4: 작성자 Member와 ManyToOne 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private Member leader;

    @OneToMany(mappedBy = "studyRecruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyApplication> applications = new ArrayList<>();

    @OneToMany(mappedBy = "studyRecruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyInterest> interests = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // TODO problem02-4: 정원이 찼는지 판단
    public boolean isFull() {
        return capacity != null && acceptedCount != null && acceptedCount >= capacity;
    }

    // TODO problem02-5: 정원 도달 시 CLOSED로 변경
    public void closeIfFull() {
        if (isFull()) {
            status = RecruitStatus.CLOSED;
        }
    }

    public void increaseAcceptedCount() {
        acceptedCount++;
        closeIfFull();
    }

    // TODO problem01-5: 작성일/수정일 자동 설정
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
