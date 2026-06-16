package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.StudyMaterial;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

    // TODO backend-12: 모집글별 파일 목록 조회 메서드를 작성하세요.
}
