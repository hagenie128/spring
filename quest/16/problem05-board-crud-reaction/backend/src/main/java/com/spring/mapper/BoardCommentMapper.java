package com.spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.dto.BoardCommentDTO;
import com.spring.dto.BoardCommentReactionReq;
import com.spring.dto.ReactionCountDTO;

@Mapper
public interface BoardCommentMapper {

  void insertBoardComment(BoardCommentDTO comment);

  BoardCommentDTO selectBoardComment(int cno);

  void deleteBoardComment(int cno);

  void updateBoardComment(BoardCommentDTO reqBoard);

  BoardCommentReactionReq selectBoardCommentReaction(@Param("cno") int cno, @Param("id") Long id);

  void insertBoardCommentReaction(BoardCommentReactionReq reactionReq);

  void deleteBoardCommentReaction(BoardCommentReactionReq reactionReq);

  void updateBoardCommentReaction(BoardCommentReactionReq reactionReq);

  ReactionCountDTO selectBoardCommentReactionCount(int cno);

}
