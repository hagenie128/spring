package com.spring.problem04;

import org.springframework.stereotype.Service;

// TODO [도전 4-3] 이 클래스를 서비스 레이어 Bean으로 등록하는 어노테이션을 추가하세요.
// @Service는 카페 서비스처럼 비즈니스 기능을 담당하는 클래스를 Bean으로 등록합니다.
@Service
public class CafeService {

    public void printWelcome() {
        // 카페 시작 메시지를 출력하는 단순 서비스 메서드입니다.
        System.out.println("[CafeService] ☕ Spring Cafe에 오신 것을 환영합니다!");
    }
}