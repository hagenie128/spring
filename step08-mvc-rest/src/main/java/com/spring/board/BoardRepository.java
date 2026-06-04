package com.spring.board;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 게시판 in-memory 저장소
 * AtomicLong으로 ID를 스레드 안전하게 생성
 */
@Component
public class BoardRepository {

    private final List<Board> boards = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(1);

    // 생성자: 샘플 데이터 3개 추가
    public BoardRepository() {
        save(new Board(null, "첫 번째 글", "안녕하세요.", "admin", null));
        save(new Board(null, "Spring MVC 공부", "재밌네요!", "user01", null));
        save(new Board(null, "게시판 테스트", "잘 작동합니다.", "user02", null));
    }

    /**
     * 게시글 저장 (ID 자동 생성, createdAt 설정)
     */
    public Board save(Board board) {
        board.setId(idGen.getAndIncrement());
        board.setCreatedAt(LocalDateTime.now().toString().substring(0, 16));
        boards.add(board);
        return board;
    }

    /**
     * 전체 목록 반환 (방어적 복사)
     */
    public List<Board> findAll() {
        return new ArrayList<>(boards);
    }

    /**
     * ID로 단건 조회
     */
    public Board findById(Long id) {
        return boards.stream()
            .filter(b -> b.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    /**
     * ID로 삭제
     */
    public boolean deleteById(Long id) {
        return boards.removeIf(b -> b.getId().equals(id));
    }
}
