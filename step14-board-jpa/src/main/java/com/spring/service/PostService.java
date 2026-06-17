package com.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.PostFormDTO;
import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.repository.PostRepository;


/**
 * [게시판 비즈니스 로직 서비스 클래스]
 * 
 * @Service: 이 클래스가 서비스 레이어의 핵심 컴포넌트(Spring Bean)임을 선언하며, 컨트롤러와 DB 접근 레이어 사이에서 비즈니스적인 예외 처리 및 트랜잭션을 중재합니다.
 * @Transactional(readOnly = true): 
 *   이 클래스의 모든 메서드는 기본적으로 읽기 전용 트랜잭션(Read-Only Transaction) 환경에서 실행됩니다.
 *   - JPA 영속성 컨텍스트는 읽기 전용일 때 스냅샷 저장 및 변경 감지(Dirty Checking) 작업을 생략하여 메모리와 성능을 최적화합니다.
 */
@Service
@Transactional(readOnly = true)
public class PostService {

  // 데이터베이스 조작을 위한 JPA 리포지토리입니다. final 키워드로 불변성을 제공합니다.
  private final PostRepository postRepository;

  /**
   * [생성자를 통한 의존성 주입 (Constructor Injection)]
   * 의존하는 객체를 생성자를 통해 주입받음으로써 불변성 확보, 누락 방지, 테스트 용이성의 이점을 얻습니다.
   */
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  /**
   * [전체 게시글 목록 단순 조회]
   * findAll(): JpaRepository가 기본 제공하는 전체 조회 API입니다.
   */
  public List<Post> getPostList() {
    return postRepository.findAll();
  }

  /**
   * [게시글 페이징 및 키워드 검색 조회]
   * 
   * @param keyword  검색 키워드 (제목 또는 작성자 닉네임 검색용)
   * @param pageable 페이징 설정(현재 몇 번째 페이지인지, 한 페이지당 글을 몇 개 가져올지 등)
   * @return Page<Post> 페이징 결과 데이터 및 메타데이터를 내포한 Page 객체
   */
  public Page<Post> getPostList(String keyword, Pageable pageable) {
    // 검색어가 입력되지 않은 경우 (기본 목록 화면 조회)
    if (keyword == null || keyword.isEmpty()) {
      // PostRepository에 선언해 둔 fetch join 쿼리를 실행해 N+1 문제 없이 전체 목록을 가져옵니다.
      return postRepository.findAllWithPost(pageable);
    } else {
      // 검색어가 존재하는 경우, LIKE 검색 쿼리를 실행합니다.
      return postRepository.searchWithPost(keyword, pageable);
    }
  }

  /**
   * [새 게시글 등록 처리 메서드]
   * 
   * @Transactional: 
   *   클래스의 readOnly = true 설정을 무시하고, 데이터 생성/쓰기가 가능하도록 일반 트랜잭션을 엽니다.
   *   메서드가 에러 없이 정상 종료되면 DB에 커밋(Commit)이 일어나고, 런타임 예외가 발생하면 롤백(Rollback)을 수행합니다.
   * 
   * @param form 작성할 게시글 제목과 내용이 담긴 DTO
   * @param loginMember 현재 세션에 로그인되어 있는 회원 객체
   * @return DB 저장 후 자동으로 기본키(ID) 값이 채워진 영속 상태의 Post 엔티티 객체
   */
  @Transactional
  public Post createPost(PostFormDTO form, Member loginMember) {
    // 1. DTO 데이터를 바탕으로 엔티티 객체 생성
    Post post = new Post();
    post.setTitle(form.getTitle());
    post.setContent(form.getContent());
    
    // 2. 게시글 작성자로 현재 로그인 회원 정보 세팅 (FK 외래키 설정)
    post.setMember(loginMember);
    
    // 3. 리포지토리를 통한 DB 저장 (INSERT 쿼리 수행)
    postRepository.save(post);
    
    return post;
  }

  public Post findById(Long id) {
    return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
  }

  @Transactional
  public void updateCount(Long id) {
    Post post = findById(id);
    post.setViewCount(post.getViewCount()+1);
  }

  @Transactional
  public void deleteById(Long id) {
    postRepository.deleteById(id);
  }
}
