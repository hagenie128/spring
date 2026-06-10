package com.spring.mapper;

import com.spring.dto.CarDTO;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper {
    List<CarDTO> findAll();

    void save(@Valid CarDTO car);

    Object findById(Integer id);

    int deleteById(Integer id);

    int update(CarDTO car);
}
