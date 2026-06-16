package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.ApplicationStatus;
import com.spring.entity.Member;
import com.spring.entity.RecruitStatus;
import com.spring.entity.StudyApplication;
import com.spring.entity.StudyRecruit;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyApplicationRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
public class StudyApplicationService {

    private final StudyApplicationRepository applicationRepository;
    private final StudyRecruitRepository studyRecruitRepository;
    private final MemberRepository memberRepository;

    public StudyApplicationService(StudyApplicationRepository applicationRepository,
                                   StudyRecruitRepository studyRecruitRepository,
                                   MemberRepository memberRepository) {
        this.applicationRepository = applicationRepository;
        this.studyRecruitRepository = studyRecruitRepository;
        this.memberRepository = memberRepository;
    }

    // TODO problem03-8,9: 스터디 신청 생성
    @Transactional
    public StudyApplication apply(Long studyId, Long applicantId, String message) {
        throw new UnsupportedOperationException("TODO problem03-8~9");
    }

    // TODO problem03: 신청 상태 변경 기능을 작성하세요.
    @Transactional
    public void accept(Long applicationId) {
        throw new UnsupportedOperationException("TODO problem03-10~13");
    }

    @Transactional
    public void reject(Long applicationId) {
        throw new UnsupportedOperationException("TODO problem03-15");
    }

    // TODO problem04-5: 대기 신청자 목록 서비스
    public List<StudyApplication> getWaitingApplications(StudyRecruit study) {
        throw new UnsupportedOperationException("TODO problem04-5");
    }

    // TODO problem04-6: 합격자 목록 서비스
    public List<StudyApplication> getAcceptedApplications(StudyRecruit study) {
        throw new UnsupportedOperationException("TODO problem04-6");
    }
}
