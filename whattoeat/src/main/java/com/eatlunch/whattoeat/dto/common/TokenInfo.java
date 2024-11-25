package com.eatlunch.whattoeat.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TokenInfo {
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private LocalDateTime refreshTokenExpiresIn;
}
