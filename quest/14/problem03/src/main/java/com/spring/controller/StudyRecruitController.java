package com.spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.entity.RecruitStatus;
import com.spring.entity.StudyRecruit;
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
                         @RequestParam Integer capacity,
                         RedirectAttributes redirectAttributes) {
        StudyRecruit study = studyRecruitService.create(leaderId, title, description, techStack, method, capacity);
        redirectAttributes.addFlashAttribute("message", "스터디 모집글이 등록되었습니다.");
        return "redirect:/studies/" + study.getId();
    }

    // TODO problem04-12,13: 상세 화면 model 구성
    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        StudyRecruit study = studyRecruitService.getDetail(id);

        ModelAndView view = new ModelAndView("study/detail");
        view.addObject("study", study);
        view.addObject("waitingApplications", applicationService.getWaitingApplications(study));
        view.addObject("acceptedApplications", applicationService.getAcceptedApplications(study));
        view.addObject("interestCount", interestService.count(study));
        return view;
    }

    // TODO problem03-14: 신청 POST 핸들러
    @PostMapping("/{id}/applications")
    public String apply(@PathVariable Long id,
                        @RequestParam Long applicantId,
                        @RequestParam String message) {
        applicationService.apply(id, applicantId, message);
        return "redirect:/studies/" + id;
    }

    // TODO problem03-15: 합격 POST 핸들러
    @PostMapping("/applications/{applicationId}/accept")
    public String accept(@RequestParam Long studyId, @PathVariable Long applicationId) {
        applicationService.accept(applicationId);
        return "redirect:/studies/" + studyId;
    }

    // TODO problem03-15: 거절 POST 핸들러
    @PostMapping("/applications/{applicationId}/reject")
    public String reject(@RequestParam Long studyId, @PathVariable Long applicationId) {
        applicationService.reject(applicationId);
        return "redirect:/studies/" + studyId;
    }

    // TODO problem04-14: 관심 토글 POST 핸들러
    @PostMapping("/{id}/interest")
    public String toggleInterest(@PathVariable Long id, @RequestParam Long memberId) {
        interestService.toggle(id, memberId);
        return "redirect:/studies/" + id;
    }
}
