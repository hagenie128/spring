package com.spring.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.service.AttachmentService;
import com.spring.service.CommentService;
import com.spring.service.PostService;

@Controller
@RequestMapping("/board")
public class PostController {

    private final PostService postService;
    private final AttachmentService attachmentService;
    private final CommentService commentService;

    PostController(PostService postService, AttachmentService attachmentService, CommentService commentService) {
        this.postService = postService;
        this.attachmentService = attachmentService;
        this.commentService = commentService;
    }

    @GetMapping("/list")
    public ModelAndView list(ModelAndView view,
        @RequestParam(value = "keyword", defaultValue = "") String keyword,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Post> list = postService.getPostList(keyword, pageable);
        System.out.println(list.isFirst());
        System.out.println(list.isLast());
        System.out.println(list.getNumber());
        System.out.println(list.getSize());
        System.out.println(list.getTotalPages());
        view.addObject("keyword", keyword);
        view.addObject("currentPage", page);
        view.addObject("postPage", list);
        view.setViewName("board/list");
        return view;
    }
}
