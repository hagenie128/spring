package com.spring.controller;

import java.util.HashMap;

import java.util.Map;

import java.util.Objects;

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

import com.spring.dto.ReactionCountDTO;

import com.spring.entity.UserEntity;

import com.spring.service.BoardCommentService;

import lombok.RequiredArgsConstructor;

@RestController

@RequestMapping("/api/comments")

@RequiredArgsConstructor

public class BoardCommentController {

  private final BoardCommentService boardCommentService;

  // 댓글 추가

  // 사용자 ID, 댓글 내용, 글번호

  @PostMapping

  public ResponseEntity<Map<String, Object>> addBoardComment(

      @RequestBody BoardCommentDTO comment,

      @AuthenticationPrincipal UserEntity entity) {

    Map<String, Object> map = new HashMap<>();

    comment.setMid(entity.getId());

    boardCommentService.addBoardComment(comment);

    map.put("commentList", boardCommentService.selectBoardCommentList(comment.getBno()));

    map.put("message", "댓글 등록 성공");

    return ResponseEntity.status(HttpStatus.CREATED).body(map);

  }

  // 댓글 삭제

  @DeleteMapping("/{cno}")

  public ResponseEntity<Map<String, Object>> deleteBoardComment(@PathVariable int cno,

      @AuthenticationPrincipal UserEntity userEntity) {

    Map<String, Object> map = new HashMap<>();

    BoardCommentDTO comment = boardCommentService.selectBoardComment(cno);

    if (comment == null) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);

    }

    if (userEntity == null || !Objects.equals(comment.getMid(), userEntity.getId())) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);

    }

    boardCommentService.deleteBoardComment(cno);

    map.put("commentList", boardCommentService.selectBoardCommentList(comment.getBno()));

    map.put("message", "댓글 삭제 성공");

    return ResponseEntity.status(HttpStatus.OK).body(map);

  }

  @PatchMapping("/{cno}")

  public ResponseEntity<Map<String, Object>> updateBoard(@PathVariable int cno,

      @RequestBody BoardCommentDTO reqBoard,

      @AuthenticationPrincipal UserEntity userEntity) {

    BoardCommentDTO comment = boardCommentService.selectBoardComment(cno);

    Map<String, Object> map = new HashMap<>();

    if (comment == null) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    if (userEntity == null || !Objects.equals(comment.getMid(), userEntity.getId())) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    reqBoard.setCno(cno);

    boardCommentService.updateBoardComment(reqBoard);

    map.put("commentList", boardCommentService.selectBoardCommentList(comment.getBno()));

    map.put("message", "댓글 수정 성공");

    return ResponseEntity.status(HttpStatus.OK).body(map);

  }

  @PostMapping("/reaction")

  public ResponseEntity<Map<String, Object>> boardCommentReaction(@RequestBody BoardCommentReactionReq reactionReq,

      @AuthenticationPrincipal UserEntity userEntity) {

    Map<String, Object> map = new HashMap<>();

    BoardCommentReactionReq req = boardCommentService.selectBoardCommentReaction(reactionReq.getCno(),

        userEntity.getId());

    if (req == null) {

      reactionReq.setMid(userEntity.getId());

      boardCommentService.addBoardCommentReaction(reactionReq);

    } else {

      reactionReq.setId(req.getId());

      if (reactionReq.getType().equals(req.getType())) {

        boardCommentService.deleteBoardCommentReaction(reactionReq);

      } else {

        boardCommentService.updateBoardCommentReaction(reactionReq);

      }

    }

    ReactionCountDTO reactionCount = boardCommentService.getBoardCommentReactionCount(reactionReq.getCno());

    map.put("count", reactionCount);

    return ResponseEntity.ok(map);

  }

}
