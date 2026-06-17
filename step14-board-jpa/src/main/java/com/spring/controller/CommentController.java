package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spring.dto.CommentFormDTO;
import com.spring.entity.Comment;
import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.service.CommentService;
import com.spring.service.PostService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * [댓글 컨트롤러 클래스]
 *
 * 【URL 매핑 구조】
 *   /comments/post/{id}   → 특정 게시글(id)에 댓글 등록 (POST)
 *   /comments/{id}/delete → 특정 댓글(id) 삭제 (GET)
 *
 * 【REST 관점에서의 설계 이유】
 *   댓글 삭제를 GET으로 처리한 이유: HTML <a> 태그는 GET 방식만 지원하므로,
 *   별도 JavaScript 없이 링크 클릭으로 삭제하려면 GET 방식을 사용합니다.
 *   실무에서는 POST/DELETE 방식 + AJAX를 쓰는 것이 RESTful 원칙에 더 맞습니다.
 */
@RequestMapping("/comments")
@Controller
public class CommentController {
  // final 미선언 → 생성자 주입 방식이지만 불변성은 보장되지 않음 (실무 권장: final 추가)
  private CommentService commentService;
  private PostService postService;

  // 생성자 주입: 스프링 컨테이너가 두 서비스 빈을 자동으로 찾아 주입합니다.
  public CommentController(CommentService commentService, PostService postService) {
    this.commentService = commentService;
    this.postService = postService;
  }

  /**
   * [댓글 등록 처리 API]
   *
   * 【처리 흐름 알고리즘】
   *   1. 세션에서 로그인 정보 확인 → 비로그인이면 로그인 페이지로 리다이렉트
   *   2. 게시글 ID로 Post 엔티티 조회 (postService.findById)
   *   3. 댓글 내용/작성자/게시글 정보를 담아 DB 저장 (commentService.addComment)
   *   4. 게시글 상세 페이지의 댓글 영역으로 리다이렉트
   *
   * 【앵커(#comments) 리다이렉트 원리】
   *   "redirect:/board/{id}#comments" 는 브라우저를 게시글 상세 페이지로 보내면서
   *   HTML에서 id="comments" 인 요소가 있는 위치로 자동 스크롤합니다.
   *   → detail.html의 <div class="comment-list" id="comments"> 로 이동
   *
   * @param id      댓글을 달 게시글 ID (URL 경로 변수)
   * @param form    댓글 내용 DTO (@Valid 로 @NotBlank, @Size 검증 수행)
   * @param member  세션에서 가져온 로그인 회원 정보 (없으면 null)
   */
  @PostMapping("/post/{id}")
  public String addComment(@PathVariable Long id, @Valid CommentFormDTO form, BindingResult bindingResult,
      @SessionAttribute(value = "loginMember", required = false) Member member) {
    // 1. 비로그인 접근 차단
    if(member == null) return "redirect:/auth/login";

    // 2. 게시글 엔티티 조회 (없으면 IllegalArgumentException 발생)
    Post post = postService.findById(id);

    // 3. 댓글 저장 처리 (비즈니스 로직은 서비스 계층에 위임)
    commentService.addComment(form, post, member);

    // 4. 게시글 상세 페이지 댓글 영역(#comments 앵커)으로 이동
    return "redirect:/board/" + id + "#comments";
  }

  /**
   * [댓글 삭제 처리 API]
   *
   * 【보안 체크 알고리즘】
   *   1. 세션에서 로그인 정보 확인 → 비로그인이면 로그인 페이지로 이동
   *   2. 댓글 작성자(comment.getMember().getId())와 현재 로그인 회원(loginMember.getId()) 비교
   *   3. 본인 댓글이 아니면 로그인 페이지로 튕겨냄 (권한 없음 처리)
   *   4. 본인 확인 완료 후 삭제 수행 → 원래 게시글 상세 페이지로 리다이렉트
   *
   * 【주의: != 비교 문제】
   *   Long 타입에 != 비교를 쓰면 객체 참조값(주소)을 비교하게 됩니다.
   *   ID 값이 같아도 다른 Long 객체면 false가 나올 수 있습니다.
   *   실무에서는 !loginMember.getId().equals(comment.getMember().getId()) 사용을 권장합니다.
   *
   * @param id          삭제할 댓글 ID
   * @param loginMember 세션에서 꺼낸 로그인 회원 정보
   */
  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Long id,
      @SessionAttribute(value = "loginMember", required = false) Member loginMember) {
    // 1. 삭제 대상 댓글 조회
    Comment comment = commentService.findById(id);

    // 2. 로그인 여부 + 작성자 본인 여부 확인 (보안 필터)
    if(loginMember == null || loginMember.getId() != comment.getMember().getId()) {
      return "redirect:/auth/login";
    }

    // 3. 댓글 삭제 처리
    commentService.deleteById(id);

    // 4. 댓글이 속한 게시글 상세 페이지로 리다이렉트
    return "redirect:/board/" + comment.getPost().getId();
  }
  


  
  
}
