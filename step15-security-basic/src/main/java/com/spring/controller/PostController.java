package com.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 공개 조회와 인증 필요 쓰기의 차이를 확인하기 위한 학습용 게시글 API이다.
 * 실제 DB 대신 메모리의 List를 사용하므로 애플리케이션을 재시작하면 추가한 게시글은 사라진다.
 * SecurityConfig에서 GET은 permitAll, 그 밖의 요청은 authenticated로 설정했기 때문에 같은 URL도
 * HTTP 메서드에 따라 필요한 인증 여부가 달라진다.
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
  // List.of()는 수정할 수 없는 목록이므로, 게시글 추가가 가능하도록 가변 ArrayList로 한 번 감싼다.
  private List<Map<String,Object>> postList = new ArrayList<>(List.of(
    Map.of("id",1,"title","제목1","author","admin","createdAt","2026-01-01"),
    Map.of("id",2,"title","제목2","author","admin","createdAt","2026-01-02"),
    Map.of("id",3,"title","제목3","author","admin","createdAt","2026-01-03"),
    Map.of("id",4,"title","제목4","author","admin","createdAt","2026-01-04")));

    // GET /api/posts: permitAll 규칙에 해당하므로 Authorization 헤더 없이도 조회할 수 있다.
    @GetMapping
    public ResponseEntity<List<Map<String,Object>>> list(){
      return ResponseEntity.ok(postList);
    }

    // GET /api/posts/{id}: URL의 {id} 값을 @PathVariable이 int 매개변수로 변환한다.
    // 현재 학습 코드는 id를 검색하지 않고 List 인덱스로 사용하므로 첫 요소의 인덱스는 0이라는 점에 주의한다.
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> post(@PathVariable int id){
      return ResponseEntity.ok(postList.get(id));
    }

    // POST /api/posts: anyRequest().authenticated() 규칙에 따라 유효한 Access Token이 필요하다.
    @PostMapping
    public ResponseEntity<Map<String,Object>> addPost(@RequestBody Map<String, String> body,@AuthenticationPrincipal UserEntity user) {
        // @RequestBody는 JSON 게시글 내용을 Map으로, @AuthenticationPrincipal은 JWT로 인증된 회원을 주입한다.
        postList.add(Map.of("id", postList.size() + 1,"title",body.get("title"),
      "author",body.get("author"), "createdAt", body.get("createdAt")));
        // Map.of()로 메시지와 최신 목록을 하나의 JSON 객체 형태로 묶어 반환한다.
        return ResponseEntity.ok(Map.of("message", "게시글 등록 성공","list", postList));
    }
    
}
