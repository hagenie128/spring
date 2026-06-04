package com.spring.problem03;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 게시글 in-memory 저장소 [완성된 파일 — 수정 불필요]
 */
@Component
public class PostRepository {

    private final List<Post> posts = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public PostRepository() {
        save(new Post(null, "첫 번째 게시글", "내용입니다.", "admin"));
        save(new Post(null, "두 번째 게시글", "내용 두 번째.", "user01"));
        save(new Post(null, "세 번째 게시글", "내용 세 번째.", "user02"));
    }

    public Post save(Post post) {
        post.setId(idGen.getAndIncrement());
        posts.add(post);
        return post;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public Post findById(Long id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean deleteById(Long id) {
        return posts.removeIf(p -> p.getId().equals(id));
    }
}
