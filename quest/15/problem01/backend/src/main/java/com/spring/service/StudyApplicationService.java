package com.spring.service;

import org.springframework.stereotype.Service;

import com.spring.dto.StudyDtos.ApplicationCreateRequest;
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

    public void apply(Long studyId, ApplicationCreateRequest request) {
        throw new UnsupportedOperationException("TODO backend-17");
    }

    public void accept(Long applicationId) {
        throw new UnsupportedOperationException("TODO backend-18");
    }

    public void reject(Long applicationId) {
        throw new UnsupportedOperationException("TODO backend-19");
    }
}
