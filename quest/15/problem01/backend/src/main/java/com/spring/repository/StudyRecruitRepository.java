package com.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.RecruitStatus;
import com.spring.entity.StudyRecruit;

public interface StudyRecruitRepository extends JpaRepository<StudyRecruit, Long> {

    // TODO backend-9: keyword/status 검색 + 페이징 쿼리로 바꾸세요.
    @Query(value = "select s from StudyRecruit s",
           countQuery = "select count(s) from StudyRecruit s")
    Page<StudyRecruit> search(String keyword, RecruitStatus status, Pageable pageable);
}
