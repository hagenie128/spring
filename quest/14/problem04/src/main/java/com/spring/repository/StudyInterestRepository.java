package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Member;
import com.spring.entity.StudyInterest;
import com.spring.entity.StudyRecruit;

public interface StudyInterestRepository extends JpaRepository<StudyInterest, Long> {

    // TODO problem04-9: 관심 여부 조회
    Optional<StudyInterest> findByStudyRecruitAndMember(StudyRecruit studyRecruit, Member member);

    // TODO problem04-10: 모집글별 관심 수 조회
    long countByStudyRecruit(StudyRecruit studyRecruit);
}
