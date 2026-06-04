package com.spring.problem02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 대시보드 컨트롤러 [완성된 파일 — 수정 불필요]
 * AdminCheckInterceptor에 의해 /dashboard/** 경로는 로그인 체크 적용
 */
@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
