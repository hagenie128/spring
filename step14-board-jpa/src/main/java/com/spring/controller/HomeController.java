package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * [홈(루트 경로) 컨트롤러 클래스]
 */
@Controller
public class HomeController {

  /**
   * [루트 경로 요청 처리]
   * 사용자가 브라우저 주소창에 "/" (예: http://localhost:8080/) 만 입력하고 들어왔을 때 실행됩니다.
   * 
   * return "redirect:/board": 
   *   스프링 MVC에게 "/board" 경로로 클라이언트가 다시 요청(Redirect)하도록 지시하여 
   *   자연스럽게 게시판 목록 화면을 첫 화면으로 노출시킵니다.
   */
  @GetMapping("/")
  public String home() {
    return "redirect:/board";
  }
}
