package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Attachment;

/**
 * [첨부파일 DB Repository]
 * 게시글 작성 시 파일 업로드 기능에서 사용 예정
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
