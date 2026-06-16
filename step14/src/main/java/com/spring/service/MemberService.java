package com.spring.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dto.MemberDTO;
import com.spring.entity.Member;
import com.spring.repository.MemberRepository;

/**
 * [회원 비즈니스 로직 — 추후 구현]
 * 로그인, 회원가입, 비밀번호 암호화(BCrypt) 등이 들어갈 계층입니다.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void register(MemberDTO form) {
        if (memberRepository.existsByUsername(form.getUsername()))
            throw new IllegalArgumentException("중복된 아이디입니다. 다른 아이디를 입력해주세요");
        if (memberRepository.existsByNickname(form.getNickname()))
            throw new IllegalArgumentException("중복된 닉네임입니다. 다른 닉네임을 입력해주세요");

        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setNickname(form.getNickname());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        memberRepository.save(member);
    }

    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        if(!passwordEncoder.matches(password, member.getPassword())) throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
            return member;
        }
    }