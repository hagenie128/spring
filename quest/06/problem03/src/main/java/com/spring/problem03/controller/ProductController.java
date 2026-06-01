package com.spring.problem03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * [완성된 파일 — 수정 불필요]
 * 상품 목록/상세 컨트롤러
 *
 * dispatcher-context.xml의 ViewResolver 설정이 올바르면
 * 이 컨트롤러가 반환하는 뷰 이름이 JSP 파일 경로로 변환된다.
 *
 * "product/list"   → /WEB-INF/views/product/list.jsp
 * "product/detail" → /WEB-INF/views/product/detail.jsp
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    /**
     * 상품 목록 화면
     * URL: GET /product/list
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<String> products = List.of("아메리카노", "카페라떼", "녹차라떼");
        model.addAttribute("products", products);
        return "product/list";
    }

    /**
     * 상품 상세 화면
     * URL: GET /product/detail?name=아메리카노
     */
    @GetMapping("/detail")
    public String detail(@RequestParam String name, Model model) {
        model.addAttribute("productName", name);
        model.addAttribute("price", 4500);
        return "product/detail";
    }
}
