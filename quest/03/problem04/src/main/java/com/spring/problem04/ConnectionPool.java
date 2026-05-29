package com.spring.problem04;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * DB 커넥션 풀 — InitializingBean / DisposableBean 구현 도전 과제.
 *
 * TODO 1: InitializingBean, DisposableBean 인터페이스를 implements 하세요.
 *
 * TODO 2: afterPropertiesSet() 구현
 *         - poolSize 개수만큼 커넥션 ID 생성 ("conn-1" ~ "conn-N")
 *         - availableConnections Deque 에 추가
 *         - 예) [ConnectionPool] afterPropertiesSet() — 커넥션 5개 생성: [conn-1, ..., conn-5]
 *
 * TODO 3: destroy() 구현
 *         - allConnections 리스트를 순회하며 닫기 로그 출력
 *         - 예) [ConnectionPool] destroy() — 커넥션 5개 닫기 완료
 *
 * TODO 4: getConnection() 구현
 *         - availableConnections 가 비어 있으면 "풀 고갈" 출력 후 null 반환
 *         - 비어 있지 않으면 pollFirst() 로 커넥션 꺼내어 반환
 *         - 예) [ConnectionPool] getConnection() → conn-1
 *
 * TODO 5: releaseConnection(String connId) 구현
 *         - availableConnections 에 connId 를 addLast() 로 반납
 *         - 예) [ConnectionPool] releaseConnection(conn-1) — 반납 완료
 */
// TODO 1: implements InitializingBean, DisposableBean 추가
public class ConnectionPool implements InitializingBean, DisposableBean{

    private int    poolSize = 3; // 기본값; XML에서 setter injection으로 오버라이드
    private final Deque<String> availableConnections = new ArrayDeque<>();
    private final List<String>  allConnections       = new ArrayList<>();

    public ConnectionPool() {
        System.out.println("[ConnectionPool] 생성자 — poolSize=" + poolSize + "(기본값)");
    }

    // TODO 2: afterPropertiesSet() 구현
    // @Override
    // public void afterPropertiesSet() throws Exception { ... }
    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 1; i <= poolSize; i++) {
            String connId = "conn-" + i;
        	availableConnections.addLast(connId);
            allConnections.add(connId);
        }
    	System.out.println("[ConnectionPool] afterPropertiesSet() — 커넥션 "+poolSize+"개 생성: [conn-1, ..., conn-"+poolSize+"]");
    }

    // TODO 3: destroy() 구현
    // @Override
    // public void destroy() throws Exception { ... }
    @Override
    public void destroy() throws Exception{
    	for(String connId : allConnections) {
    		System.out.println("[ConnectionPool] destroy() — " + connId + " 닫기");
    	}
    	System.out.println("[ConnectionPool] destroy() — 커넥션 " + allConnections.size() + "개 닫기 완료");
    }

    // TODO 4: getConnection() 구현
    public String getConnection() {
        if(availableConnections.isEmpty()){
        	System.out.println("풀 고갈");
        	return null;
        }else {
            String connId = availableConnections.pollFirst();
        	System.out.println("[ConnectionPool] getConnection() → " + connId);
        	return connId;
        }
    }

    // TODO 5: releaseConnection() 구현
    public void releaseConnection(String connId) {
        availableConnections.addLast(connId);
        System.out.println("[ConnectionPool] releaseConnection("+connId+") — 반납 완료");
    }

    public void setPoolSize(int poolSize) {
        System.out.println("[ConnectionPool] setPoolSize(" + poolSize + ") — XML 주입");
        this.poolSize = poolSize;
    }

    public int getPoolSize() { return poolSize; }
}
