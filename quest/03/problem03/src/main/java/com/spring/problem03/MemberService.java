package com.spring.problem03;

/**
 * 회원 비즈니스 로직.
 * MemberRepository 를 생성자로 주입받는다.
 */
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        System.out.println("[MemberService] 생성 — MemberRepository 주입됨");
    }

    public void register(String name) {
        System.out.print("[MemberService] register(\"" + name + "\") → ");
        memberRepository.save(name);
    }
}
