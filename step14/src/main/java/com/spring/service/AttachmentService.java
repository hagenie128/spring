package com.spring.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.Attachment;
import com.spring.entity.Post;
import com.spring.repository.AttachmentRepository;

@Service
@Transactional(readOnly = true)
public class AttachmentService {
  private final AttachmentRepository attachmentRepository;
  private final Path uploadPath;

  public AttachmentService(AttachmentRepository attachmentRepository,
    @Value("${app.upload.dir}") String uploadDir)
  {
    this.attachmentRepository = attachmentRepository;
    this.uploadPath = Paths.get(uploadDir).toAbsolutePath();
  }

  @Transactional
  public void saveFiles(MultipartFile[] files, Post post) throws IOException {
    // 파일 쓰기 처리
    // 1. 업로드한 파일이 있는지 체크 없으면 메서드 종료.
    if(files == null) return;
    // 2. 업로드한 파일 한대씩 읽어서 파일 쓰기를 수행
    //      저장하는 이름은 원본파일명이 아니라 임의의 일렬번호로 처리
    for(MultipartFile file : files){
      if(file == null || file.isEmpty()) continue;  

      String originalName = file.getOriginalFilename();
      String extension = "";
      if(originalName != null && originalName.contains(".")){
        extension = originalName.substring(originalName.lastIndexOf("."));
      }
      String storedName = UUID.randomUUID() + extension;
      // 물리적으로 파일쓰기 완료
      Files.copy(file.getInputStream(), uploadPath.resolve(storedName));
      
      // 3. DB에 업로드한 파일 정보를 저장
      Attachment attachment = new Attachment();
      attachment.setOriginalName(originalName);
      attachment.setStoredName(storedName);
      attachment.setFileSize(file.getSize());
      attachment.setPost(post);
      attachmentRepository.save(attachment);
    }
  }

}