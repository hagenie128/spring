package com.spring.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.BoardDTO;
import com.spring.entity.UserEntity;
import com.spring.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> boardList(
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int size) {

    // TODO 5:
    // 1. keyword 유무에 따라 일반 목록 또는 검색 목록을 조회하세요.
    // 2. 검색일 때는 검색 결과 개수에 맞는 count 쿼리를 사용하세요.
    // 3. 요청 size와 같은 크기로 페이징 정보를 계산하세요.
    // 4. list와 pagging을 Map에 담아 응답하세요.
    throw new UnsupportedOperationException("TODO 5");
  }

  @GetMapping("/{bno}")
  public ResponseEntity<Map<String, Object>> boardContent(@PathVariable Long bno) {
    // TODO 6: board와 commentList를 함께 응답하세요.
    throw new UnsupportedOperationException("TODO 6");
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> addBoard(
      @RequestBody BoardDTO board,
      @AuthenticationPrincipal UserEntity currentUser) {

    // TODO 7:
    // 요청 JSON의 작성자 값은 신뢰하지 말고 currentUser의 id를 작성자로 사용해 저장하세요.
    throw new UnsupportedOperationException("TODO 7");
  }
}
