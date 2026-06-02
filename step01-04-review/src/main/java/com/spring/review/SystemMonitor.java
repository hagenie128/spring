package com.spring.review;

public class SystemMonitor {

	public SystemMonitor() {
		System.out.println("[SystemMonitor] 생성자 호출");
	}

	public void start() {
		System.out.println("[SystemMonitor] init-method - 모니터링 시작");
	}

	public void stop() {
		System.out.println("[SystemMonitor] destroy-method - 모니터링 종료");
	}
}
