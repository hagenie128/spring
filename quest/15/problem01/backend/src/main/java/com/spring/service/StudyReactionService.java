package com.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.StudyDtos.ReactionRequest;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyReactionRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
@Transactional(readOnly = true)
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

    // TODO backend-20: 좋아요/싫어요 토글 처리
    // 힌트:
    //   1. studyRecruit와 member를 각 repository에서 조회하세요.
    //   2. reactionRepository에서 (studyRecruit, member) 조합으로 기존 반응이 있는지 확인하세요.
    //   3. 기존 반응이 있을 때:
    //        - 같은 타입이면 → 취소(reactionRepository.delete(existing))
    //        - 다른 타입이면 → existing.setType(request.getType()) (Dirty Checking)
    //   4. 기존 반응이 없을 때:
    //        - 새 StudyReaction 엔티티를 만들어 저장하세요.
    @Transactional
    public void toggle(Long studyId, ReactionRequest request) {
        throw new UnsupportedOperationException("TODO backend-20");
    }
}
