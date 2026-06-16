package com.spring.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dto.PostFormDTO;
import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.service.AttachmentService;
import com.spring.service.CommentService;
import com.spring.service.PostService;

import jakarta.validation.Valid;

/**
 * [게시글 관련 HTTP 요청 처리]
 *
 * URL 예시:
 * GET /board/list → 목록 (1페이지)
 * GET /board/list?page=1 → 2페이지 (page 는 0부터 시작)
 * GET /board/list?keyword=샘플 → 검색
 *
 * [@RequestMapping("/board")]
 * - 이 컨트롤러의 모든 메서드 URL 앞에 /board 가 붙습니다.
 */
@Controller
@RequestMapping("/board")
public class PostController {

	private final PostService postService;
	private final AttachmentService attachmentService; // 추후 첨부파일 기능용
	private final CommentService commentService; // 추후 댓글 기능용

	/**
	 * [생성자 주입]
	 * Spring 이 PostService 등을 만들어서 이 생성자에 넣어 줍니다 (DI / 의존성 주입).
	 * 필드를 직접 new 하지 않아도 되어 테스트·유지보수가 쉬워집니다.
	 */
	PostController(PostService postService, AttachmentService attachmentService, CommentService commentService) {
		this.postService = postService;
		this.attachmentService = attachmentService;
		this.commentService = commentService;
	}

	/**
	 * 게시글 목록 + 검색 + 페이징
	 *
	 * [@RequestParam]
	 * - URL 쿼리스트링 (?keyword=...&page=...) 값을 파라미터로 받습니다.
	 * - defaultValue : 값이 없을 때 기본값
	 *
	 * [ModelAndView]
	 * - view.addObject("이름", 값) → Thymeleaf 에서 ${이름} 으로 사용
	 * - setViewName("board/list") → templates/board/list.html 렌더링
	 *
	 * [Pageable / PageRequest]
	 * - Pageable : "몇 페이지, 한 페이지에 몇 개" 같은 페이징 조건 (Spring Data 인터페이스)
	 * - PageRequest.of(page, size) : Pageable 구현체 생성 (page 는 0부터)
	 * - Page<Post> : 한 페이지 분량 목록 + 전체 개수·전체 페이지 수 등 메타 정보
	 */
	@GetMapping("/list")
	public ModelAndView list(ModelAndView view,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Post> list = postService.getPostList(keyword, pageable);

		// Thymeleaf list.html 에 넘기는 데이터 (모델)
		view.addObject("keyword", keyword); // 검색어 (입력창 유지용)
		view.addObject("currentPage", page); // 현재 페이지 번호 (0-based)
		view.addObject("postPage", list); // Page<Post> → 페이징 + 목록
		view.setViewName("board/list");
		return view;
	}

	@GetMapping("/new")
	public String postForm(@SessionAttribute(value = "loginMember", required = false) Member member, Model model) {
		if (member == null)
			return "redirect:/auth/login";
		model.addAttribute("form", new PostFormDTO());
		return "board/write";
	}

	@PostMapping("/new")
	public String postWrite(@Valid @ModelAttribute("form") PostFormDTO form, BindingResult bindingResult,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			RedirectAttributes redirectAttributes, @RequestParam(value = "files", required = false) MultipartFile[] files) 
			throws IOException {
		if (loginMember == null)
			return "redirect:/auth/login";
		if (bindingResult.hasErrors())
			return "board/write";
		Post post = postService.createPost(form, loginMember);
		attachmentService.saveFiles(files, post);
		// return "redirect:/board/" + post.getId() + "/detail";
		return "redirect:/";
	}
}
