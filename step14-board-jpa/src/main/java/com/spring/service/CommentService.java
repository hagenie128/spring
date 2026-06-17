package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.CommentFormDTO;
import com.spring.entity.Comment;
import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.repository.CommentRepository;

/**
 * [댓글 비즈니스 로직 서비스 클래스]
 *
 * 【서비스 계층(Service Layer)의 역할】
 *   Controller(화면 처리) ↔ Service(비즈니스 규칙) ↔ Repository(DB 접근)
 *   - 컨트롤러는 HTTP 요청/응답에만 집중하고, 데이터 처리 규칙은 서비스에 위임합니다.
 *   - 서비스 계층에 @Transactional 을 두어 여러 Repository 호출을 하나의 원자적 작업으로 묶습니다.
 *
 * 【@Transactional(readOnly = true) 전략】
 *   - 클래스 레벨에 readOnly = true 설정 → 기본적으로 조회 전용 트랜잭션
 *   - 변경(INSERT/UPDATE/DELETE) 메서드에 @Transactional 별도 선언 → 쓰기 트랜잭션으로 오버라이드
 *   - readOnly=true 이면 JPA 영속성 컨텍스트의 Dirty Checking(변경 감지)을 건너뛰어 성능 향상
 */
@Transactional(readOnly = true)
@Service
public class CommentService {
  // final 선언으로 한 번 주입된 후 변경 불가 (불변성 보장)
  private final CommentRepository commentRepository;

  // 스프링이 생성자 주입을 통해 CommentRepository 구현체(프록시)를 자동 주입합니다.
  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  /**
   * [특정 게시글의 댓글 목록 조회]
   *
   * 두 가지 구현 방법 중 현재는 메서드명 기반 쿼리를 사용합니다.
   * - 방법 A (주석 처리됨): @Query JPQL + JOIN FETCH → N+1 방지에 더 안전
   * - 방법 B (현재 사용): 메서드명 파싱 → 코드가 간결, 단순 조회 시 충분
   *
   * 【응용】 회원 닉네임이 화면에서 자주 쓰인다면 방법 A로 바꿔서
   *         N+1 문제를 원천 차단하는 것이 실무 권장 방식입니다.
   */
  public List<Comment> getCommentByPost(Long id) {
    // return commentRepository.getCommentByPost(id);  // JPQL 페치 조인 버전 (N+1 방지)
    return commentRepository.findByPostIdOrderByCreatedAtAsc(id);  // 메서드명 기반 쿼리
  }

  /**
   * [댓글 등록 처리]
   *
   * 【알고리즘 흐름】
   *   1. DTO(CommentFormDTO)에서 댓글 내용(content)을 꺼냅니다.
   *   2. 새 Comment 엔티티 객체를 생성하고 내용 / 작성자 / 게시글을 세팅합니다.
   *   3. commentRepository.save(comment) → JPA가 INSERT SQL을 자동으로 실행합니다.
   *   4. @Transactional 이 감싸고 있으므로 예외 발생 시 모두 롤백됩니다.
   *
   * 【응용 방법】
   *   - 댓글에 부모 댓글 ID(parentId)를 추가하면 대댓글(계층형 댓글) 구현 가능
   *   - 비속어 필터링 등 검증 로직을 서비스에 추가할 수 있음
   *
   * @param form   사용자가 입력한 댓글 내용 DTO
   * @param post   댓글이 달릴 게시글 엔티티
   * @param member 댓글을 작성한 회원 엔티티
   */
  @Transactional
  public void addComment(CommentFormDTO form, Post post, Member member) {
    Comment comment = new Comment();
    comment.setContent(form.getContent());  // 댓글 내용 세팅
    comment.setMember(member);              // FK: member_id 세팅
    comment.setPost(post);                  // FK: post_id 세팅
    commentRepository.save(comment);        // DB INSERT 실행
  }

  /**
   * [댓글 단건 조회]
   *
   * orElseThrow(): Optional<Comment>에 값이 없으면 (= 존재하지 않는 ID) 예외를 발생시킵니다.
   * 컨트롤러에서 예외 처리를 하거나 @ExceptionHandler로 에러 페이지를 보여줄 수 있습니다.
   */
  public Comment findById(Long id) {
    return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다"));
  }

  /**
   * [댓글 삭제]
   *
   * deleteById()는 내부적으로 먼저 SELECT로 엔티티 존재를 확인한 뒤 DELETE를 실행합니다.
   * 따라서 존재하지 않는 ID를 넘기면 EmptyResultDataAccessException이 발생하며,
   * 이를 방지하려면 findById() 로 먼저 확인하는 패턴을 쓰기도 합니다.
   */
  @Transactional
  public void deleteById(Long id) {
    commentRepository.deleteById(id);
  }

}
