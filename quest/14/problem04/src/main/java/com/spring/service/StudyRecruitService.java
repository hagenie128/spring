package com.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Member;
import com.spring.entity.RecruitStatus;
import com.spring.entity.StudyRecruit;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
public class StudyRecruitService {

    private final StudyRecruitRepository studyRecruitRepository;
    private final MemberRepository memberRepository;

    public StudyRecruitService(StudyRecruitRepository studyRecruitRepository,
                               MemberRepository memberRepository) {
        this.studyRecruitRepository = studyRecruitRepository;
        this.memberRepository = memberRepository;
    }

    // TODO problem01-8: 검색 조건에 따라 Page 조회
    public Page<StudyRecruit> search(String keyword, RecruitStatus status, Pageable pageable) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        if (normalizedKeyword.isEmpty() && status == null) {
            return studyRecruitRepository.findAllWithLeader(pageable);
        }
        return studyRecruitRepository.search(normalizedKeyword, status, pageable);
    }

    // TODO problem02-6~8: 모집글 등록
    @Transactional
    public StudyRecruit create(Long leaderId, String title, String description,
                               String techStack, String method, Integer capacity) {
        if (capacity == null || capacity < 1) {
            throw new IllegalArgumentException("모집 정원은 1명 이상이어야 합니다.");
        }

        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        StudyRecruit study = new StudyRecruit();
        study.setLeader(leader);
        study.setTitle(title);
        study.setDescription(description);
        study.setTechStack(techStack);
        study.setMethod(method);
        study.setCapacity(capacity);
        return studyRecruitRepository.save(study);
    }

    // TODO problem04-4: 상세 조회 + 조회수 증가
    @Transactional
    public StudyRecruit getDetail(Long id) {
        StudyRecruit study = studyRecruitRepository.findByIdWithLeader(id)
                .orElseThrow(() -> new IllegalArgumentException("스터디 모집글을 찾을 수 없습니다."));
        study.setViewCount(study.getViewCount() + 1);
        return study;
    }
}
