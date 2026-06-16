package com.spring.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dto.MemberDTO;
import com.spring.entity.Member;
import com.spring.repository.MemberRepository;

/**
 * [회원 비즈니스 로직 계층]
 *
 * 회원가입, 로그인, 비밀번호 암호화(BCrypt) 등을 처리합니다.
 *
 * [BCryptPasswordEncoder]
 *  - 비밀번호를 평문 그대로 DB에 저장하지 않고 해시(암호화)해서 저장합니다.
 *  - encode(평문)  → DB 저장용 해시 생성
 *  - matches(평문, 해시) → 로그인 시 비밀번호 일치 여부 확인
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     * 1. 아이디·닉네임 중복 검사
     * 2. 비밀번호 BCrypt 암호화
     * 3. Member 엔티티로 변환 후 DB 저장
     */
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

    /**
     * 로그인
     * 1. username 으로 회원 조회 (없으면 예외)
     * 2. 입력한 비밀번호와 DB 해시 비교 (matches)
     * 3. 성공 시 Member 반환 → 컨트롤러가 세션에 저장
     */
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        return member;
    }
}
