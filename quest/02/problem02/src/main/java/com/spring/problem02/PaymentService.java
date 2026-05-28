package com.spring.problem02;

import org.springframework.stereotype.Service;

// TODO [문제 2-2] 이 클래스를 서비스 레이어 Bean으로 등록하는 어노테이션을 추가하세요.
//  힌트: @Service
// @Service는 비즈니스 로직을 담당하는 클래스라는 뜻입니다.
// 예제에서는 결제 금액을 받아 "결제 처리" 흐름을 출력합니다.
@Service
public class PaymentService {

    public void processPayment(int amount) {
        // 실제 결제 API 호출 대신 콘솔 출력으로 처리 과정을 확인합니다.
        System.out.println("[PaymentService] 결제 처리 시작: " + amount + "원");
    }
}