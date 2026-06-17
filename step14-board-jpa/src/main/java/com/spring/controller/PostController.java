package com.spring.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.CommentFormDTO;
import com.spring.dto.PostFormDTO;
import com.spring.entity.Attachment;
import com.spring.entity.Comment;
import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.service.AttachmentService;
import com.spring.service.CommentService;
import com.spring.service.PostService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * [게시판 컨트롤러 클래스]
 * 
 * @Controller: 이 클래스가 Spring MVC의 컨트롤러 역할을 수행함을 나타내며, 스프링 컨테이너가 Bean으로 등록하여 관리합니다.
 * @RequestMapping("/board"): 이 컨트롤러 내부의 모든 핸들러 메서드들의 기본 URL 경로를 '/board'로 매핑합니다.
 */
@Controller
@RequestMapping("/board")
public class PostController {

  // 서비스 레이어와의 협업을 위한 의존성 주입 대상 필드들입니다.
  // final 키워드를 붙여 변경 불가능하게 하고 생성자 주입 방식으로 객체를 주입받습니다.
  private final PostService postService;
  private final AttachmentService attachmentService;
  private final CommentService commentService;

  /**
   * [생성자를 통한 의존성 주입(DI)]
   * Spring 4.3 이후부터는 생성자가 하나만 존재하고 생성자 파라미터가 빈(Bean)으로 등록되어 있다면,
   * 따로 @Autowired 어노테이션을 쓰지 않아도 스프링이 자동으로 주입해 줍니다.
   */
  public PostController(PostService postService, AttachmentService attachmentService, CommentService commentService) {
    this.postService = postService;
    this.attachmentService = attachmentService;
    this.commentService = commentService;
  }

  /**
   * [게시글 목록 화면 반환 API]
   * 
   * @GetMapping: HTTP GET 메서드로 '/board' 요청이 올 때 실행됩니다.
   * @RequestParam: 쿼리 스트링 파라미터를 받아옵니다. 파라미터가 넘어오지 않았을 경우 기본값(defaultValue)을 사용합니다.
   *   - keyword: 검색어 (기본값: 빈 문자열 "")
   *   - page: 현재 페이지 번호 (기본값: 0페이지부터 시작)
   *   - size: 한 페이지에 보여줄 게시글 수 (기본값: 10개)
   * 
   * ModelAndView: 화면(View) 이름과 화면에 전달할 데이터(Model)를 동시에 관리 및 반환해주는 스프링 제공 객체입니다.
   */
  @GetMapping
  public ModelAndView list(ModelAndView view,
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    // PageRequest.of(page, size): 스프링 데이터의 페이징 정보를 나타내는 Pageable 객체를 생성합니다.
    // page는 0부터 시작하므로 첫 페이지는 0입니다.
    Pageable pageable = PageRequest.of(page, size);

    // postService를 통해 조건에 부합하는 페이징 처리된 게시글 정보(Page<Post>)를 얻어옵니다.
    Page<Post> list = postService.getPostList(keyword, pageable);
    
    // Page 객체에서 제공하는 유용한 페이징 관련 메서드들입니다. (로그 출력을 통해 작동 확인 가능)
    System.out.println("첫 페이지 여부: " + list.isFirst());
    System.out.println("마지막 페이지 여부: " + list.isLast());
    System.out.println("현재 페이지 번호 (0부터 시작): " + list.getNumber());
    System.out.println("한 페이지 당 데이터 수: " + list.getSize());
    System.out.println("전체 페이지 수: " + list.getTotalPages());
    
    // Thymeleaf 템플릿 엔진으로 데이터를 넘겨주기 위해 Model 객체에 attribute를 추가합니다.
    view.addObject("currentPage", page);
    view.addObject("postPage", list);
    view.addObject("keyword", keyword);
    
    // 이동할 뷰 이름을 'templates/board/list.html'로 매핑하기 위해 경로명을 입력합니다.
    view.setViewName("board/list");
    
    return view;
  }

  /**
   * [게시글 작성 화면 반환 API]
   * 
   * @SessionAttribute(value = "loginMember", required = false): 
   *   세션에 저장되어 있는 "loginMember" 속성값(로그인 회원 정보)을 바로 파라미터로 바인딩받습니다.
   *   - required = false: 비로그인 상태(세션에 해당 속성이 없음)여도 예외를 내지 않고 null로 받아옵니다.
   * 
   * @param loginMember 세션에서 꺼내온 현재 로그인 회원 객체 (없다면 null)
   * @param model 화면에 DTO를 넘겨줄 모델 객체
   * @return 로그인하지 않았다면 로그인 페이지로 리다이렉트, 로그인 상태라면 글쓰기 화면("templates/board/write.html") 반환
   */
  @GetMapping("/new")
  public String postForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember, Model model) {
    // 세션 정보가 없으면 글쓰기를 제한하고 로그인 화면으로 리다이렉트 처리합니다.
    if(loginMember == null) return "redirect:/auth/login";

    // 폼 검증과 입력 데이터 보관을 위해 빈 PostFormDTO 객체를 뷰에 전달합니다.
    model.addAttribute("form", new PostFormDTO());

    return "board/write";
  }
  
  /**
   * [신규 게시글 등록 및 파일 업로드 처리 API]
   * 
   * @Valid @ModelAttribute("form") PostFormDTO form: 
   *   화면 폼에서 넘어온 제목/본문 텍스트를 DTO에 바인딩하고 유효성 제약조건(@NotBlank 등)을 즉시 검사합니다.
   * @BindingResult bindingResult: 
   *   검증 실패 정보가 담기는 객체입니다.
   * @RequestParam(value = "files", required = false): 
   *   HTML input[type="file"] 태그의 name="files"로 전송된 업로드 파일 목록을 수신합니다.
   */
  @PostMapping("/new")
  public String postNew(@Valid @ModelAttribute("form") PostFormDTO form,
      BindingResult bindingResult,
      @SessionAttribute(value = "loginMember", required = false) Member loginMember, 
      RedirectAttributes attributes,
      @RequestParam(value = "files", required = false) MultipartFile[] files
    ) throws IOException {
    
    // 1. 비로그인 접근 차단
    if(loginMember == null) return "redirect:/auth/login";
    
    // 2. 글 제목/내용 입력 오류가 있다면 글 작성 화면으로 백(Back) 처리
    if(bindingResult.hasErrors()) return "board/write";

    // 3. 서비스 레이어를 호출하여 게시글 본문 DB 저장
    Post post = postService.createPost(form, loginMember);
    
    // 4. 서비스 레이어를 호출하여 첨부파일(들) 디스크 복사 및 메타데이터 DB 저장
    attachmentService.saveFiles(files, post);
    
    // 5. 등록 완료 후 홈 화면("/")으로 이동 (이후 /board로 다시 리다이렉트 됨)
    return "redirect:/";
  }

  /**
   * [게시글 상세 화면 반환 API]
   *
   * 【처리 흐름 알고리즘】
   *   1. 게시글 조회 (없으면 IllegalArgumentException)
   *   2. 댓글 목록 조회 (commentService → CommentRepository → DB)
   *   3. 첨부파일 목록 조회 (attachmentService → AttachmentRepository → DB)
   *   4. 조회수 중복 방지 로직 (세션 기반 HashSet 활용)
   *   5. Model에 데이터 담아 detail.html 반환
   *
   * 【조회수 중복 방지 알고리즘 - HashSet 활용】
   *   - 세션(HttpSession)에 "pageList"라는 이름으로 HashSet<Long>을 저장합니다.
   *   - HashSet은 중복을 허용하지 않으므로, pageList.add(id)는 이미 추가된 ID면 false를 반환합니다.
   *   - add()가 true(새로 추가됨)인 경우에만 조회수를 1 증가시킵니다.
   *   - 같은 브라우저(세션)에서 동일 게시글을 여러 번 새로고침해도 조회수는 1번만 증가합니다.
   *   - 브라우저를 닫아 세션이 만료되면 세트가 초기화되므로 재방문 시 다시 증가합니다.
   *
   * 【알고리즘 복잡도】 HashSet.add() / .contains() → O(1) 평균
   *
   * @param id      조회할 게시글 PK
   * @param view    ModelAndView 객체 (뷰 이름 + 데이터 동시 관리)
   * @param session 사용자별 세션 객체 (조회수 중복 방지용)
   */
  @GetMapping("/{id}")
  public ModelAndView detail(@PathVariable Long id, ModelAndView view, HttpSession session) {
    // 1. 게시글 단건 조회
    Post post = postService.findById(id);

    // 2. 댓글 목록 조회 (작성일 오름차순)
    // 아래 주석된 코드: post.getComments()를 통한 지연 로딩 방식 (N+1 발생 가능)
    // post.getComments().forEach(comment -> {
    //   System.out.println(comment.getId() + " / " + comment.getContent());
    // });
    List<Comment> comments = commentService.getCommentByPost(id);
    for (Comment comment : comments) {
      System.out.println(comment.getId() + " / " + comment.getContent());  // 콘솔 디버그 출력
    }

    // 3. 첨부파일 목록 조회
    List<Attachment> attachments = attachmentService.getAttachmentByPost(id);
    for (Attachment attachment : attachments) {
      System.out.println(attachment.getOriginalName());  // 콘솔 디버그 출력
    }

    // 4. 조회수 중복 방지: 세션의 HashSet에 게시글 ID를 추가 시도
    //    HashSet은 중복 불가이므로 add()가 true면 최초 방문 → 조회수 증가
    //    add()가 false면 이미 본 게시글 → 조회수 그대로 유지
    HashSet<Long> pageList = (HashSet<Long>) session.getAttribute("pageList");
    if (pageList == null) {
      pageList = new HashSet<Long>();
      session.setAttribute("pageList", pageList);  // 세션에 빈 HashSet 최초 생성
    }
    if (pageList.add(id)) {  // 처음 보는 게시글 ID라면 true가 반환됨
      postService.updateCount(id);  // 조회수 +1 업데이트 (Dirty Checking 활용)
    }

    // 5. 뷰에 데이터 전달
    view.addObject("comments", comments);
    view.addObject("attachments", attachments);
    view.addObject("post", post);
    view.addObject("commentForm", new CommentFormDTO());  // 댓글 입력 폼 바인딩용 빈 DTO
    view.setViewName("board/detail");
    return view;
  }

  /**
   * [게시글 삭제 처리 API]
   *
   * 【삭제 처리 순서】
   *   1. 게시글 조회 후 작성자 본인 여부 확인 (보안)
   *   2. 서버 물리 디렉토리에서 첨부파일들을 실제로 삭제 (파일 시스템)
   *   3. DB에서 게시글 삭제 → cascade로 댓글/첨부 메타데이터도 자동 삭제
   *
   * 【@Value("${app.upload.dir}")】
   *   application.properties에 정의된 업로드 디렉토리 경로를 메서드 파라미터로 직접 주입받습니다.
   *   서비스에서 처리할 수도 있지만, 컨트롤러에서 직접 처리한 예시입니다.
   *
   * 【파일 삭제 알고리즘】
   *   업로드 디렉토리 절대경로(rootPath) + 저장 파일명(storedName)을 합쳐
   *   물리 파일 경로를 만들고, .toFile().delete()로 OS 파일을 삭제합니다.
   *
   * @param id        삭제할 게시글 PK
   * @param loginMember 세션에서 꺼낸 로그인 회원 (없으면 null)
   * @param uploadDir application.properties의 app.upload.dir 값
   */
  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Long id,
      @SessionAttribute(value = "loginMember", required = false) Member loginMember,
      @Value("${app.upload.dir}") String uploadDir) {

    // 1. 게시글 조회 + 작성자 본인 확인 (로그인 여부 + ID 일치 여부)
    Post post = postService.findById(id);
    if (loginMember == null || loginMember.getId() != post.getMember().getId()) {
      return "redirect:/auth/login";
    }

    // 2. 첨부파일 물리 삭제: DB에서 파일 목록 조회 후 실제 서버 디스크에서 파일 제거
    List<Attachment> fileList = attachmentService.getAttachmentByPost(id);
    Path rootPath = Paths.get(uploadDir).toAbsolutePath();  // 업로드 루트 경로 (절대 경로)
    for (Attachment att : fileList) {
      rootPath.resolve(att.getStoredName()).toFile().delete();  // 물리 파일 삭제
    }

    // 3. 게시글 DB 삭제 (CascadeType.ALL 로 댓글 + 첨부파일 메타데이터도 자동 삭제)
    postService.deleteById(id);

    return "redirect:/board";
  }
  
  
}
