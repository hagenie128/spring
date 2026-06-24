package com.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
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

  public BoardDTO selectBoard(Long bno) {
    return boardMapper.selectBoard(bno);
  }

  public List<BoardCommentDTO> selectBoardComment(Long bno) {
    return boardMapper.selectBoardComment(bno);
  }

}
