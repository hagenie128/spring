package com.spring.controller;

import com.spring.repository.OrderItemRepository;
import com.spring.repository.OrderRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.entity.MenuItem;
import com.spring.entity.Order;
import com.spring.entity.OrderStatus;
import com.spring.service.MemberService;
import com.spring.service.MenuItemService;
import com.spring.service.OrderService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberService memberService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    public OrderController(OrderService orderService, MemberService memberService, MenuItemService menuItemService, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.memberService = memberService;
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
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
        model.addAttribute("menuItems", menuItemService.findAll());
        return "order/form";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Order order = orderService.findByIdWithDetails(id);
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("order", order);
        return "order/detail";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(Model model, @PathVariable("id") Long id, @RequestParam("status") OrderStatus status, RedirectAttributes ra) {
        Order order = orderService.findByIdWithDetails(id);
        order.setStatus(status);
        orderService.update(order);
        ra.addFlashAttribute("message", "주문 상태가 변경되었습니다.");
        return "redirect:/orders/" + id;
    }

    @PostMapping
    public String save(
            @RequestParam("memberId") Long memberId,
            @RequestParam("menuItemIds") List<Long> menuItemIds,
            @RequestParam("quantities") List<Integer> quantities,
            RedirectAttributes ra) {
        try {
            Order order = orderService.save(memberId, menuItemIds, quantities);
            return "redirect:/orders/" + order.getId();
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/orders/new";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes ra) {
        orderService.delete(id);
        ra.addFlashAttribute("message", "주문이 삭제되었습니다.");
        return "redirect:/orders";
    }

}
