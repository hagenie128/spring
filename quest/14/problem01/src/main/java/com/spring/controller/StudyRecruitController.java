package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.entity.RecruitStatus;
import com.spring.service.StudyApplicationService;
import com.spring.service.StudyInterestService;
import com.spring.service.StudyRecruitService;

@Controller
@RequestMapping("/studies")
public class StudyRecruitController {

    private final StudyRecruitService studyRecruitService;
    private final StudyApplicationService applicationService;
    private final StudyInterestService interestService;

    public StudyRecruitController(StudyRecruitService studyRecruitService,
                                  StudyApplicationService applicationService,
                                  StudyInterestService interestService) {
        this.studyRecruitService = studyRecruitService;
        this.applicationService = applicationService;
        this.interestService = interestService;
    }

    // TODO problem01-9,10: keyword/status/page/size 파라미터와 model 구성
    @GetMapping
    public ModelAndView list(@RequestParam(defaultValue = "") String keyword,
                             @RequestParam(required = false) RecruitStatus status,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "9") int size) {
        ModelAndView view = new ModelAndView("study/list");
        // TODO problem01-9,10: 목록 조회에 필요한 데이터들을 model에 담으세요.
        return view;
    }

    // TODO problem02-9: GET /studies/new
    @GetMapping("/new")
    public String form() {
        return "study/form";
    }

    // TODO problem02-10: POST /studies
    @PostMapping
    public String create(@RequestParam Long leaderId,
                         @RequestParam String title,
                         @RequestParam String description,
                         @RequestParam String techStack,
                         @RequestParam String method,
                         @RequestParam Integer capacity) {
        throw new UnsupportedOperationException("TODO problem02-10");
    }

    // TODO problem04-12,13: 상세 화면 model 구성
    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView view = new ModelAndView("study/detail");
        // TODO problem04-12,13
        return view;
    }

    // TODO problem03-14: 신청 POST 핸들러
    @PostMapping("/{id}/applications")
    public String apply(@PathVariable Long id,
                        @RequestParam Long applicantId,
                        @RequestParam String message) {
        throw new UnsupportedOperationException("TODO problem03-14");
    }

    // TODO problem03-15: 합격 POST 핸들러
    @PostMapping("/applications/{applicationId}/accept")
    public String accept(@RequestParam Long studyId, @PathVariable Long applicationId) {
        throw new UnsupportedOperationException("TODO problem03-15");
    }

    // TODO problem03-15: 거절 POST 핸들러
    @PostMapping("/applications/{applicationId}/reject")
    public String reject(@RequestParam Long studyId, @PathVariable Long applicationId) {
        throw new UnsupportedOperationException("TODO problem03-15");
    }

    // TODO problem04-14: 관심 토글 POST 핸들러
    @PostMapping("/{id}/interest")
    public String toggleInterest(@PathVariable Long id, @RequestParam Long memberId) {
        throw new UnsupportedOperationException("TODO problem04-14");
    }
}
