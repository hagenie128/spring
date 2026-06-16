package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.StudyApplication;

public interface StudyApplicationRepository extends JpaRepository<StudyApplication, Long> {

    // TODO backend-10: 신청/합격 목록과 중복 신청 조회 메서드를 작성하세요.
}
