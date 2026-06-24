package com.spring.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * DB의 사용자 정보를 Spring Security가 이해하는 {@link UserDetails} 형태로 반환한다.
 * 
 * <p>로그인 흐름:</p>
 * <ol>
 *   <li>AuthController가 아이디와 비밀번호를 받는다.</li>
 *   <li>AuthenticationManager.authenticate()가 이 메서드로 사용자를 조회한다.</li>
 *   <li>PasswordEncoder가 요청 비밀번호와 DB의 BCrypt 해시를 비교한다.</li>
 *   <li>일치하면 인증된 Authentication을 반환하고, 아니면 인증 예외가 발생한다.</li>
 * </ol>
 * 
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{
  
  private final UserRepository userRepository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // UserEntity가 UserDetails를 구현하므로 별도의 변환 객체 없이 바로 반환할 수 있다.
    return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));
  }

}
