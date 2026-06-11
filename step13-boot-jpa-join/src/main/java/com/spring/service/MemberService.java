package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Member;
import com.spring.repository.MemberRepository;

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
	
	
}
