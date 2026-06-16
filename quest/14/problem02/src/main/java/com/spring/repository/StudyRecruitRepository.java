package com.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.RecruitStatus;
import com.spring.entity.StudyRecruit;

public interface StudyRecruitRepository extends JpaRepository<StudyRecruit, Long> {

    // TODO problem01-6: 작성자 fetch join 목록 쿼리로 바꾸세요.
    @Query(value = "select s from StudyRecruit s",
           countQuery = "select count(s) from StudyRecruit s")
    Page<StudyRecruit> findAllWithLeader(Pageable pageable);

    // TODO problem01-7: 키워드 + 모집 상태 검색 조건을 추가하세요.
    @Query(value = "select s from StudyRecruit s",
           countQuery = "select count(s) from StudyRecruit s")
    Page<StudyRecruit> search(String keyword,
                              RecruitStatus status,
                              Pageable pageable);

    // TODO problem04-1: 상세 조회 시 작성자 fetch join
    // 문제 4에서 직접 추가하세요.
}
