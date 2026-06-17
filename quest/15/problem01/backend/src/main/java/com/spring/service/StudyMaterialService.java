package com.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.StudyMaterial;
import com.spring.repository.StudyMaterialRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
@Transactional(readOnly = true)
public class StudyMaterialService {

    @Value("${app.upload-dir}")
    private String uploadDir; // application.properties의 app.upload-dir 값이 주입됩니다.

    private final StudyMaterialRepository materialRepository;
    private final StudyRecruitRepository studyRecruitRepository;

    public StudyMaterialService(StudyMaterialRepository materialRepository,
                                StudyRecruitRepository studyRecruitRepository) {
        this.materialRepository = materialRepository;
        this.studyRecruitRepository = studyRecruitRepository;
    }

    // TODO backend-21: 파일 업로드
    // 힌트:
    //   1. studyRecruitRepository.findById(studyId).orElseThrow(...)로 스터디를 조회하세요.
    //   2. 저장 디렉토리를 생성하세요:
    //        Path dir = Paths.get(uploadDir);
    //        java.nio.file.Files.createDirectories(dir);
    //   3. 고유 파일명을 생성하세요:
    //        String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    //   4. 파일을 디스크에 저장하세요:
    //        java.nio.file.Files.copy(file.getInputStream(), dir.resolve(savedName));
    //   5. StudyMaterial 엔티티를 만들어 studyRecruit, originalName, savedName, fileSize를 설정하고 저장하세요.
    @Transactional
    public void upload(Long studyId, MultipartFile file) {
        throw new UnsupportedOperationException("TODO backend-21");
    }

    // TODO backend-22: 파일 다운로드 (Resource 반환)
    // 힌트:
    //   1. materialRepository.findById(materialId).orElseThrow(...)로 파일 정보를 조회하세요.
    //   2. 저장된 파일 경로로 UrlResource를 만드세요:
    //        Path path = Paths.get(uploadDir).resolve(material.getSavedName());
    //        return new UrlResource(path.toUri());
    public Resource download(Long materialId) {
        throw new UnsupportedOperationException("TODO backend-22");
    }

    // 파일 정보 조회 (컨트롤러에서 파일명 인코딩에 사용)
    public StudyMaterial findById(Long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));
    }
}
