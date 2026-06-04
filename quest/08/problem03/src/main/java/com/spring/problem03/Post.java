package com.spring.problem03;

/**
 * 게시글 도메인 객체 [완성된 파일 — 수정 불필요]
 */
public class Post {

    private Long id;
    private String title;
    private String content;
    private String author;

    // 기본 생성자
    public Post() {}

    // 전체 생성자
    public Post(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // --- Getter/Setter ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
