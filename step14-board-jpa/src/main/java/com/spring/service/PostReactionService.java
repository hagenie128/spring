package com.spring.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.PostReaction;
import com.spring.entity.ReactionType;
import com.spring.repository.MemberRepository;
import com.spring.repository.PostReactionRepository;
import com.spring.repository.PostRepository;

@Service
@Transactional(readOnly = true)
public class PostReactionService {

  private final PostReactionRepository postReactionRepository;
  private final MemberRepository memberRepository;
  private final PostRepository postRepository;

  PostReactionService(PostReactionRepository postReactionRepository, MemberRepository memberRepository,
      PostRepository postRepository) {
    this.postReactionRepository = postReactionRepository;
    this.memberRepository = memberRepository;
    this.postRepository = postRepository;
  }

  public long getReactionCount(Long postId, ReactionType type) {
    return postReactionRepository.countByPostIdAndType(postId, type);
  }

  @Transactional
  public void addReaction(Long postId, ReactionType reactionType, Long memberId) {
    System.out.println(1);
    Optional<PostReaction> opt = postReactionRepository.findByPostIdAndMemberId(postId, memberId);
    if (opt.isPresent()) {
      System.out.println(2);
      PostReaction reaction = opt.get();

      if (reaction.getType() == reactionType) {
        postReactionRepository.delete(reaction);// 같은 타입 취소
        System.out.println(3);
      } else {
        reaction.setType(reactionType);// 다른 타입 변경
        System.out.println(4);
      }
    } else {
      System.out.println(5);
      PostReaction reaction = new PostReaction();
      reaction.setMember(memberRepository.findById(memberId).orElseThrow(
          () -> new IllegalArgumentException("해당하는 회원이 없습니다")));
      reaction.setPost(postRepository.findById(postId).orElseThrow(
          () -> new IllegalArgumentException("해당 게시글이 없습니다")));
      reaction.setType(reactionType);
      reaction.setId(null);
      postReactionRepository.save(reaction);
    }
  }
}
