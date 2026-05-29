package com.spring.problem04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
    private MemberRepository memberRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	@Qualifier("smsNotifier")
    private Notifier notifier;

    public void join(String memberId, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        memberRepository.save(memberId, encodedPassword);
        notifier.send(memberId);
        System.out.println("[MemberService] 회원가입 완료");
    }
}