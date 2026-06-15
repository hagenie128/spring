package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.ApplicationStatus;
import com.spring.entity.Member;
import com.spring.entity.StudyApplication;
import com.spring.entity.StudyRecruit;

public interface StudyApplicationRepository extends JpaRepository<StudyApplication, Long> {

    // TODO problem03-5: 모집글별 신청 목록 조회
    List<StudyApplication> findByStudyRecruitOrderByIdAsc(StudyRecruit studyRecruit);

    // TODO problem03-6, problem04-2,3: 상태별 신청자/합격자 목록 조회
    List<StudyApplication> findByStudyRecruitAndStatusOrderByIdAsc(StudyRecruit studyRecruit,
                                                                    ApplicationStatus status);

    // TODO problem03-7: 회원+모집글 신청 여부 조회
    Optional<StudyApplication> findByStudyRecruitAndApplicant(StudyRecruit studyRecruit, Member applicant);
}
