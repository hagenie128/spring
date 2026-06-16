package com.spring.service;

import org.springframework.stereotype.Service;

import com.spring.dto.StudyDtos.ReactionRequest;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyReactionRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
public class StudyReactionService {

    private final StudyReactionRepository reactionRepository;
    private final StudyRecruitRepository studyRecruitRepository;
    private final MemberRepository memberRepository;

    public StudyReactionService(StudyReactionRepository reactionRepository,
                                StudyRecruitRepository studyRecruitRepository,
                                MemberRepository memberRepository) {
        this.reactionRepository = reactionRepository;
        this.studyRecruitRepository = studyRecruitRepository;
        this.memberRepository = memberRepository;
    }

    public void toggle(Long studyId, ReactionRequest request) {
        throw new UnsupportedOperationException("TODO backend-20");
    }
}
