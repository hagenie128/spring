package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
