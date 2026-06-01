package com.spring.life_cycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LifecycleMainApp {

	public static void main(String[] args) {
		System.out.println("▶ ApplicationContext 생성 시작\n");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-lifecycle.xml");

		// DatabaseConnctor 생성
		DatabaseConnector connector1 = context.getBean(DatabaseConnector.class);
		connector1.query("select * from member");
		System.out.println("\n▶ 컨테이너 종료(close()) 시작\n");
		//소멸될 때 호출되는 메서드는 scope가 싱글톤일때만 호출
		//prototype은 호출이 안됨 - IoC가 생성 후에 관리를 안함
		((ClassPathXmlApplicationContext) context).close();
	}

}
