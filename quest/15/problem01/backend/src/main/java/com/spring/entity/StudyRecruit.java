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
@Table(name = "study_recruit")
public class StudyRecruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO backend-5: 모집글 필드와 작성자를 매핑하세요.
    private String title;
    private String description;
    private String techStack;
    private String method;
    private Integer capacity;
    private Integer acceptedCount;
    private Long viewCount;
    private RecruitStatus status;

    @Transient
    private Member leader;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isFull() {
        throw new UnsupportedOperationException("TODO backend-5");
    }
}
