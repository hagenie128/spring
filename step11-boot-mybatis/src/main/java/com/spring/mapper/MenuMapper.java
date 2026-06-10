package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.dto.MenuDTO;

@Mapper
public interface MenuMapper {

    public List<MenuDTO> findAll();
    public List<MenuDTO> search(@Param("keyword") String keyword, 
                                @Param("category") String category,
                                @Param("available") Boolean available);
    public int deleteById(Long id);
    public MenuDTO findById(Long id);
    public void save(MenuDTO menu);
    public int update(MenuDTO menu);

}





