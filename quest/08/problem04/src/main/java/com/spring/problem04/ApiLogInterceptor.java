package com.spring.problem04;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// TODO 1: HandlerInterceptor 구현
// TODO 2: preHandle에서 sysout "[ApiLog] 요청: " + request.getMethod() + " " + request.getRequestURI() 후 return true
// TODO 3: afterCompletion에서 sysout "[ApiLog] 완료: " + request.getRequestURI()
public class ApiLogInterceptor implements HandlerInterceptor{
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("[ApiLog] 요청: " + request.getMethod() + " " + request.getRequestURI());
		return true;
	}
    // TODO: HandlerInterceptor를 구현하고 preHandle, afterCompletion 메서드를 작성하라.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("[ApiLog] 완료: " + request.getRequestURI());
	}
}
