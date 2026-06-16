package com.spring;

import com.spring.entity.Member;
import com.spring.entity.Post;
import com.spring.entity.Comment;
import com.spring.entity.CommentReaction;
import com.spring.entity.PostReaction;
import com.spring.entity.ReactionType;
import com.spring.repository.CommentReactionRepository;
import com.spring.repository.CommentRepository;
import com.spring.repository.MemberRepository;
import com.spring.repository.PostReactionRepository;
import com.spring.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * [앱 시작 시 샘플 데이터 자동 삽입]
 *
 * CommandLineRunner : Spring Boot 가 완전히 뜬 뒤 run() 을 한 번 실행합니다.
 * 개발·실습용으로 회원 5명, 게시글 60개, 댓글, 좋아요 등을 넣어 둡니다.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostReactionRepository postReactionRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DataInitializer(MemberRepository memberRepository,
            PostRepository postRepository,
            CommentRepository commentRepository,
            PostReactionRepository postReactionRepository,
            CommentReactionRepository commentReactionRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postReactionRepository = postReactionRepository;
        this.commentReactionRepository = commentReactionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // 이미 DB에 회원 데이터가 있으면 샘플 데이터를 다시 넣지 않음 (재시작 시 중복 오류 방지)
        if (memberRepository.count() > 0) {
            return;
        }
        List<Member> members = createMembers();
        List<Post> posts = createPosts(members);
        List<Comment> comments = createComments(posts, members);
        createPostReactions(posts, members);
        createCommentReactions(comments, members);
    }

    private List<Member> createMembers() {
        List<Member> members = new ArrayList<>();
        members.add(createMember("sample1", "샘플유저1"));
        members.add(createMember("sample2", "샘플유저2"));
        members.add(createMember("sample3", "샘플유저3"));
        members.add(createMember("sample4", "샘플유저4"));
        members.add(createMember("sample5", "샘플유저5"));
        return memberRepository.saveAll(members);
    }

    private Member createMember(String username, String nickname) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode("1234"));
        member.setNickname(nickname);
        member.setRole("USER");
        return member;
    }

    private List<Post> createPosts(List<Member> members) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            Member member = members.get((i - 1) % members.size());

            Post post = new Post();
            post.setTitle(String.format("샘플 게시글 %02d", i));
            post.setContent(createContent(i, member.getNickname()));
            post.setMember(member);
            post.setViewCount(i * 3L);
            posts.add(post);
        }
        return postRepository.saveAll(posts);
    }

    private String createContent(int index, String nickname) {
        return """
                안녕하세요. %s이 작성한 %d번째 샘플 게시글입니다.

                게시판 목록, 상세 보기, 페이징, 검색 기능을 확인하기 위한 테스트 데이터입니다.
                JPA 연관관계와 Thymeleaf 화면 출력이 자연스럽게 보이는지 확인해보세요.
                """.formatted(nickname, index);
    }

    private List<Comment> createComments(List<Post> posts, List<Member> members) {
        List<Comment> comments = new ArrayList<>();
        for (int postIndex = 0; postIndex < posts.size(); postIndex++) {
            Post post = posts.get(postIndex);
            int commentCount = postIndex % 3 + 2;

            for (int i = 1; i <= commentCount; i++) {
                Member member = members.get((postIndex + i) % members.size());
                Comment comment = new Comment();
                comment.setPost(post);
                comment.setMember(member);
                comment.setContent(String.format(
                        "샘플 댓글 %d입니다. %s 화면 테스트에 도움이 되는 댓글입니다.",
                        i,
                        member.getNickname()));
                comments.add(comment);
            }
        }
        return commentRepository.saveAll(comments);
    }

    private void createPostReactions(List<Post> posts, List<Member> members) {
        List<PostReaction> reactions = new ArrayList<>();
        for (int postIndex = 0; postIndex < posts.size(); postIndex++) {
            Post post = posts.get(postIndex);
            int reactionCount = postIndex % members.size() + 1;

            for (int memberIndex = 0; memberIndex < reactionCount; memberIndex++) {
                Member member = members.get(memberIndex);
                PostReaction reaction = new PostReaction();
                reaction.setPost(post);
                reaction.setMember(member);
                reaction.setReactionType((postIndex + memberIndex) % 5 == 0 ? ReactionType.DISLIKE : ReactionType.LIKE);
                reactions.add(reaction);
            }
        }
        postReactionRepository.saveAll(reactions);
    }

    private void createCommentReactions(List<Comment> comments, List<Member> members) {
        List<CommentReaction> reactions = new ArrayList<>();
        for (int commentIndex = 0; commentIndex < comments.size(); commentIndex++) {
            Comment comment = comments.get(commentIndex);
            int reactionCount = commentIndex % 3 + 1;

            for (int i = 0; i < reactionCount; i++) {
                Member member = members.get((commentIndex + i) % members.size());
                CommentReaction reaction = new CommentReaction();
                reaction.setComment(comment);
                reaction.setMember(member);
                reaction.setReactionType((commentIndex + i) % 4 == 0 ? ReactionType.DISLIKE : ReactionType.LIKE);
                reactions.add(reaction);
            }
        }
        commentReactionRepository.saveAll(reactions);
    }
}
