package com.spring.problem03.controller;

import com.spring.problem03.dto.OrderForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    // 메뉴명 → 단가 매핑 (완성된 코드)
    private final Map<String, Integer> priceMap = Map.of(
        "불고기버거", 8500,
        "치킨버거",   9000,
        "새우버거",   8000
    );

    // GET /orders/new → 주문 입력 폼 표시
    @GetMapping("/new")
    public String orderForm(Model model) {
        // TODO 4: model에 "menus" 키로 priceMap.keySet()을 추가하세요.
        //         (템플릿의 <select> 태그에서 th:each로 사용됩니다)
    	model.addAttribute("menus",priceMap.keySet());
        // TODO 5: model에 "orderForm" 키로 new OrderForm()을 추가하세요.
        //         (템플릿의 th:object="${orderForm}"과 연결됩니다)
    	model.addAttribute("orderForm",new OrderForm());
        return "order/form";
    }

    // POST /orders → 주문 처리 후 결과 표시
    @PostMapping
    public String order(@ModelAttribute OrderForm form, Model model) {
        // TODO 6: priceMap에서 form.getMenuName()에 해당하는 단가(unitPrice)를 조회하세요.
        //         메뉴가 없을 경우 기본값 0을 사용해도 됩니다.
    	int unitPrice = 
    			priceMap.get(form.getMenuName()) == null ? 
    					0 : priceMap.get(form.getMenuName());
        // TODO 7: totalPrice = unitPrice * form.getQuantity() 로 계산하세요.
    	int totalPrice = unitPrice * form.getQuantity();
        // TODO 8: model에 다음을 추가하세요.
        //   - "orderForm"  → form (입력된 주문 정보)
        //   - "unitPrice"  → 단가
        //   - "totalPrice" → 총 금액
    	model.addAttribute("orderForm",form);
    	model.addAttribute("unitPrice",unitPrice);
    	model.addAttribute("totalPrice",totalPrice);
        return "order/result";
    }
}
