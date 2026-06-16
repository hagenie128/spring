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
        throw new UnsupportedOperationException("TODO problem01-8");
    }

    // TODO problem02-6~8: 모집글 등록
    @Transactional
    public StudyRecruit create(Long leaderId, String title, String description,
                               String techStack, String method, Integer capacity) {
        throw new UnsupportedOperationException("TODO problem02-6~8");
    }

    // TODO problem04-4: 상세 조회 + 조회수 증가
    @Transactional
    public StudyRecruit getDetail(Long id) {
        throw new UnsupportedOperationException("TODO problem04-4");
    }
}
