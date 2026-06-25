package com.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
import com.spring.dto.BoardReactionReq;
import com.spring.dto.ReactionCountDTO;
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
  public ResponseEntity<Map<String,Object>> boardContent(@PathVariable int bno) {
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

      board.setMid(entity.getId());
      boardService.addBoard(board);

      return ResponseEntity.ok(map);
  }
  
  //게시글 삭제
  // 게시글 글번호 받아서 삭제
  // 게시글 작성자 == 로그인한 회원
  @DeleteMapping("/{bno}")
  public ResponseEntity<Map<String,Object>> deleteBoard(@PathVariable int bno,
    @AuthenticationPrincipal UserEntity userEntity
  ){
    Map<String, Object> map = new HashMap<>();
    //해당 게시글 가져옴
    BoardDTO board = boardService.selectBoard(bno);

    if(board == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    } 

    if(board.getMid() != userEntity.getId()){
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
    }
    boardService.deleteBoard(bno);
    // 응답이 필요없을때
    return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      // return ResponseEntity.status(HttpStatus.OK).body(map);
  }

  @PatchMapping("/{bno}")
  public ResponseEntity updateBoard(@PathVariable int bno, @RequestBody BoardDTO reqBoard, @AuthenticationPrincipal UserEntity userEntity){
    BoardDTO board = boardService.selectBoard(bno);
    
    if(board == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 

    if(board.getMid() != userEntity.getId()){
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    reqBoard.setBno(bno);
    boardService.updateBoard(reqBoard);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  // 게시글 좋아요 싫어요
  @PostMapping("/reaction")
  public ResponseEntity<Map<String,Object>> boardReaction(@RequestBody BoardReactionReq reactionReq, @AuthenticationPrincipal UserEntity userEntity) {
      Map<String, Object> map = new HashMap<>();
      BoardReactionReq req = boardService.selectBoardReaction(reactionReq.getBno(), userEntity.getId());
      
      if(req == null){
        reactionReq.setMid(userEntity.getId());
        boardService.addBoardReaction(reactionReq);
      }else{
        reactionReq.setId(req.getId());
        if(reactionReq.getType().equals(req.getType())){
          boardService.deleteBoardReaction(reactionReq);
        }else{
          boardService.updateBoardReaction(reactionReq);
        }
      }

      ReactionCountDTO reactionCount = boardService.getBoardReactionCount(reactionReq.getBno());

      map.put("count", reactionCount);
      return ResponseEntity.ok(map);
  }

  
}




