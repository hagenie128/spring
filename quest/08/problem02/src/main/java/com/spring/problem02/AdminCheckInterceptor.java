package com.spring.problem02;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminCheckInterceptor implements HandlerInterceptor {

    // TODO 1: preHandle 메서드를 오버라이드하라.
    //   - 세션에서 "adminUser" 속성을 꺼낸다.
    //   - "adminUser"가 null이면:
    //       1) sysout "[AdminCheckInterceptor] 미인증 접근 차단"
    //       2) response.sendRedirect(request.getContextPath() + "/login") 호출
    //       3) return false
    //   - "adminUser"가 있으면:
    //       1) sysout "[AdminCheckInterceptor] 인증 확인: " + adminUser
    //       2) return true

}
