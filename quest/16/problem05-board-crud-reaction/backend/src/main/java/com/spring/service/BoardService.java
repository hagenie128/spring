package com.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
import com.spring.dto.BoardReactionReq;
import com.spring.dto.ReactionCountDTO;
import com.spring.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardMapper boardMapper;

  public List<BoardDTO> getBoardList(int page, int size) {
    return boardMapper.selectBoardList(page,size);
  }

	public List<BoardDTO> searchBoardList(String keyword, int page, int size) {
    return boardMapper.searchBoardList(keyword,page,size);
	}

  public int boardCount() {
    return boardMapper.boardCount();
  }

  public BoardDTO selectBoard(int bno) {
    return boardMapper.selectBoard(bno);
  }

  public List<BoardCommentDTO> selectBoardComment(int bno) {
    return boardMapper.selectBoardComment(bno);
  }

  public void deleteBoard(int bno) {
    boardMapper.deleteBoard(bno);
  }

  public void addBoard(BoardDTO board) {
    boardMapper.insertBoard(board);
  }

  public void updateBoard(BoardDTO reqBoard) {
    boardMapper.updateBoard(reqBoard);
  }

  public BoardReactionReq selectBoardReaction(int bno, Long id) {
    return boardMapper.selectBoardReaction(bno,id);
  }

  public void addBoardReaction(BoardReactionReq reactionReq) {
    boardMapper.insertBoardReaction(reactionReq);
  }

  public void deleteBoardReaction(BoardReactionReq reactionReq) {
    boardMapper.deleteBoardReaction(reactionReq);
  }

  public void updateBoardReaction(BoardReactionReq reactionReq) {
    boardMapper.updateBoardReaction(reactionReq);
  }

  public ReactionCountDTO getBoardReactionCount(int bno) {
    return boardMapper.selectBoardReactionCount(bno);
  }

}
