package com.spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardCommentReactionReq;
import com.spring.dto.BoardDTO;
import com.spring.dto.BoardReactionReq;
import com.spring.dto.ReactionCountDTO;
import com.spring.entity.UserEntity;
import com.spring.service.BoardCommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class BoardCommentController {

  private final BoardCommentService boardCommentService;

  //댓글 추가
  // 사용자 ID, 댓글 내용, 글번호
  @PostMapping
  public ResponseEntity addBoardComment(
    @RequestBody BoardCommentDTO comment,
    @AuthenticationPrincipal UserEntity entity
  ) {
      comment.setMid(entity.getId());
      boardCommentService.addBoardComment(comment);

      return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // 댓글 삭제
  @DeleteMapping("/{cno}")
  public ResponseEntity<Map<String,Object>> deleteBoardComment(@PathVariable int cno,
    @AuthenticationPrincipal UserEntity userEntity
  ){
    Map<String, Object> map = new HashMap<>();
    //해당 게시글 가져옴
    BoardCommentDTO comment = boardCommentService.selectBoardComment(cno);

    if(comment == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    } 

    if(comment.getMid() != userEntity.getId()){
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
    }
    boardCommentService.deleteBoardComment(cno);
    // 응답이 필요없을때
    return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/{cno}")
  public ResponseEntity updateBoard(@PathVariable int cno, @RequestBody BoardCommentDTO reqBoard, @AuthenticationPrincipal UserEntity userEntity){
    BoardCommentDTO comment = boardCommentService.selectBoardComment(cno);
    
    if(comment == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 

    if(comment.getMid() != userEntity.getId()){
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    reqBoard.setCno(cno);
    boardCommentService.updateBoardComment(reqBoard);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("/reaction")
  public ResponseEntity<Map<String,Object>> boardCommentReaction(@RequestBody BoardCommentReactionReq reactionReq, @AuthenticationPrincipal UserEntity userEntity) {
      Map<String, Object> map = new HashMap<>();
      BoardCommentReactionReq req = boardCommentService.selectBoardCommentReaction(reactionReq.getCno(), userEntity.getId());
      
      if(req == null){
        reactionReq.setMid(userEntity.getId());
        boardCommentService.addBoardCommentReaction(reactionReq);
      }else{
        reactionReq.setId(req.getId());
        if(reactionReq.getType().equals(req.getType())){
          boardCommentService.deleteBoardCommentReaction(reactionReq);
        }else{
          boardCommentService.updateBoardCommentReaction(reactionReq);
        }
      }

      ReactionCountDTO reactionCount = boardCommentService.getBoardCommentReactionCount(reactionReq.getCno());

      map.put("count", reactionCount);
      return ResponseEntity.ok(map);
  }
}
