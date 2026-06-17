package com.spring.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dto.MemberDTO;
import com.spring.entity.Member;
import com.spring.repository.MemberRepository;

/**
 * [회원 가입 및 로그인 처리 서비스 클래스]
 */
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  
  /**
   * [비밀번호 암호화 인코더 정의]
   * BCryptPasswordEncoder: 단방향 해시 암호화 기능을 제공하는 Spring Security 제공 클래스입니다.
   * - 파라미터 10: 해시를 수행하는 연산 강도(Strength, Log rounds)를 설정합니다. 
   *   값이 높을수록 암호화 계산이 복잡해져 해킹(무차별 대입 공격 등)에 강하지만 서버 CPU 연산 시간이 다소 증가합니다. (기본값: 10)
   */
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

  /**
   * [생성자 의존성 주입]
   */
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /**
   * [신규 회원 가입 처리 메서드]
   * 
   * @param form 회원가입 입력 정보가 들어있는 DTO
   * @throws IllegalArgumentException 아이디나 닉네임이 이미 존재할 경우 예외를 던집니다.
   */
  public void register(MemberDTO form) {
    // 1. 아이디 중복 체크
    if(memberRepository.existsByUsername(form.getUsername()))
      throw new IllegalArgumentException("중복된 아이디 입니다. 다른 아이디를 입력해 주세요");
      
    // 2. 닉네임 중복 체크
    if(memberRepository.existsByNickname(form.getNickname()))
      throw new IllegalArgumentException("중복된 닉네임 입니다. 다른 닉네임을 입력해 주세요");

    // 3. DTO의 데이터를 꺼내어 엔티티(Member) 객체 생성 및 비밀번호 암호화
    Member member = new Member();
    member.setUsername(form.getUsername());
    member.setNickname(form.getNickname());
    
    // 사용자가 입력한 평문 비밀번호를 passwordEncoder.encode()를 통해 안전한 해시 암호문으로 변환하여 세팅합니다.
    member.setPassword(passwordEncoder.encode(form.getPassword()));
    
    // 4. DB에 영속화(저장)
    memberRepository.save(member);
  }

  /**
   * [로그인 검증 메서드]
   * 
   * @param username 입력받은 아이디
   * @param password 입력받은 비밀번호(평문)
   * @return 로그인 성공 시 해당하는 회원 엔티티 객체
   * @throws IllegalArgumentException 아이디가 없거나 비밀번호가 틀린 경우 예외를 던집니다.
   */
  public Member login(String username, String password) {
    // 1. 해당 아이디를 가진 회원이 존재하는지 DB 조회
    //    아이디가 존재하지 않는 경우 바로 IllegalArgumentException 예외를 던져 로직을 중단시킵니다.
    Member member = memberRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));
    
    // 2. 입력받은 평문 패스워드와 DB에 저장된 암호화 패스워드 비교
    //    *주의*: BCrypt는 매번 다른 솔트를 섞어서 암호문을 생성하므로 passwordEncoder.encode(password).equals(savedPassword) 같은 
    //    단순 문자열 비교로는 절대 일치하지 않습니다. 반드시 .matches(평문, 암호문) 메서드를 이용해 비교해야 합니다.
    if(!passwordEncoder.matches(password, member.getPassword())){
      throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    // 3. 아이디와 패스워드가 모두 일치하면 세션에 등록할 수 있도록 회원 정보 반환
    return member;
  }
}
