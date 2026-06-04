package com.spring.board;

/**
 * 게시글 도메인 객체 (in-memory 저장소용)
 */
public class Board {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdAt; // LocalDateTime.now().toString().substring(0, 16)

    // 기본 생성자
    public Board() {}

    // 전체 생성자
    public Board(Long id, String title, String content, String author, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
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

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
