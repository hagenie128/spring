package com.spring.mapper;

import com.spring.dto.BookDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BookMapper {
  // TODO R — 목록
  List<BookDTO> findAll();

  // TODO R — 상세
  BookDTO findById(Long bookId);

  // TODO C — 등록
  void insert(BookDTO book);

  // TODO U — 수정
  void update(BookDTO book);

  // TODO D — 삭제
  void deleteById(Long bookId);
}
