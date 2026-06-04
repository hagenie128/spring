package com.spring.problem03.controller;

import com.spring.problem03.dto.OrderForm;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final Map<String, Integer> priceMap = Map.of(
            "불고기버거", 8500,
            "치킨버거", 9000,
            "새우버거", 8000
    );

    @GetMapping("/new")
    public String orderForm(Model model) {
        model.addAttribute("menus", priceMap.keySet());
        return "order/form";
    }

    @PostMapping
    public String order(@ModelAttribute OrderForm form, Model model) {
        // TODO 4: form.getMenuName()으로 단가를 조회하라.
        int unitPrice = priceMap.getOrDefault(form.getMenuName(), null);

        // TODO 5: unitPrice * form.getQuantity()로 총 금액을 계산하라.
        int totalPrice = unitPrice*form.getQuantity();

        // TODO 6: model에 "orderForm", "unitPrice", "totalPrice" 추가
        model.addAttribute("orderForm",form);
        model.addAttribute("unitPrice",unitPrice);
        model.addAttribute("totalPrice",totalPrice);
        return "order/result";
    }
}

