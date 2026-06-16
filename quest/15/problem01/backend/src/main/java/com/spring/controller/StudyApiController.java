package com.spring.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.StudyDtos.ApplicationCreateRequest;
import com.spring.dto.StudyDtos.ReactionRequest;
import com.spring.dto.StudyDtos.StudyCreateRequest;
import com.spring.dto.StudyDtos.StudyUpdateRequest;
import com.spring.entity.RecruitStatus;
import com.spring.service.StudyApplicationService;
import com.spring.service.StudyMaterialService;
import com.spring.service.StudyReactionService;
import com.spring.service.StudyRecruitService;

@RestController
@RequestMapping("/api")
public class StudyApiController {

    private final StudyRecruitService studyRecruitService;
    private final StudyApplicationService applicationService;
    private final StudyReactionService reactionService;
    private final StudyMaterialService materialService;

    public StudyApiController(StudyRecruitService studyRecruitService,
                              StudyApplicationService applicationService,
                              StudyReactionService reactionService,
                              StudyMaterialService materialService) {
        this.studyRecruitService = studyRecruitService;
        this.applicationService = applicationService;
        this.reactionService = reactionService;
        this.materialService = materialService;
    }

    @GetMapping("/studies")
    public ResponseEntity<?> studies(@RequestParam(defaultValue = "") String keyword,
                                     @RequestParam(required = false) RecruitStatus status,
                                     Pageable pageable) {
        throw new UnsupportedOperationException("TODO backend-23");
    }

    @GetMapping("/studies/{id}")
    public ResponseEntity<?> study(@PathVariable Long id) {
        throw new UnsupportedOperationException("TODO backend-24");
    }

    @PostMapping("/studies")
    public ResponseEntity<?> create(@RequestBody StudyCreateRequest request) {
        throw new UnsupportedOperationException("TODO backend-25");
    }

    @PutMapping("/studies/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody StudyUpdateRequest request) {
        throw new UnsupportedOperationException("TODO backend-26");
    }

    @PostMapping("/studies/{id}/applications")
    public ResponseEntity<?> apply(@PathVariable Long id, @RequestBody ApplicationCreateRequest request) {
        throw new UnsupportedOperationException("TODO backend-27");
    }

    @PatchMapping("/applications/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable Long id) {
        throw new UnsupportedOperationException("TODO backend-28");
    }

    @PatchMapping("/applications/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        throw new UnsupportedOperationException("TODO backend-29");
    }

    @PostMapping("/studies/{id}/reactions")
    public ResponseEntity<?> reaction(@PathVariable Long id, @RequestBody ReactionRequest request) {
        throw new UnsupportedOperationException("TODO backend-30");
    }

    @PostMapping("/studies/{id}/materials")
    public ResponseEntity<?> upload(@PathVariable Long id, @RequestParam MultipartFile file) {
        throw new UnsupportedOperationException("TODO backend-31");
    }

    @GetMapping("/materials/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        throw new UnsupportedOperationException("TODO backend-32");
    }
}
