package com.spring.service;

import com.spring.dto.CarDTO;
import com.spring.mapper.CarMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarMapper mapper;

    public CarService(CarMapper mapper) {
        this.mapper = mapper;
    }

    public List<CarDTO> findAll() {
        return mapper.findAll();
    }
}




