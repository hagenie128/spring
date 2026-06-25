package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardDTO;
import com.spring.dto.BoardReactionReq;
import com.spring.dto.ReactionCountDTO;

@Mapper
public interface BoardMapper {

  List<BoardDTO> selectBoardList(@Param("page") int page,
                                 @Param("size") int size);

  List<BoardDTO> searchBoardList(@Param("keyword") String keyword, 
                                 @Param("page") int page,
                                 @Param("size") int size);

  int boardCount();

  BoardDTO selectBoard(int bno);

  List<BoardCommentDTO> selectBoardComment(int bno);

  void deleteBoard(int bno);

  void insertBoard(BoardDTO board);

  void updateBoard(BoardDTO reqBoard);

  BoardReactionReq selectBoardReaction(@Param("bno") int bno,@Param("id") Long id);

  void insertBoardReaction(BoardReactionReq reactionReq);

  void deleteBoardReaction(BoardReactionReq reactionReq);

  void updateBoardReaction(BoardReactionReq reactionReq);

  ReactionCountDTO selectBoardReactionCount(int bno);

}
