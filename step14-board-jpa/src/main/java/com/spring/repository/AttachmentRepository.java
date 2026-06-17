package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

  List<Attachment> findByPostId(Long id);

}
