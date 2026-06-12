package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.entity.OrderStatus;
import com.spring.service.MemberService;
import com.spring.service.MenuItemService;
import com.spring.service.OrderService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final MemberService memberService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    public OrderController(OrderService orderService, MemberService memberService, MenuItemService menuItemService) {
        this.memberService = memberService;
        this.menuItemService = menuItemService;
        this.orderService = orderService;
    }

    @GetMapping
    public String list(
            @RequestParam(value = "memberId", required = false) Long memberId,
            @RequestParam(value = "status", required = false) OrderStatus status,
            Model model) {
        model.addAttribute("members", memberService.findAll());
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("orders", orderService.search(memberId, status));
        model.addAttribute("selectedMemberId", memberId);
        model.addAttribute("selectedStatus", status);

        return "order/list";
    }


    @GetMapping("/new")
    public String orderForm(Model model) {
        model.addAttribute("members", memberService.findAll());
        model.addAttribute("statuses", OrderStatus.values());
        return "order/form";
    }

}
