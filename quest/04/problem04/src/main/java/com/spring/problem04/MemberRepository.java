package com.spring.problem04;

import org.springframework.stereotype.Repository;

// TODO: @Repository를 추가하세요.
@Repository
public class MemberRepository {
    public void save(String memberId, String encodedPassword) {
        System.out.println("[MemberRepository] 회원 저장: " + memberId);
    }
}
