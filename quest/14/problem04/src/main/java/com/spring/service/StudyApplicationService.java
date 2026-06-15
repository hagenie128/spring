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
        StudyRecruit study = studyRecruitRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("스터디를 찾을 수 없습니다."));
        if (study.getStatus() == RecruitStatus.CLOSED) {
            throw new IllegalStateException("마감된 스터디에는 신청할 수 없습니다.");
        }

        Member applicant = memberRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        applicationRepository.findByStudyRecruitAndApplicant(study, applicant)
                .ifPresent(existing -> {
                    throw new IllegalStateException("이미 신청한 스터디입니다.");
                });

        StudyApplication application = new StudyApplication();
        application.setStudyRecruit(study);
        application.setApplicant(applicant);
        application.setMessage(message);
        return applicationRepository.save(application);
    }

    // TODO problem03-10~13: 합격 처리, 정원 검사, 자동 마감
    @Transactional
    public void accept(Long applicationId) {
        StudyApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("신청 내역을 찾을 수 없습니다."));
        StudyRecruit study = application.getStudyRecruit();

        if (application.getStatus() == ApplicationStatus.ACCEPTED) {
            return;
        }
        if (study.isFull()) {
            throw new IllegalStateException("이미 정원이 찼습니다.");
        }

        application.setStatus(ApplicationStatus.ACCEPTED);
        study.increaseAcceptedCount();
    }

    @Transactional
    public void reject(Long applicationId) {
        StudyApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("신청 내역을 찾을 수 없습니다."));
        application.setStatus(ApplicationStatus.REJECTED);
    }

    // TODO problem04-5: 대기 신청자 목록 서비스
    public List<StudyApplication> getWaitingApplications(StudyRecruit study) {
        return applicationRepository.findByStudyRecruitAndStatusOrderByIdAsc(study, ApplicationStatus.WAITING);
    }

    // TODO problem04-6: 합격자 목록 서비스
    public List<StudyApplication> getAcceptedApplications(StudyRecruit study) {
        return applicationRepository.findByStudyRecruitAndStatusOrderByIdAsc(study, ApplicationStatus.ACCEPTED);
    }
}
