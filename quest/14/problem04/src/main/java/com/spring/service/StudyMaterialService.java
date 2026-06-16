package com.spring.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.StudyMaterial;
import com.spring.entity.StudyRecruit;
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

    public List<StudyMaterial> findByStudy(StudyRecruit study) {
        throw new UnsupportedOperationException("TODO problem04-23");
    }

    public StudyMaterial upload(Long studyId, MultipartFile file) {
        throw new UnsupportedOperationException("TODO problem04-24");
    }

    public StudyMaterial findMaterial(Long materialId) {
        throw new UnsupportedOperationException("TODO problem04-25");
    }

    public Resource loadAsResource(StudyMaterial material) {
        throw new UnsupportedOperationException("TODO problem04-25");
    }
}
