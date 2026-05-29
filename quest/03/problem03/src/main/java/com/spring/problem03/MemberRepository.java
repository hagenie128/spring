package com.spring.problem03;

/**
 * 회원 데이터를 저장하는 Repository.
 * AppConfig 에서 @Bean 으로 등록한다.
 */
public class MemberRepository {

    public MemberRepository() {
        System.out.println("[MemberRepository] 생성");
    }

    public void save(String name) {
        System.out.println("[MemberRepository] save(\"" + name + "\") 완료");
    }
}
