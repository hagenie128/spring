package com.spring.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.repository.StudyMaterialRepository;
import com.spring.repository.StudyRecruitRepository;

@Service
public class StudyMaterialService {

    private final StudyMaterialRepository materialRepository;
    private final StudyRecruitRepository studyRecruitRepository;

    public StudyMaterialService(StudyMaterialRepository materialRepository,
                                StudyRecruitRepository studyRecruitRepository) {
        this.materialRepository = materialRepository;
        this.studyRecruitRepository = studyRecruitRepository;
    }

    public void upload(Long studyId, MultipartFile file) {
        throw new UnsupportedOperationException("TODO backend-21");
    }

    public Resource download(Long materialId) {
        throw new UnsupportedOperationException("TODO backend-22");
    }
}
