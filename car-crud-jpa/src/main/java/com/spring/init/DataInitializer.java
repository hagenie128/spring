package com.spring.init;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spring.dto.CarDTO;
import com.spring.repository.CarRepository;

/**
 * 애플리케이션 시작 직후 실행되어 차량 샘플 데이터를 넣어주는 초기화 클래스입니다.
 * CommandLineRunner를 구현한 Bean은 Spring Boot 실행 완료 후 run 메서드가 자동 호출됩니다.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CarRepository repository;

    /**
     * 차량 데이터를 저장하기 위해 JPA Repository를 생성자 주입으로 받습니다.
     */
    public DataInitializer(CarRepository repository) {
        this.repository = repository;
    }

    /**
     * DB에 데이터가 없을 때만 샘플 차량 10건을 저장합니다.
     */
    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) {
            return;
        }

        repository.saveAll(List.of(
                new CarDTO(null, "Hyundai", "Avante", 2023, 15000, 25000000, null),
                new CarDTO(null, "Kia", "K5", 2022, 28000, 23000000, null),
                new CarDTO(null, "Genesis", "G80", 2021, 35000, 42000000, null),
                new CarDTO(null, "BMW", "320i", 2020, 48000, 36000000, null),
                new CarDTO(null, "Mercedes-Benz", "E300", 2019, 62000, 45000000, null),
                new CarDTO(null, "Tesla", "Model 3", 2024, 8000, 52000000, null),
                new CarDTO(null, "Toyota", "Camry", 2018, 76000, 19000000, null),
                new CarDTO(null, "Chevrolet", "Spark", 2017, 92000, 6500000, null),
                new CarDTO(null, "Hyundai", "Palisade", 2022, 41000, 39000000, null),
                new CarDTO(null, "Kia", "Carnival", 2021, 55000, 33000000, null)));

        System.out.println("[DataInitializer] 샘플 차량 10개 삽입 완료");
    }
}
