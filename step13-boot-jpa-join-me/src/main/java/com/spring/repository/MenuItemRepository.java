package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByOrderByNameAsc();

    List<MenuItem> findByCategory(String category);

    List<MenuItem> findByAvailableTrue();
    
}


