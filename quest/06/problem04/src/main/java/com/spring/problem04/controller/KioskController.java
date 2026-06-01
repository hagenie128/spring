package com.spring.problem04.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.problem04.dto.MenuItem;

// TODO 1: 필요한 import를 추가하라.
//   import org.springframework.stereotype.Controller;
//   import org.springframework.ui.Model;
//   import org.springframework.web.bind.annotation.GetMapping;
//   import org.springframework.web.bind.annotation.RequestMapping;
//   import org.springframework.web.bind.annotation.RequestParam;
//   import java.util.List;
//   import com.spring.problem04.dto.MenuItem;

// TODO 2: @Controller 어노테이션을 추가하라.
//         → Spring MVC가 이 클래스를 요청 처리 Handler로 인식한다.

// TODO 3: @RequestMapping("/kiosk")를 클래스 레벨에 추가하라.
//         → 이 클래스의 모든 메서드는 /kiosk 로 시작하는 URL을 처리한다.
/**
 * [실습] 키오스크 기능을 처리하는 컨트롤러 클래스
 * 
 * @Controller: 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다. 
 * Spring이 이 클래스의 객체를 생성하고 관리하며, 웹 요청을 처리하는 핸들러로 사용합니다.
 * 
 * @RequestMapping("/kiosk"): 이 컨트롤러의 모든 메서드는 URL이 "/kiosk"로 시작하는 요청을 처리합니다.
 * 클래스 레벨에 공통 경로를 지정하여 중복을 줄일 수 있습니다.
 */
@RequestMapping("/kiosk")
@Controller
public class KioskController {

    /**
     * 키오스크 홈 화면 요청 처리
     * URL: GET /kiosk/home
     * 
     * @GetMapping("/home"): HTTP GET 방식의 "/kiosk/home" 요청을 이 메서드에 연결합니다.
     * @return "kiosk/home": ViewResolver에 의해 "/WEB-INF/views/kiosk/home.jsp" 페이지로 이동합니다.
     */
	@GetMapping("/home")
	public String home() {
		return "kiosk/home";
	}

    /**
     * 메뉴 목록 화면 요청 처리
     * URL: GET /kiosk/menu
     * 
     * ModelAndView: 데이터(Model)와 이동할 페이지(View) 정보를 한꺼번에 담아서 반환하는 객체입니다.
     */
	@GetMapping("/menu")
	public ModelAndView menu(ModelAndView view) {
		// 실습을 위해 임의의 메뉴 리스트 생성 (실제로는 DB에서 가져옴)
		List<MenuItem> list = List.of(
				new MenuItem("불고기버거", 8500),
				new MenuItem("치킨버거", 9000),
				new MenuItem("새우버거", 8000));
		
		// view.addObject(key, value): JSP에서 사용할 데이터를 추가합니다.
		view.addObject("menuList", list);
		
		// view.setViewName(viewName): 이동할 JSP 파일의 경로(이름)를 지정합니다.
		view.setViewName("kiosk/menu");
		
		return view;
	}

    /**
     * 주문 처리 요청 처리
     * URL: GET /kiosk/order?menu=메뉴명&qty=수량
     * 
     * @param menu: URL 쿼리 파라미터 중 "menu" 값을 받아옵니다.
     * @param qty: URL 쿼리 파라미터 중 "qty" 값을 int 타입으로 자동 변환하여 받아옵니다.
     */
	@GetMapping("/order")
	public ModelAndView order(ModelAndView view, String menu, int qty) {
		// 메뉴별 단가 정보를 담은 Map
		Map<String, Integer> prices = new HashMap<>();
		prices.put("불고기버거", 8500);
		prices.put("치킨버거", 9000);
		prices.put("새우버거", 8000);
		
		// JSP로 전달할 데이터 추가
		view.addObject("orderMenu", menu);
		view.addObject("orderQty", qty);
		
		// 단가를 조회하여 총 금액 계산 (없을 경우 0원)
		int unitPrice = prices.getOrDefault(menu, 0);
		view.addObject("totalPrice", unitPrice * qty);
		
		// 주문 완료 페이지로 이동
		view.setViewName("kiosk/order");
		return view;
	}
	
	/**
     * 메뉴 등록 폼 화면 요청 처리
     * URL: GET /kiosk/register/view
     */
	@GetMapping("/register/view")
	public String regView() {
		return "kiosk/form";
	}
	
	/**
     * 메뉴 등록 처리 (POST 방식)
     * URL: POST /kiosk/register
     * 
     * @param item: 커맨드 객체(Command Object). 
     * 폼의 input name과 DTO의 필드명이 같으면 Spring이 자동으로 값을 채워(Binding)줍니다.
     */
	@PostMapping("/register")
	public String register(MenuItem item) {
		// 전송된 데이터 확인
		System.out.println("등록된 메뉴 정보: " + item);
		return "kiosk/home";
	}
}