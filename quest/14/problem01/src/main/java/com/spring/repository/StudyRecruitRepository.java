package com.spring.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.RecruitStatus;
import com.spring.entity.StudyRecruit;

public interface StudyRecruitRepository extends JpaRepository<StudyRecruit, Long> {

    // TODO problem01-6: 작성자 fetch join 목록 쿼리
    @Query(value = "select s from StudyRecruit s join fetch s.leader order by s.id desc",
           countQuery = "select count(s) from StudyRecruit s")
    Page<StudyRecruit> findAllWithLeader(Pageable pageable);

    // TODO problem01-7: 키워드 + 모집 상태 검색 쿼리
    @Query(value = """
            select s from StudyRecruit s
            join fetch s.leader
            where (:keyword = '' or s.title like concat('%', :keyword, '%')
                or s.description like concat('%', :keyword, '%')
                or s.techStack like concat('%', :keyword, '%'))
              and (:status is null or s.status = :status)
            order by s.id desc
            """,
           countQuery = """
            select count(s) from StudyRecruit s
            where (:keyword = '' or s.title like concat('%', :keyword, '%')
                or s.description like concat('%', :keyword, '%')
                or s.techStack like concat('%', :keyword, '%'))
              and (:status is null or s.status = :status)
            """)
    Page<StudyRecruit> search(@Param("keyword") String keyword,
                              @Param("status") RecruitStatus status,
                              Pageable pageable);

    // TODO problem04-1: 상세 조회 시 작성자 fetch join
    @Query("select s from StudyRecruit s join fetch s.leader where s.id = :id")
    Optional<StudyRecruit> findByIdWithLeader(@Param("id") Long id);
}
