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
@Table(name = "study_material")
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO problem04-21: 파일 정보 필드 매핑
    private String originalName;
    private String storedName;
    private Long fileSize;

    // TODO problem04-22: 모집글 연관관계 매핑
    @Transient
    private StudyRecruit studyRecruit;

    private LocalDateTime createdAt;
}
