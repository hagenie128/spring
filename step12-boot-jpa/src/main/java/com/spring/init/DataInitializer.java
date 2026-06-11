package com.spring.init;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spring.dto.MenuDTO;
import com.spring.repository.MenuRepository;

/**
 * 애플리케이션 시작 시점에 샘플 메뉴 데이터를 DB에 넣어주는 초기화 클래스입니다.
 * CommandLineRunner를 구현한 Bean은 Spring Boot가 실행된 직후 run 메서드가 자동 호출됩니다.
 */
@Component
public class DataInitializer implements CommandLineRunner {

	private final MenuRepository repository;

	/**
	 * 샘플 데이터를 저장하기 위해 JPA Repository를 생성자 주입으로 받습니다.
	 */
	public DataInitializer(MenuRepository repository) {
		this.repository = repository;
	}

	/**
	 * DB에 기존 데이터가 없을 때만 샘플 메뉴 12건을 저장합니다.
	 */
	@Override
	public void run(String... args) throws Exception {
		if (repository.count() > 0) {
			return;
		}

		repository.saveAll(List.of(
				new MenuDTO("불고기버거", 5500, "버거", "국내산 불고기 패티를 사용한 대표 버거", true),
				new MenuDTO("치즈버거", 4900, "버거", "진한 체다치즈가 들어간 클래식 버거", true),
				new MenuDTO("새우버거", 5200, "버거", "통새우 패티 버거", true),
				new MenuDTO("베이컨버거", 6200, "버거", "바삭한 베이컨과 패티의 조합", false),
				new MenuDTO("콜라", 2000, "음료", "코카콜라 355ml", true),
				new MenuDTO("제로콜라", 2000, "음료", "제로칼로리 콜라 355ml", true),
				new MenuDTO("오렌지주스", 2500, "음료", "100% 착즙 오렌지주스", true),
				new MenuDTO("아메리카노", 2300, "음료", "진한 에스프레소 아메리카노", true),
				new MenuDTO("감자튀김", 2000, "사이드", "바삭한 황금 감자튀김", true),
				new MenuDTO("치킨너겟", 3000, "사이드", "바삭하고 촉촉한 치킨너겟 6개", true),
				new MenuDTO("양파링", 2500, "사이드", "바삭한 양파링", false),
				new MenuDTO("콘샐러드", 2200, "사이드", "달콤한 옥수수 샐러드", true)));

		System.out.println("[DataInitializer] 샘플 메뉴 12개 삽입 완료");
	}
}
