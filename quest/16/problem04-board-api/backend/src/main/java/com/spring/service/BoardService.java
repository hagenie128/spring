package com.spring.service;

import java.util.List;

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
    // TODO 4: Mapper로 일반 목록을 조회하세요.
    throw new UnsupportedOperationException("TODO 4");
  }

  public List<BoardDTO> searchBoardList(String keyword, int page, int size) {
    // TODO 4: Mapper로 검색 목록을 조회하세요.
    throw new UnsupportedOperationException("TODO 4");
  }

  public int boardCount() {
    // TODO 4: 전체 게시글 개수를 반환하세요.
    throw new UnsupportedOperationException("TODO 4");
  }

  public BoardDTO selectBoard(Long bno) {
    // TODO 4: 게시글 상세를 반환하세요.
    throw new UnsupportedOperationException("TODO 4");
  }

  public List<BoardCommentDTO> selectBoardComment(Long bno) {
    // TODO 4: 게시글 댓글 목록을 반환하세요.
    throw new UnsupportedOperationException("TODO 4");
  }
}
