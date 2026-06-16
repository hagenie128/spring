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

/**
 * [첨부파일 비즈니스 로직 계층]
 *
 * 게시글 작성 시 업로드된 파일을
 *  1) 서버 디스크(uploads 폴더)에 저장하고
 *  2) DB(attachment 테이블)에 메타정보를 기록합니다.
 *
 * [MultipartFile]
 *  - HTML form 의 <input type="file" name="files" multiple> 로 넘어온 파일 객체
 *  - 컨트롤러에서 MultipartFile[] files 로 받음
 *
 * [@Value("${app.upload.dir}")]
 *  - application.properties 의 app.upload.dir 값을 주입 (기본: uploads)
 *
 * [UUID 파일명]
 *  - 원본 파일명 그대로 저장하면 한글·공백·중복 문제가 생길 수 있어
 *    UUID + 확장자 로 고유한 저장 이름을 만듭니다.
 */
@Service
@Transactional(readOnly = true)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final Path uploadPath;

    public AttachmentService(AttachmentRepository attachmentRepository,
            @Value("${app.upload.dir}") String uploadDir) {
        this.attachmentRepository = attachmentRepository;
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath();
    }

    /**
     * 첨부파일 저장
     *
     * @param files 게시글 작성 폼에서 넘어온 파일 배열 (없으면 null)
     * @param post  방금 저장한 게시글 (첨부파일이 어느 글에 속하는지 연결)
     */
    @Transactional
    public void saveFiles(MultipartFile[] files, Post post) throws IOException {
        if (files == null)
            return;

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty())
                continue;

            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            // 디스크에 저장할 고유 파일명 (예: 13977950-5ddd-....png)
            String storedName = UUID.randomUUID() + extension;
            Files.copy(file.getInputStream(), uploadPath.resolve(storedName));

            // DB에 첨부파일 정보 저장
            Attachment attachment = new Attachment();
            attachment.setOriginalName(originalName);
            attachment.setStoredName(storedName);
            attachment.setFileSize(file.getSize());
            attachment.setPost(post);
            attachmentRepository.save(attachment);
        }
    }

}
