package com.spring.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 게시판 CRUD 컨트롤러
 * URL 설계:
 *   GET  /board/list          목록
 *   GET  /board/write         등록 폼
 *   POST /board/write         등록 처리
 *   GET  /board/detail?id=N   상세
 *   POST /board/delete?id=N   삭제
 *
 * ※ /board/** 경로는 LoginCheckInterceptor가 적용됨 → 로그인 필요
 */
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;

    // 생성자 주입
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * GET /board/list → 게시글 목록
     */
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boards", boardRepository.findAll());
        return "board/list";
    }

    /**
     * GET /board/write → 등록 폼
     */
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    /**
     * POST /board/write → 등록 처리 후 목록으로 리다이렉트
     */
    @PostMapping("/write")
    public String write(@RequestParam String title,
                        @RequestParam String content,
                        @RequestParam String author) {
        boardRepository.save(new Board(null, title, content, author, null));
        return "redirect:/board/list";
    }

    /**
     * GET /board/detail?id=N → 상세 조회
     */
    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        model.addAttribute("board", boardRepository.findById(id));
        return "board/detail";
    }

    /**
     * POST /board/delete?id=N → 삭제 후 목록으로 리다이렉트
     */
    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        boardRepository.deleteById(id);
        return "redirect:/board/list";
    }
}
