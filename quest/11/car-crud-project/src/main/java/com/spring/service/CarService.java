package com.spring.service;

import com.spring.dto.CarDTO;
import com.spring.mapper.CarMapper;
import jakarta.validation.Valid;
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

    public void save(@Valid CarDTO car) {
        mapper.save(car);
    }

    public Object findById(Integer id) {
        return mapper.findById(id);
    }

    public int deleteById(Integer id) {
       return mapper.deleteById(id);
    }

    public int update(CarDTO car) {
        return mapper.update(car);
    }
}




