package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Member;
import com.spring.repository.MemberRepository;

import jakarta.validation.Valid;

// 조회 전용 서비스이므로 기본 트랜잭션을 readOnly로 설정합니다.
@Transactional(readOnly = true)
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public List<Member> findAll() {
		// JpaRepository의 findAll()을 사용해 회원 전체 목록을 조회합니다.
		return memberRepository.findAll();
	}

	@Transactional
	public void delete(long id) {
		memberRepository.deleteById(id);
	}

	@Transactional
	public Member save(Member member) {
		return memberRepository.save(member);
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
	}

	@Transactional
	public void update(Member member) {
		Member raw = findById(member.getId());
		if (raw != null) {
			raw.setName(member.getName());
			raw.setEmail(member.getEmail());
			raw.setPhone(member.getPhone());
		} else {
			throw new RuntimeException("Member not found");
		}
	}

}
