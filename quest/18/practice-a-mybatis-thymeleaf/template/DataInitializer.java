package com.spring;

import com.spring.dto.BookDTO;
import com.spring.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 샘플 데이터 — 앱 시작 시 DB가 비어 있으면 도서를 넣습니다.
 *
 * 사용법:
 * 1. src/main/java/com/spring/DataInitializer.java 로 복사
 * 2. TODO 부분에 insert 로직 직접 작성
 * 3. BookMapper.insert + XML 이 먼저 구현되어 있어야 함
 *
 * SQL 파일 방식이 더 빠르면 이 클래스는 만들지 않아도 됩니다.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final BookMapper bookMapper;

  @Override
  public void run(String... args) {
    // 이미 데이터가 있으면 스킵 (중복 삽입 방지)
    if (!bookMapper.findAll().isEmpty()) {
      return;
    }

    // TODO 1: BookDTO 객체 3개 만들고 setTitle, setAuthor, setPrice, setPublishedDate
    // TODO 2: bookMapper.insert(dto) 호출

  }
}
