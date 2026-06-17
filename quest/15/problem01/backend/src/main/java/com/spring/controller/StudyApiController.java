package com.spring.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import com.spring.dto.StudyDtos.StudyDetailResponse;
import com.spring.dto.StudyDtos.StudyListResponse;
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
    public ResponseEntity<Page<StudyListResponse>> studies(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) RecruitStatus status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        // TODO backend-23: 목록 검색 API를 구현하세요. (keyword, status, pageable → Page<StudyListResponse>)
        throw new UnsupportedOperationException("TODO backend-23");
    }

    @GetMapping("/studies/{id}")
    public ResponseEntity<StudyDetailResponse> study(@PathVariable Long id) {
        // TODO backend-24: 상세 조회 API를 구현하세요. (id → StudyDetailResponse)
        throw new UnsupportedOperationException("TODO backend-24");
    }

    @PostMapping("/studies")
    public ResponseEntity<?> create(@RequestBody StudyCreateRequest request) {
        // TODO backend-25: 모집글 등록 API를 구현하세요. (201 Created + { "id": 새 ID })
        throw new UnsupportedOperationException("TODO backend-25");
    }

    @PutMapping("/studies/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody StudyUpdateRequest request) {
        // TODO backend-26: 모집글 수정 API를 구현하세요. (200 OK + { "message": "수정이 완료됐습니다." })
        throw new UnsupportedOperationException("TODO backend-26");
    }

    @PostMapping("/studies/{id}/applications")
    public ResponseEntity<?> apply(@PathVariable Long id, @RequestBody ApplicationCreateRequest request) {
        // TODO backend-27: 스터디 신청 API를 구현하세요. (201 Created + { "message": "신청이 완료됐습니다." })
        throw new UnsupportedOperationException("TODO backend-27");
    }

    @PatchMapping("/applications/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable Long id) {
        // TODO backend-28: 신청 합격 처리 API를 구현하세요. (200 OK + { "message": "합격 처리됐습니다." })
        throw new UnsupportedOperationException("TODO backend-28");
    }

    @PatchMapping("/applications/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        // TODO backend-29: 신청 거절 처리 API를 구현하세요. (200 OK + { "message": "거절 처리됐습니다." })
        throw new UnsupportedOperationException("TODO backend-29");
    }

    @PostMapping("/studies/{id}/reactions")
    public ResponseEntity<?> reaction(@PathVariable Long id, @RequestBody ReactionRequest request) {
        // TODO backend-30: 좋아요/싫어요 토글 API를 구현하세요. (200 OK + { "message": "반응이 처리됐습니다." })
        throw new UnsupportedOperationException("TODO backend-30");
    }

    @PostMapping("/studies/{id}/materials")
    public ResponseEntity<?> upload(@PathVariable Long id, @RequestParam MultipartFile file) {
        // TODO backend-31: 파일 업로드 API를 구현하세요. (201 Created + { "message": "파일이 업로드됐습니다." })
        throw new UnsupportedOperationException("TODO backend-31");
    }

    @GetMapping("/materials/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        // TODO backend-32: 파일 다운로드 API를 구현하세요.
        // 힌트:
        //   1. materialService.findById(id)로 파일 정보(원본 파일명)를 가져오세요.
        //   2. materialService.download(id)로 Resource를 가져오세요.
        //   3. 한글 파일명 인코딩: URLEncoder.encode(originalName, StandardCharsets.UTF_8).replace("+", "%20")
        //   4. 응답 헤더: HttpHeaders.CONTENT_DISPOSITION → "attachment; filename*=UTF-8''인코딩된파일명"
        throw new UnsupportedOperationException("TODO backend-32");
    }
}
