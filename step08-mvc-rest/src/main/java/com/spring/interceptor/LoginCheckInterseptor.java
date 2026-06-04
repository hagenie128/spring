package com.spring.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginCheckInterseptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object user = request.getSession().getAttribute("loginUser");
		if(user==null) {
			//비로그인 사용자
			//요청처리 중단, 로그인 페이지로 리다이렉트
			System.out.println("비로그인 사용자의 요청");
			response.sendRedirect(request.getContextPath()+"/login");
			return false;//요청처리 중단
		}
		System.out.println(user+"로그인 확인");
		return true;//요청 처리 계속
	}
}
