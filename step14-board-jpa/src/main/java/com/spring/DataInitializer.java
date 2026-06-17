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
 * [테스트 데이터 초기화 클래스]
 *
 * 【CommandLineRunner 패턴】
 *   스프링 부트 애플리케이션이 완전히 구동된 직후 run() 메서드를 자동 실행합니다.
 *   → 개발/테스트 환경에서 더미 데이터를 자동으로 DB에 넣어두는 데 활용합니다.
 *
 * 【@Component 주석 처리된 이유】
 *   @Component 가 있으면 스프링 Bean으로 등록되어 서버 시작 시 매번 데이터가 삽입됩니다.
 *   데이터가 이미 있는 상태에서 다시 실행하면 중복 오류가 발생하므로,
 *   최초 한 번 데이터를 넣은 후에는 @Component 를 주석 처리하거나 제거합니다.
 *
 * 【응용 방법】
 *   - 운영 환경에서는 DataInitializer 클래스 자체를 삭제하거나 활성화하지 않습니다.
 *   - application.properties 에서 spring.jpa.hibernate.ddl-auto=create 로 설정하면
 *     서버 시작 시 테이블을 다시 만들면서 기존 데이터를 삭제하므로 이 클래스와 조합해 쓸 수 있습니다.
 */
// @Component  ← 데이터 최초 삽입 후 비활성화 (중복 방지)
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

    /**
     * [스프링 부트 시작 후 자동 실행되는 초기화 메서드]
     *
     * @Transactional: 모든 초기화 작업을 하나의 트랜잭션으로 묶어,
     * 중간에 예외 발생 시 지금까지 INSERT된 내용을 모두 롤백합니다.
     * 순서: 회원 → 게시글 → 댓글 → 게시글 반응 → 댓글 반응
     */
    @Override
    @Transactional
    public void run(String... args) {
        List<Member> members = createMembers();            // 1단계: 회원 5명 생성
        List<Post> posts = createPosts(members);           // 2단계: 게시글 60개 생성
        List<Comment> comments = createComments(posts, members);  // 3단계: 댓글 생성
        createPostReactions(posts, members);               // 4단계: 게시글 좋아요/싫어요 생성
        createCommentReactions(comments, members);         // 5단계: 댓글 좋아요/싫어요 생성
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
        // BCryptPasswordEncoder.encode("1234"): 평문 "1234"를 단방향 해시로 암호화
        // → DB에 "$2a$10$..." 형태의 60자 암호화 문자열로 저장됨
        // 로그인 시에는 passwordEncoder.matches(입력값, 저장값)으로 비교합니다.
        member.setPassword(passwordEncoder.encode("1234"));
        member.setNickname(nickname);
        member.setRole("USER");
        return member;
    }

    private List<Post> createPosts(List<Member> members) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            // (i-1) % members.size() : 0,1,2,3,4,0,1,2... 순으로 5명 회원에게 번갈아 게시글 할당
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
                reaction.setType((postIndex + memberIndex) % 5 == 0 ? ReactionType.DISLIKE : ReactionType.LIKE);
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
                reaction.setType((commentIndex + i) % 4 == 0 ? ReactionType.DISLIKE : ReactionType.LIKE);
                reactions.add(reaction);
            }
        }
        commentReactionRepository.saveAll(reactions);
    }
}
