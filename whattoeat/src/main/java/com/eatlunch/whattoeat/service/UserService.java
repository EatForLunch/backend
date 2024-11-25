package com.eatlunch.whattoeat.service;

import com.eatlunch.whattoeat.dto.request.user.UserLoginRequest;
import com.eatlunch.whattoeat.dto.request.user.UserSignupRequest;
import com.eatlunch.whattoeat.entity.User;
import com.eatlunch.whattoeat.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User signup(UserSignupRequest request) {
    // 이메일 중복 검사
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("이미 가입된 이메일입니다");
    }

    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(request.getPassword());

    // 회원 생성
    User user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(encodedPassword)
        .build();

    return userRepository.save(user);
  }
}