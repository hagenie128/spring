package com.spring.mapper;

import com.spring.dto.CarDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper {
    List<CarDTO> findAll();
}
