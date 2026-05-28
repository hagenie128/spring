package com.spring.problem02;

import org.springframework.stereotype.Repository;

// TODO [문제 2-1] 이 클래스를 데이터 접근 레이어 Bean으로 등록하는 어노테이션을 추가하세요.
//  힌트: @Repository
// @Repository는 저장소/DB 접근 역할의 클래스를 Spring Bean으로 등록합니다.
// component-scan이 이 어노테이션을 발견하면 paymentRepository라는 Bean 이름으로 관리합니다.
@Repository
public class PaymentRepository {

    public void save(int paymentId) {
        // 실제 DB 저장 대신 콘솔 출력으로 저장 동작을 흉내 냅니다.
        System.out.println("[PaymentRepository] 결제 내역 저장 완료 (id=" + paymentId + ")");
    }
}