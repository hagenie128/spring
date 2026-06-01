package com.spring.problem01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * [실습] 환영 메시지를 보여주는 컨트롤러
 * 
 * @Controller: Spring MVC가 이 클래스를 컨트롤러로 인식하도록 합니다.
 */
@Controller
public class WelcomeController {

    /**
     * 기본 경로("/") 요청 처리
     * 
     * @return welcome(model): 현재는 welcome 메서드를 직접 호출하여 결과를 반환합니다.
     */
    @GetMapping("/")
    public String main(Model model) {
    	return welcome(model); 
    }
	
	/**
     * "/welcome" 경로 요청 처리
     * 
     * Model model: 컨트롤러에서 뷰(JSP)로 데이터를 전달할 때 사용하는 바구니 역할을 합니다.
     * @return "welcome": ViewResolver 설정에 따라 "/WEB-INF/views/welcome.jsp" 파일을 찾아 실행합니다.
     */
	@GetMapping("/welcome")
    public String welcome(Model model) {
        // model.addAttribute(key, value): JSP에서 ${key} 형식으로 사용할 수 있는 데이터를 담습니다.
        model.addAttribute("msg", "환영합니다! Spring MVC 첫 번째 실습");
        return "welcome";
    }
}