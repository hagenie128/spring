package com.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.StudyDtos.StudyCreateRequest;
import com.spring.dto.StudyDtos.StudyDetailResponse;
import com.spring.dto.StudyDtos.StudyListResponse;
import com.spring.dto.StudyDtos.StudyUpdateRequest;
import com.spring.entity.RecruitStatus;
import com.spring.repository.MemberRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
@Transactional(readOnly = true)
public class StudyRecruitService {

    private final StudyRecruitRepository studyRecruitRepository;
    private final MemberRepository memberRepository;

    public StudyRecruitService(StudyRecruitRepository studyRecruitRepository,
                               MemberRepository memberRepository) {
        this.studyRecruitRepository = studyRecruitRepository;
        this.memberRepository = memberRepository;
    }

    // TODO backend-13: 목록 검색 (검색어 + 상태 필터 + 페이징)
    // 힌트:
    //   1. keyword/status 조건에 따라 studyRecruitRepository의 검색 메서드를 선택해 호출하세요.
    //   2. Page<StudyRecruit> → Page<StudyListResponse>로 변환하려면 page.map(entity -> new StudyListResponse(...))를 사용하세요.
    //   3. StudyListResponse 생성자 파라미터는 StudyDtos 클래스를 참고하세요.
    public Page<StudyListResponse> search(String keyword, RecruitStatus status, Pageable pageable) {
        throw new UnsupportedOperationException("TODO backend-13");
    }

    // TODO backend-14: 상세 조회 (조회수 증가 포함)
    // 힌트:
    //   1. studyRecruitRepository.findById(id).orElseThrow(...)로 엔티티를 가져오세요.
    //   2. study.setViewCount(study.getViewCount() + 1) 로 조회수를 올리세요. (Dirty Checking → 자동 UPDATE)
    //   3. StudyDetailResponse로 변환해 반환하세요.
    public StudyDetailResponse getDetail(Long id) {
        throw new UnsupportedOperationException("TODO backend-14");
    }

    // TODO backend-15: 모집글 등록
    // 힌트:
    //   1. memberRepository.findById(request.getLeaderId()).orElseThrow(...)로 리더를 조회하세요.
    //   2. StudyRecruit 엔티티를 새로 만들고 필드를 채운 뒤 studyRecruitRepository.save(entity)하세요.
    //   3. 저장된 엔티티의 getId()를 반환하세요.
    @Transactional
    public Long create(StudyCreateRequest request) {
        throw new UnsupportedOperationException("TODO backend-15");
    }

    // TODO backend-16: 모집글 수정
    // 힌트:
    //   1. studyRecruitRepository.findById(id).orElseThrow(...)로 엔티티를 가져오세요.
    //   2. 엔티티의 set 메서드로 값을 변경하세요. save() 없이도 Dirty Checking으로 자동 UPDATE됩니다.
    @Transactional
    public void update(Long id, StudyUpdateRequest request) {
        throw new UnsupportedOperationException("TODO backend-16");
    }
}
