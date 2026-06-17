package com.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.StudyDtos.ApplicationCreateRequest;
import com.spring.entity.ApplicationStatus;
import com.spring.entity.RecruitStatus;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyApplicationRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
@Transactional(readOnly = true)
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

    // TODO backend-17: 스터디 신청
    // 힌트:
    //   1. studyRecruit와 applicant(member)를 각 repository에서 조회하세요.
    //   2. 중복 신청 확인: applicationRepository에서 중복 신청 여부를 확인하고, 있으면 예외를 던지세요.
    //   3. StudyApplication 엔티티를 만들어 status=WAITING으로 저장하세요.
    @Transactional
    public void apply(Long studyId, ApplicationCreateRequest request) {
        throw new UnsupportedOperationException("TODO backend-17");
    }

    // TODO backend-18: 신청 합격 처리
    // 힌트:
    //   1. applicationRepository.findById(applicationId).orElseThrow(...)로 신청 엔티티를 가져오세요.
    //   2. studyRecruit의 acceptedCount와 capacity를 비교해 정원 초과 여부를 확인하세요.
    //   3. application.setStatus(ApplicationStatus.ACCEPTED)로 상태를 변경하세요. (Dirty Checking)
    //   4. study.setAcceptedCount(study.getAcceptedCount() + 1) 로 합격 인원을 증가시키세요.
    //   5. acceptedCount >= capacity이면 study.setStatus(RecruitStatus.CLOSED)로 마감 처리하세요.
    @Transactional
    public void accept(Long applicationId) {
        throw new UnsupportedOperationException("TODO backend-18");
    }

    // TODO backend-19: 신청 거절 처리
    // 힌트:
    //   1. applicationRepository.findById(applicationId).orElseThrow(...)로 신청 엔티티를 가져오세요.
    //   2. application.setStatus(ApplicationStatus.REJECTED)로 상태를 변경하세요. (Dirty Checking)
    @Transactional
    public void reject(Long applicationId) {
        throw new UnsupportedOperationException("TODO backend-19");
    }
}
