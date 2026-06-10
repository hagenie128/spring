package com.spring.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spring.dto.MenuDTO;
import com.spring.repository.MenuRepository;

/**
 * 애플리케이션 시작 시점에 코드를 실행하여 기초 데이터를 DB에 넣어주는 역할을 합니다.
 * CommandLineRunner 인터페이스를 구현하면 run 메서드가 자동으로 호출됩니다.
 */
@Component // Spring Bean으로 등록하여 자동 실행되도록 함
public class DataInitailizer implements CommandLineRunner{
	
	private final MenuRepository repository;
	
	/**
	 * 생성자 주입
	 */
	public DataInitailizer(MenuRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * 애플리케이션 구동 직후 실행되는 로직입니다.
	 */
	@Override
	public void run(String... args) throws Exception {
		// 이미 데이터가 존재하면 중복 삽입을 방지하기 위해 종료
		if (repository.count() > 0) {
            return; // 이미 데이터가 있으면 스킵
        }

		// 초기 샘플 데이터 12개 생성 및 저장
        repository.save(new MenuDTO("불고기버거", 5500, "버거", "국내산 불고기 패티를 사용한 대표 버거", true));
        repository.save(new MenuDTO("치즈버거", 4900, "버거", "진한 체다치즈가 들어간 클래식 버거", true));
        repository.save(new MenuDTO("새우버거", 5200, "버거", "통새우 패티 버거", true));
        repository.save(new MenuDTO("베이컨버거", 6200, "버거", "바삭한 베이컨과 패티의 조합", false));
        repository.save(new MenuDTO("콜라", 2000, "음료", "코카콜라 355ml", true));
        repository.save(new MenuDTO("제로콜라", 2000, "음료", "제로칼로리 콜라 355ml", true));
        repository.save(new MenuDTO("오렌지주스", 2500, "음료", "100% 착즙 오렌지주스", true));
        repository.save(new MenuDTO("아메리카노", 2300, "음료", "진한 에스프레소 아메리카노", true));
        repository.save(new MenuDTO("감자튀김", 2000, "사이드", "바삭한 황금 감자튀김", true));
        repository.save(new MenuDTO("치킨너겟", 3000, "사이드", "바삭하고 촉촉한 치킨너겟 6개", true));
        repository.save(new MenuDTO("양파링", 2500, "사이드", "바삭한 양파링", false));
        repository.save(new MenuDTO("콘샐러드", 2200, "사이드", "달콤한 옥수수 샐러드", true));

        System.out.println("[DataInitializer] 샘플 메뉴 12개 삽입 완료");		
	}
}
