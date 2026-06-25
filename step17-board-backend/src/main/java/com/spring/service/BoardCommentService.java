package com.spring.service;

import org.springframework.stereotype.Service;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardCommentReactionReq;
import com.spring.dto.ReactionCountDTO;
import com.spring.mapper.BoardCommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
  private final BoardCommentMapper boardCommentMapper;

  public void addBoardComment(BoardCommentDTO comment) {
    boardCommentMapper.insertBoardComment(comment);
  }

  public BoardCommentDTO selectBoardComment(int cno) {
    return boardCommentMapper.selectBoardComment(cno);
  }

  public void deleteBoardComment(int cno) {
    boardCommentMapper.deleteBoardComment(cno);
  }

  public void updateBoardComment(BoardCommentDTO reqBoard) {
    boardCommentMapper.updateBoardComment(reqBoard);
  }

  public BoardCommentReactionReq selectBoardCommentReaction(int cno, Long id) {
    return boardCommentMapper.selectBoardCommentReaction(cno,id);
  }

  public void addBoardCommentReaction(BoardCommentReactionReq reactionReq) {
    boardCommentMapper.insertBoardCommentReaction(reactionReq);
  }

  public void deleteBoardCommentReaction(BoardCommentReactionReq reactionReq) {
    boardCommentMapper.deleteBoardCommentReaction(reactionReq);
  }

  public void updateBoardCommentReaction(BoardCommentReactionReq reactionReq) {
    boardCommentMapper.updateBoardCommentReaction(reactionReq);
  }

  public ReactionCountDTO getBoardCommentReactionCount(int cno) {
    return boardCommentMapper.selectBoardCommentReactionCount(cno);
  }
}
