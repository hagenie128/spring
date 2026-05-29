package com.spring.setter;

import java.util.List;

public class MenuRepository {
    public List<String> findAll() {
        return List.of("아메리카노", "카페라떼", "바닐라라떼");
    }
}
