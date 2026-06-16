package com.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.dto.PostFormDTO;
import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.repository.PostRepository;

import jakarta.validation.Valid;

/**
 * [게시글 비즈니스 로직 계층]
 *
 * [계층 구조 (일반적인 Spring MVC 패턴)]
 * Controller → Service → Repository → DB
 *
 * Controller : HTTP 요청/응답, 화면으로 넘길 데이터 준비
 * Service : 실제 업무 규칙 (검색 조건 분기, 트랜잭션 등)
 * Repository : DB CRUD (SQL 은 Spring Data JPA 가 생성)
 *
 * [@Service]
 * - Spring 이 이 클래스를 '서비스 빈(Bean)'으로 등록합니다.
 * - @Component 와 비슷하지만, 역할이 '비즈니스 로직'임을 나타내는 표시입니다.
 */
@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	/** 전체 목록 (페이징 없음) — 필요 시 사용 */
	public List<Post> getPostList() {
		return postRepository.findAll();
	}

	/**
	 * 검색어 + 페이징 목록
	 *
	 * keyword 가 비어 있으면 → 전체 목록 (findAllWithPost)
	 * keyword 가 있으면 → 제목/내용 LIKE 검색 (searchWithPost)
	 */
	public Page<Post> getPostList(String keyword, Pageable pageable) {
		if (keyword == null || keyword.isEmpty()) {
			return postRepository.findAllWithPost(pageable);
		} else {
			return postRepository.searchWithPost(keyword, pageable);
		}
	}

	public Post createPost(PostFormDTO form, Member loginMember) {
		Post post = new Post();
		post.setTitle(form.getTitle());
		post.setContent(form.getContent());
		post.setMember(loginMember);

		return postRepository.save(post);
	}
}
