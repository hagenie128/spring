package com.spring.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.spring.entity.Post;
import com.spring.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    public Page<Post> getPostList(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return postRepository.findAllWithPost(pageable);
        } else {
            return postRepository.searchWithPost(keyword, pageable);
        }
    }
}
