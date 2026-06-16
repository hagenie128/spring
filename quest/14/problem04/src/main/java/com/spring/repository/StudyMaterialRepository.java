package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.StudyMaterial;
import com.spring.entity.StudyRecruit;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

    // TODO problem04-23: 모집글별 파일 목록 조회 메서드를 작성하세요.
    List<StudyMaterial> findByStudyRecruitOrderByIdDesc(StudyRecruit studyRecruit);
}
