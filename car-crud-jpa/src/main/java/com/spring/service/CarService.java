package com.spring.service;

import com.spring.dto.CarDTO;
import com.spring.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 차량 관련 비즈니스 로직을 담당하는 Service 계층입니다.
 */
@Service
public class CarService {

    private final CarRepository repository;

    /**
     * JPA Repository를 생성자 주입으로 받아 DB 작업을 위임합니다.
     */
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    /**
     * 모든 차량을 조회합니다.
     */
    public List<CarDTO> findAll() {
        return repository.findAll();
    }

    /**
     * 신규 차량을 저장합니다. id가 null이면 INSERT로 동작합니다.
     */
    public void save(CarDTO car) {
        repository.save(car);
    }

    /**
     * ID로 차량을 조회합니다. 없으면 null을 반환합니다.
     */
    public CarDTO findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * 삭제 성공 여부를 숫자로 반환해 Controller에서 메시지를 구분할 수 있게 합니다.
     */
    public int deleteById(Integer id) {
        if (!repository.existsById(id)) {
            return 0;
        }
        repository.deleteById(id);
        return 1;
    }

    /**
     * 기존 차량을 수정합니다. 등록일은 최초 생성 시간을 유지하기 위해 기존 값을 다시 넣습니다.
     */
    public int edit(CarDTO car) {
        if (!repository.existsById(car.getCarId())) {
            return 0;
        }
        CarDTO savedCar = repository.findById(car.getCarId()).orElse(null);
        if (savedCar != null) {
            car.setRegisteredAt(savedCar.getRegisteredAt());
        }
        repository.save(car);
        return 1;
    }
}




