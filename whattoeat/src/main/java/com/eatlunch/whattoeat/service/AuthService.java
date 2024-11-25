package com.eatlunch.whattoeat.service;


import com.eatlunch.whattoeat.dto.common.TokenInfo;
import com.eatlunch.whattoeat.dto.request.user.UserLoginRequest;
import com.eatlunch.whattoeat.dto.response.auth.AuthResponse;
import com.eatlunch.whattoeat.entity.RefreshToken;
import com.eatlunch.whattoeat.entity.User;
import com.eatlunch.whattoeat.repository.RefreshTokenRepository;
import com.eatlunch.whattoeat.repository.UserRepository;
import com.eatlunch.whattoeat.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  // AuthService
  @Transactional
  public AuthResponse login(UserLoginRequest request) {
    // 1. 인증 처리
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 2. 토큰 생성
    TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

    // 3. 사용자 정보 조회
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    // 4. RefreshToken 저장
    RefreshToken refreshToken = RefreshToken.builder()
        .key(authentication.getName())
        .value(tokenInfo.getRefreshToken())
        .expiresAt(tokenInfo.getRefreshTokenExpiresIn())
        .build();
    refreshTokenRepository.save(refreshToken);

    return AuthResponse.builder()
        .tokenInfo(tokenInfo)
        .user(user)
        .build();
  }

  @Transactional
  public TokenInfo refreshToken(String refreshToken) {
    // 1. Refresh Token 검증
    if (!jwtTokenProvider.validateToken(refreshToken)) {
      throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
    }

    // 2. Access Token에서 User ID 가져오기
    Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

    // 3. 저장소에서 User ID를 기반으로 Refresh Token 값 가져오기
    RefreshToken refreshTokenEntity = refreshTokenRepository.findById(authentication.getName())
        .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

    // 4. Refresh Token 일치하는지 검사
    if (!refreshTokenEntity.getValue().equals(refreshToken)) {
      throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
    }

    // 5. 새로운 토큰 생성
    TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

    // 6. 저장소 정보 업데이트
    refreshTokenEntity.updateValue(tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpiresIn());

    return tokenInfo;
  }
}
