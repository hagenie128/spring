package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.StudyReaction;

public interface StudyReactionRepository extends JpaRepository<StudyReaction, Long> {

    // TODO backend-11: 반응 조회와 종류별 개수 조회 메서드를 작성하세요.
}
