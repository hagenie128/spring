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

    @GetMapping
    public ModelAndView list(@RequestParam(defaultValue = "") String keyword,
                             @RequestParam(required = false) RecruitStatus status,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "9") int size) {
        ModelAndView view = new ModelAndView("study/list");
        // TODO problem01: 목록 화면에 필요한 데이터를 담으세요.
        return view;
    }

    @GetMapping("/new")
    public String form() {
        // TODO problem02: 등록 화면을 반환하세요.
        return "study/form";
    }

    @PostMapping
    public String create(@RequestParam Long leaderId,
                         @RequestParam String title,
                         @RequestParam String description,
                         @RequestParam String techStack,
                         @RequestParam String method,
                         @RequestParam Integer capacity) {
        throw new UnsupportedOperationException("TODO problem02");
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView view = new ModelAndView("study/detail");
        // TODO problem04: 상세 화면에 필요한 데이터를 담으세요.
        return view;
    }

    @PostMapping("/{id}/applications")
    public String apply(@PathVariable Long id,
                        @RequestParam Long applicantId,
                        @RequestParam String message) {
        throw new UnsupportedOperationException("TODO problem03");
    }

    @PostMapping("/applications/{applicationId}/accept")
    public String accept(@RequestParam Long studyId, @PathVariable Long applicationId) {
        throw new UnsupportedOperationException("TODO problem03");
    }

    @PostMapping("/applications/{applicationId}/reject")
    public String reject(@RequestParam Long studyId, @PathVariable Long applicationId) {
        throw new UnsupportedOperationException("TODO problem03");
    }

    @PostMapping("/{id}/interest")
    public String toggleInterest(@PathVariable Long id, @RequestParam Long memberId) {
        throw new UnsupportedOperationException("TODO problem04");
    }
}
