package com.spring.repository;

import com.spring.dto.MenuDTO;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 실제 데이터를 저장하고 조회하는 저장소 역할을 합니다.
 * 여기서는 데이터베이스 대신 메모리(Map)를 사용합니다.
 */
@Repository
public class MenuRepository {

    // 메모리 저장소: Key는 메뉴 ID(Long), Value는 MenuDTO 객체
    private final Map<Long, MenuDTO> store = new LinkedHashMap<>();
    
    // ID를 순차적으로 자동 부여하기 위한 시퀀스 (동시성 고려하여 AtomicLong 사용)
    private final AtomicLong sequence = new AtomicLong(6);

    // 저장소 생성 시 초기 샘플 데이터 등록
    public MenuRepository() {
        save(new MenuDTO(1L, "아메리카노", 3000, "커피", "시원한 블랙 커피", true));
        save(new MenuDTO(2L, "카페라떼", 4000, "커피", "부드러운 우유가 들어간 커피", true));
        save(new MenuDTO(3L, "카페모카", 5000, "커피", "달콤한 초코와 커피의 만남", true));
        save(new MenuDTO(4L, "녹차라떼", 4500, "음료", "진한 녹차의 향연", true));
        save(new MenuDTO(5L, "치즈케이크", 6000, "디저트", "입에서 녹는 치즈케이크", true));
    }

    /** 
     * 저장 및 업데이트 
     * ID가 없으면 신규 저장(ID 부여), ID가 있으면 기존 데이터 수정
     */
    public MenuDTO save(MenuDTO item) {
        if (item.getId() == null) {
            item.setId(sequence.getAndIncrement());
        }
        store.put(item.getId(), item);
        return item;
    }

    /** 전체 목록 조회 */
    public List<MenuDTO> findAll() {
        return new ArrayList<>(store.values());
    }

    /** 단건 조회 (Optional을 통해 null 처리 용이하게 함) */
    public Optional<MenuDTO> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /** 카테고리로 메뉴 필터링 조회 */
    public List<MenuDTO> findByCategory(String category) {
        return store.values().stream()
                .filter(m -> category.equals(m.getCategory()))
                .collect(Collectors.toList());
    }

    /** 판매 가능한 메뉴만 조회 */
    public List<MenuDTO> findByAvailable() {
        return store.values().stream()
                .filter(MenuDTO::isAvailable)
                .collect(Collectors.toList());
    }

    /** 메뉴 이름으로 포함 검색 */
    public List<MenuDTO> findByNameContaining(String keyword) {
        return store.values().stream()
                .filter(m -> m.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    /** 특정 ID의 메뉴 삭제 */
    public void deleteById(Long id) {
        store.remove(id);
    }
}
