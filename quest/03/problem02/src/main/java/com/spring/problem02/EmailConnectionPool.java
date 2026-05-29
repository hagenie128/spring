package com.spring.problem02;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * 이메일 발송 서비스용 커넥션 풀.
 *
 * TODO 1: 클래스에 @Component 어노테이션을 추가하세요.
 *
 * TODO 2: initPool() 메서드에 @PostConstruct 어노테이션을 추가하세요.
 *         커넥션 3개 생성 로그를 출력해야 합니다.
 *         예) [EmailConnectionPool] initPool() — conn-1 생성
 *             [EmailConnectionPool] initPool() — conn-2 생성
 *             [EmailConnectionPool] initPool() — conn-3 생성
 *             [EmailConnectionPool] initPool() — 커넥션 3개 생성 완료
 *
 * TODO 3: closePool() 메서드에 @PreDestroy 어노테이션을 추가하세요.
 *         커넥션 3개 닫기 로그를 출력해야 합니다.
 *         예) [EmailConnectionPool] closePool() — conn-1 닫기
 *             [EmailConnectionPool] closePool() — conn-2 닫기
 *             [EmailConnectionPool] closePool() — conn-3 닫기
 *             [EmailConnectionPool] closePool() — 커넥션 3개 닫기 완료
 */
// TODO 1: @Component 추가
@Component
public class EmailConnectionPool {

    private static final int POOL_SIZE = 3;

    public EmailConnectionPool() {
        System.out.println("[EmailConnectionPool] 생성자 호출");
    }

    // TODO 2: @PostConstruct 추가
    @PostConstruct
    public void initPool() {
        for (int i = 1; i <= POOL_SIZE; i++) {
            System.out.println("[EmailConnectionPool] initPool() — conn-" + i + " 생성");
        }
        System.out.println("[EmailConnectionPool] initPool() — 커넥션 " + POOL_SIZE + "개 생성 완료");
    }

    // TODO 3: @PreDestroy 추가
    @PreDestroy
    public void closePool() {
        for (int i = 1; i <= POOL_SIZE; i++) {
            System.out.println("[EmailConnectionPool] closePool() — conn-" + i + " 닫기");
        }
        System.out.println("[EmailConnectionPool] closePool() — 커넥션 " + POOL_SIZE + "개 닫기 완료");
    }

    public void sendEmail(String to) {
        System.out.println("[EmailConnectionPool] 이메일 발송: to=" + to);
    }
}
