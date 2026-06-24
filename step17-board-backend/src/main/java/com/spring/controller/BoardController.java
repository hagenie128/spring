package com.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
import com.spring.entity.UserEntity;
import com.spring.service.BoardService;
import com.spring.vo.PaggingVO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  
  // 게시글 조회
  // 페이지 번호, 한 페이지당 게시글 개수, 검색어
  @GetMapping
  public ResponseEntity<Map<String,Object>> boardList(
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "20") int size
  ){
    List<BoardDTO> boardList = null;
    System.out.println(keyword.isBlank() + " " + keyword.length());
    if(keyword.isBlank() || keyword.length() == 0)
      boardList = boardService.getBoardList(page,size);
    else
      boardList = boardService.searchBoardList(keyword,page,size);

    int count = boardService.boardCount();
    PaggingVO paggingVO = new PaggingVO(count, page);

    Map<String,Object> map = new HashMap<String,Object>();
    map.put("list",boardList);
    map.put("pagging",paggingVO);

    return ResponseEntity.ok(map);
  }

  @GetMapping("/{bno}")
  public ResponseEntity<Map<String,Object>> boardContent(@PathVariable Long bno) {
    Map<String, Object> map = new HashMap<>();
    
    BoardDTO board = boardService.selectBoard(bno);
    List<BoardCommentDTO> commentList = boardService.selectBoardComment(bno);    
    map.put("board", board);
    map.put("commentList", commentList);
    return ResponseEntity.ok(map);
  }

  @PostMapping
  public ResponseEntity<Map<String,Object>> addBoard(
    @RequestBody BoardDTO board,
    @AuthenticationPrincipal UserEntity entity
  ) {
      Map<String, Object> map = new HashMap<>();
      map.put("board", board);
      map.put("user", entity);
      return ResponseEntity.ok(map);
  }
  
  
  

}




