package com.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.dto.StudyDtos.StudyCreateRequest;
import com.spring.dto.StudyDtos.StudyDetailResponse;
import com.spring.dto.StudyDtos.StudyListResponse;
import com.spring.dto.StudyDtos.StudyUpdateRequest;
import com.spring.entity.RecruitStatus;
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

    public Page<StudyListResponse> search(String keyword, RecruitStatus status, Pageable pageable) {
        throw new UnsupportedOperationException("TODO backend-13");
    }

    public StudyDetailResponse getDetail(Long id) {
        throw new UnsupportedOperationException("TODO backend-14");
    }

    public Long create(StudyCreateRequest request) {
        throw new UnsupportedOperationException("TODO backend-15");
    }

    public void update(Long id, StudyUpdateRequest request) {
        throw new UnsupportedOperationException("TODO backend-16");
    }
}
