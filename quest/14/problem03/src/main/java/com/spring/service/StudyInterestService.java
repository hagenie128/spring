package com.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Member;
import com.spring.entity.StudyInterest;
import com.spring.entity.StudyRecruit;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyInterestRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
public class StudyInterestService {

    private final StudyInterestRepository interestRepository;
    private final StudyRecruitRepository studyRecruitRepository;
    private final MemberRepository memberRepository;

    public StudyInterestService(StudyInterestRepository interestRepository,
                                StudyRecruitRepository studyRecruitRepository,
                                MemberRepository memberRepository) {
        this.interestRepository = interestRepository;
        this.studyRecruitRepository = studyRecruitRepository;
        this.memberRepository = memberRepository;
    }

    // TODO problem04-11: 관심 등록/취소 토글
    @Transactional
    public void toggle(Long studyId, Long memberId) {
        StudyRecruit study = studyRecruitRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("스터디를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        interestRepository.findByStudyRecruitAndMember(study, member)
                .ifPresentOrElse(interestRepository::delete, () -> {
                    StudyInterest interest = new StudyInterest();
                    interest.setStudyRecruit(study);
                    interest.setMember(member);
                    interestRepository.save(interest);
                });
    }

    public long count(StudyRecruit study) {
        return interestRepository.countByStudyRecruit(study);
    }
}
