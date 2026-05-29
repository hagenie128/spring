package com.spring.life_cycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

//Spring 에 종속
public class NetworkService implements InitializingBean, DisposableBean{
	
	
	
	public NetworkService() {
		System.out.println("[NetworkService] 생성자 - 인스턴스화");
	}

	@Override
	public void destroy() throws Exception{
		System.out.println("[NetworkService] destroy() - 서버연결 종료");
		
	}
	
	@Override
	public void afterPropertiesSet() throws Exception{
		System.out.println("[NetworkService] afterPropertiesSet() - 서버연결");
	}
}
