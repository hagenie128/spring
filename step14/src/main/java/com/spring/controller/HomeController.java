package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * [홈(루트) URL 처리 컨트롤러]
 *
 * 브라우저에서 http://localhost:8888/ 로 접속했을 때
 * 게시판 목록으로 보내 주는 역할만 합니다.
 *
 * [Controller 계층]
 *  - 사용자의 HTTP 요청(URL)을 받아서 Service 를 호출하고, 화면(뷰) 또는 JSON 을 반환합니다.
 *  - 이 프로젝트는 Thymeleaf HTML 화면을 반환하는 방식입니다.
 */
@Controller
public class HomeController {

	/**
	 * @GetMapping("/")
	 *  - HTTP GET 요청 + 경로 "/" 일 때 이 메서드 실행
	 *
	 * return "redirect:/board/list"
	 *  - 브라우저에게 /board/list 로 다시 요청하라고 알려 줌 (302 리다이렉트)
	 *  - PostController 의 @GetMapping("/list") + @RequestMapping("/board") 와 연결됨
	 */
	@GetMapping("/")
	public String home() {
		return "redirect:/board/list";
	}

}
