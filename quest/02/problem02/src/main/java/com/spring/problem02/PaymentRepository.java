package com.spring.problem02;

// TODO [문제 2-1] 이 클래스를 데이터 접근 레이어 Bean으로 등록하는 어노테이션을 추가하세요.
//  힌트: @Repository
public class PaymentRepository {

    public void save(int paymentId) {
        System.out.println("[PaymentRepository] 결제 내역 저장 완료 (id=" + paymentId + ")");
    }
}
